package com.serverside.service;

import com.serverside.model.User;

public interface UserService {

    void registerUser(String firstName, String lastName, String email, String password, String token, int code);

    User verifyAccount(String token, String code);

    String checkToken(String token);

    String checkEmail(String email);

    String checkPassword(String email);

    int isVerified(String email);

    User getUserDetails(String email);
}
