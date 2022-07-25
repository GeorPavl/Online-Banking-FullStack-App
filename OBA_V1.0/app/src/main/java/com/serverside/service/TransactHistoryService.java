package com.serverside.service;

import com.serverside.model.TransactionHistory;

import java.util.List;

public interface TransactHistoryService {

    List<TransactionHistory> getTransactionRecordsById(int user_id);
}
