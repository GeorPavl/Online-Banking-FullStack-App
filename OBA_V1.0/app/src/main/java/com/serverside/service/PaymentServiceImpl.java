package com.serverside.service;

import com.serverside.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void makePayment(int account_id, String beneficiary, String beneficiary_acc_no, double amount, String reference_no, String status, String reason_code) {
        paymentRepository.makePayment(account_id, beneficiary, beneficiary_acc_no, amount, reference_no, status, reason_code);
    }
}
