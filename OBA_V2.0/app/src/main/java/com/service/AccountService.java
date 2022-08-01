package com.service;

import com.dto.AccountDTO;
import com.entity.Account;
import javassist.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account dtoToEntity(AccountDTO accountDTO) throws NotFoundException;

    AccountDTO get(Long id) throws NotFoundException;

    List<Account> getUserAccounts(Long id);

    AccountDTO save(AccountDTO accountDTO) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    BigDecimal getTotalBalance(Long user_id);
}
