package com.service;

import com.dto.AccountDTO;
import com.entity.Account;
import com.entity.User;
import com.repository.AccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account dtoToEntity(AccountDTO accountDTO) throws NotFoundException {
        Account account = new Account();
        BeanUtils.copyProperties(accountDTO, account);
        if (accountDTO.getUserId() != null) {
            User user = new User();
            user.setId(accountDTO.getUserId());
            account.setUser(user);
        }
        return account;
    }

    @Override
    public AccountDTO get(Long id) throws NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Did not found account with id: " + id);
        }
        return new AccountDTO(optionalAccount.get());
    }

    @Override
    public AccountDTO save(AccountDTO accountDTO) throws NotFoundException {
        return new AccountDTO(accountRepository.save(dtoToEntity(accountDTO)));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (get(id) != null) {
            accountRepository.deleteById(id);
        }
    }
}
