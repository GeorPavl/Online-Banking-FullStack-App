package com.demobank.service;

import com.demobank.model.User;

import javax.mail.MessagingException;

public interface UserService {

    User save(User user);

    User registerUser(User user) throws MessagingException;

    void verifyUser(String token, String code);

    String checkToken(String token);

    User findByTokenAndCode(String token, String code);

    User findByEmail(String email);
}
