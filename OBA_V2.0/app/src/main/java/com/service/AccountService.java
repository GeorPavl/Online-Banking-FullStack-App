package com.service;

import com.dto.AccountDTO;
import com.entity.Account;
import javassist.NotFoundException;

public interface AccountService {

    Account dtoToEntity(AccountDTO accountDTO) throws NotFoundException;

    AccountDTO get(Long id) throws NotFoundException;


    AccountDTO save(AccountDTO accountDTO) throws NotFoundException;

    AccountDTO createAccount(AccountDTO accountDTO) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
