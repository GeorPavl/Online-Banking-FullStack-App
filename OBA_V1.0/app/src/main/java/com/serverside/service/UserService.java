package com.serverside.service;

import com.serverside.model.User;

public interface UserService {

    User registerUser(User user);

    User verifyAccount(String token, String code);

    String checkToken(String token);

    String checkEmail(String email);

    String checkPassword(String email);

    int isVerified(String email);

    User getUserDetails(String email);
}
