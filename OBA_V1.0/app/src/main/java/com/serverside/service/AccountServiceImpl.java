package com.serverside.service;

import com.serverside.model.Account;
import com.serverside.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getUserAccounts(int user_id) {
        return getUserAccounts(user_id);
    }

    @Override
    public BigDecimal getTotalBalance(int user_id) {
        return accountRepository.getTotalBalance(user_id);
    }
}
