package com.serverside.service;

public interface TransactService {

    void logTransaction(int account_id, String transactType, double amount, String source, String status, String reasonCode);
}
