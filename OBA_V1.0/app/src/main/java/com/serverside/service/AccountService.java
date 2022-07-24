package com.serverside.service;

import com.serverside.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> getUserAccounts(int user_id);

    BigDecimal getTotalBalance(int user_id);

    Account createAccount(Account account);
}
