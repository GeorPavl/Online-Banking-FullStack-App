package com._config._helpers;

import java.util.Random;

public class RandomCodeGenerator {

    public static Integer generateRandomCode() {
        Random random = new Random();
        int bound = 123;
        int code = bound * random.nextInt(bound);
        return code;
    }
}
