package com.serverside.service;

import com.serverside.model.TransactionHistory;
import com.serverside.repository.TransactHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactHistoryImpl implements TransactHistoryService{

    @Autowired
    private TransactHistoryRepository transactHistoryRepository;

    @Override
    public List<TransactionHistory> getTransactionRecordsById(int user_id) {
        return transactHistoryRepository.getTransactionRecordsById(user_id);
    }
}
