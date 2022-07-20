package com.serverside.service;

import com.serverside.model.User;

public interface UserService {

    void registerUser(User user);

    void verifyAccount(String token, String code);
}
