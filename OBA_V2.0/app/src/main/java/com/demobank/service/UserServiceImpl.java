package com.demobank.service;

import com.demobank._helpers.GenerateRandomCode;
import com.demobank._helpers.HTML;
import com.demobank._helpers.MailMessenger;
import com.demobank._helpers.Token;
import com.demobank.model.User;
import com.demobank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    private static final String emailFrom = "georpavloglou@gmail.com";
    private static final String emailTitle = "Account Verification";

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User user) throws MessagingException {
        // Get token string
        String token = Token.generateToken();
        user.setToken(token);

        // Generate random code
        Integer code = GenerateRandomCode.generateRandomCode();
        user.setCode(code);

        // Hash password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        // Set verified
        user.setVerified(false);

        // Register user
        User registeredUser = userRepository.save(user);
        // Check if registration was successful
        if (!registeredUser.equals(null)) {
            // Get email html body
            String emailBody = HTML.htmlEmailTemplate(token, Integer.toString(code));
            // Send email confirmation
            MailMessenger.htmlEmailMessenger(emailFrom, user.getEmail(), emailTitle, emailBody);
        }

        return registeredUser;
    }

    @Override
    public void verifyUser(String token, String code) {
        User user = findByTokenAndCode(token, code);

        user.setToken(null);
        user.setCode(null);
        user.setVerified(true);
//        user.setVerifiedAt(Date.valueOf(String.valueOf(LocalDateTime.now())));
        user.setVerifiedAt(LocalDateTime.now());

        save(user);
    }

    @Override
    public String checkToken(String token) {
        return userRepository.checkToken(token);
    }

    @Override
    public User findByTokenAndCode(String token, String code) {
        Optional<User> optionalUser = userRepository.findByTokenAndCode(token, Integer.valueOf(code));

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Did not found user with token: " + token + " and code: " + code);
        }
        return optionalUser.get();
    }
}
