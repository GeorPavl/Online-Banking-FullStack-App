package com.serverside.service;

public interface PaymentService {

    void makePayment(int account_id, String beneficiary, String beneficiary_acc_no, double amount, String reference_no, String status, String reason_code);
}
