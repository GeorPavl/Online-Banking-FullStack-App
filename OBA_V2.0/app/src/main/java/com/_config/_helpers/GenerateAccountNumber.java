package com._config._helpers;

import java.util.Random;

public class GenerateAccountNumber {

    public static Integer generateAccountNumber() {
        int accountNumber;
        Random random = new Random();
        int bound = 1000;
        accountNumber = bound * random.nextInt();
        if (accountNumber < 0) return -accountNumber;
        return accountNumber;
    }
}
