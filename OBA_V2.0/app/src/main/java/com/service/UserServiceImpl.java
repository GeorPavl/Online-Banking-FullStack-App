package com.service;

import com.dto.AccountDTO;
import com.dto.RoleDTO;
import com.dto.UserDTO;
import com.entity.User;
import com.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public UserDTO save(UserDTO userDTO) throws NotFoundException {
        return new UserDTO(userRepository.save(dtoToEntity(userDTO)));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (get(id) != null) {
            userRepository.deleteById(id);
        }
    }
}
