package com.service;

import com._config._helpers.RandomCodeGenerator;
import com._config._helpers._enums.RoleName;
import com._config._mail.HTML;
import com._config._mail.MailMessenger;
import com._config._mail.Token;
import com.dto.AccountDTO;
import com.dto.RoleDTO;
import com.dto.UserDTO;
import com.entity.User;
import com.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;

    private static final Locale locale = LocaleContextHolder.getLocale();
    private static String errorMessage;

    @Override
    public User dtoToEntity(UserDTO userDTO) throws NotFoundException {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        if (userDTO.getPersonDTO() != null) {
            user.setPerson(personService.dtoToEntity(userDTO.getPersonDTO()));
        }
        if (userDTO.getRoleDTOS() != null) {
            for (RoleDTO roleDTO : userDTO.getRoleDTOS()) {
                user.getRoles().add(roleService.dtoToEntity(roleDTO));
            }
        }
        if (userDTO.getAccountDTOS() != null) {
            for (AccountDTO accountDTO : userDTO.getAccountDTOS()) {
                user.addAccount(accountService.dtoToEntity(accountDTO));
            }
        }
        return user;
    }

    @Override
    public List<UserDTO> list() {
        List<UserDTO> list = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            list.add(new UserDTO(user));
        }
        return list;
    }

    @Override
    public UserDTO get(Long id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User with id: " + id + ", did not found!");
        }
        return new UserDTO(optionalUser.get());
    }

    @Override
    public UserDTO getByUsername(String username) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Did not found user with username: " + username);
        }
        return new UserDTO(optionalUser.get());
    }

    @Override
    public UserDTO getByTokenAndCode(String token, String code) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByTokenAndCode(token, Integer.valueOf(code));
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Did not found user with token: " + token + " and code: " + code);
        }
        return new UserDTO(optionalUser.get());
    }

    @Override
    public UserDTO getLoggedInUser() throws NotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return getByUsername(username);
    }

    @Override
    public UserDTO save(UserDTO userDTO) throws NotFoundException {
        return new UserDTO(userRepository.save(dtoToEntity(userDTO)));
    }

    @Override
    public UserDTO register(UserDTO userDTO) throws NotFoundException, MessagingException {

        // Check if passwords match
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            errorMessage = messageSource.getMessage("errors.registration.passwordMisMatch", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        // Generate and set token
        userDTO.setToken(Token.generateToken());
        // Generate and set code
        userDTO.setCode(RandomCodeGenerator.generateRandomCode());
        // Hash and set password
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Set verified and enabled
        userDTO.setVerified(false);
        userDTO.setEnabled(false);
        // Set role
        RoleDTO roleDTO = roleService.getByName(RoleName.ROLE_USER);
        userDTO.getRoleDTOS().add(roleDTO);
        // Register user
        UserDTO registeredUser = save(userDTO);
        // Check if registration was successful
        if (registeredUser != null) {
            // Send verification email
            sendVerificationEmail(registeredUser);
        }
        return registeredUser;
    }

    @Override
    public void verify(String token, String code) throws NotFoundException {
        UserDTO userDTO = getByTokenAndCode(token, code);

        userDTO.setToken(null);
        userDTO.setCode(null);
        userDTO.setVerified(true);
        userDTO.setVerifiedAt(LocalDateTime.now());
        userDTO.setEnabled(true);

        save(userDTO);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (get(id) != null) {
            userRepository.deleteById(id);
        }
    }

    private void sendVerificationEmail(UserDTO userDTO) throws MessagingException {
        // Get email body
        String emailBody = HTML.htmlEmailTemplate(userDTO.getToken(), String.valueOf(userDTO.getCode()));
        // Send email confirmation
        MailMessenger.htmlEmailMessenger(MailMessenger.emailFrom, userDTO.getPersonDTO().getEmail(), MailMessenger.emailTitle, emailBody);
    }
}
