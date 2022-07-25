package com.serverside.service;

import com.serverside.repository.TransactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactServiceImpl implements TransactService{

    @Autowired
    private TransactRepository transactRepository;

    @Override
    public void logTransaction(int account_id, String transactType, double amount, String source, String status, String reasonCode) {
        transactRepository.logTransaction(account_id, transactType, amount, source, status, reasonCode);
    }
}
