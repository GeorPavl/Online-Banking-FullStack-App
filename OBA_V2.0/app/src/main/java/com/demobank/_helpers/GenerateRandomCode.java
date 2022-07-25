package com.demobank._helpers;

import java.util.Random;

public class GenerateRandomCode {

    public static Integer generateRandomCode() {
        Random random = new Random();
        int bound = 123;
        int code = bound * random.nextInt(bound);
        return code;
    }
}
