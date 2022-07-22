package com.serverside.service;

import com.serverside.model.User;
import com.serverside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User verifyAccount(String token, String code) {
        Optional<User> optionalUser = userRepository.findByTokenAndCode(token, code);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("This account didn't found for verification");
        }

        User updatedUser = optionalUser.get();
        updatedUser.setToken(null);
        updatedUser.setCode(null);
        updatedUser.setVerified(1);
        updatedUser.setVerified_at(LocalDate.now());
        updatedUser.setUpdated_at(LocalDateTime.now());

        return userRepository.save(updatedUser);
    }

    @Override
    public String checkToken(String token) {
        return userRepository.checkToken(token);
    }

    @Override
    public String checkEmail(String email) {
        return userRepository.getUserEmail(email);
    }

    @Override
    public String checkPassword(String email) {
        return userRepository.getUserPassword(email);
    }

    @Override
    public int isVerified(String email) {
        return userRepository.isVerified(email);
    }

    @Override
    public User getUserDetails(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser == null) {
            throw new RuntimeException("Did not found user with email: " + email);
        }
        return optionalUser.get();
    }
}
