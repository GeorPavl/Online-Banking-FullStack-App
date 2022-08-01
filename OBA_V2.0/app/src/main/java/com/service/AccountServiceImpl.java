package com.service;

import com.dto.AccountDTO;
import com.dto.TransactionDTO;
import com.entity.Account;
import com.entity.User;
import com.repository.AccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;

    @Override
    public Account dtoToEntity(AccountDTO accountDTO) throws NotFoundException {
        Account account = new Account();
        BeanUtils.copyProperties(accountDTO, account);
        if (accountDTO.getUserId() != null) {
            User user = new User();
            user.setId(accountDTO.getUserId());
            account.setUser(user);
        }
        if (accountDTO.getTransactionDTOS() != null) {
            for (TransactionDTO transactionDTO : accountDTO.getTransactionDTOS()) {
                account.addTransaction(transactionService.dtoToEntity(transactionDTO));
            }
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
    public List<Account> getUserAccounts(Long id) {
        Optional<List<Account>> optionalAccounts = accountRepository.getUserAccountsById(id);
        if (optionalAccounts.isEmpty()) {
            throw new RuntimeException("Did not found accounts for this user id: " + id);
        }
        return optionalAccounts.get();
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

    @Override
    public BigDecimal getTotalBalance(Long user_id) {
        return accountRepository.getTotalBalance(user_id);
    }
}
