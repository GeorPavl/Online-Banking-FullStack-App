package com.serverside.service;

import com.serverside.model.PaymentHistory;

import java.util.List;

public interface PaymentHistoryService {

    List<PaymentHistory> getPaymentRecordsById(int user_id);
}
