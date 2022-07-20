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
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void verifyAccount(String token, String code) {
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

        userRepository.save(updatedUser);
    }
}
