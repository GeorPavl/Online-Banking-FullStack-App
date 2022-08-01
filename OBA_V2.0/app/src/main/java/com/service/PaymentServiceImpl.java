package com.service;

import com.dto.PaymentDTO;
import com.entity.Payment;
import com.entity.Transaction;
import com.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment dtoToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentDTO, payment);
        if (paymentDTO.getTransactionId() != null) {
            Transaction transaction = new Transaction();
            transaction.setId(paymentDTO.getTransactionId());
            payment.setTransaction(transaction);
        }
        return payment;
    }
}
