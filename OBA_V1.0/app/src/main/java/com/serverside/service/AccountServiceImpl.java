package com.serverside.service;

import com.serverside.model.Account;
import com.serverside.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getUserAccounts(int user_id) {
        Optional<List<Account>> optionalAccounts = accountRepository.getUserAccountsById(user_id);
        if (optionalAccounts.isEmpty()) {
            throw new RuntimeException("Did not found accounts for this user id: " + user_id);
        }
        return optionalAccounts.get();
    }

    @Override
    public BigDecimal getTotalBalance(int user_id) {
        return accountRepository.getTotalBalance(user_id);
    }

    @Override
    public void createAccount(int user_id, String account_number, String account_name, String account_type) {
        accountRepository.createBankAccount(user_id, account_number, account_name, account_type);
    }
}
