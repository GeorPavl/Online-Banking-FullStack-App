package com._config._mail;

import java.util.UUID;

public class Token {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
