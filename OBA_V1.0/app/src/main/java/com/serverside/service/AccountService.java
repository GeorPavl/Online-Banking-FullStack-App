package com.serverside.service;

import com.serverside.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> getUserAccounts(int user_id);

    BigDecimal getTotalBalance(int user_id);

    void createAccount(int user_id, String account_number, String account_name, String account_type);
}
