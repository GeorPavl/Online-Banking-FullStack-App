package com.serverside.service;

import com.serverside.model.PaymentHistory;
import com.serverside.repository.PaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService{

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Override
    public List<PaymentHistory> getPaymentRecordsById(int user_id) {
        return paymentHistoryRepository.getPaymentRecordsById(user_id);
    }
}
