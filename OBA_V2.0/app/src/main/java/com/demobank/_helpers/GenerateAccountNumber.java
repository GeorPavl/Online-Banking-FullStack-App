package com.demobank._helpers;

import java.util.Random;

public class GenerateAccountNumber {

    public static int generateAccountNumber() {
        int accountNumber;
        Random random = new Random();
        int bound = 1000;
        accountNumber = bound * random.nextInt();
        if (accountNumber < 0) return -accountNumber;
        return accountNumber;
    }
}
