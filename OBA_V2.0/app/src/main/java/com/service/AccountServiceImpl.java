package com.service;

import com.dto.AccountDTO;
import com.dto.TransactionDTO;
import com.entity.Account;
import com.entity.User;
import com.repository.AccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    private static final Locale locale = LocaleContextHolder.getLocale();
    private static String errorMessage;

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
    public AccountDTO save(AccountDTO accountDTO) throws NotFoundException {
        return new AccountDTO(accountRepository.save(dtoToEntity(accountDTO)));
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) throws NotFoundException {
        return save(new AccountDTO(accountDTO.getName(), accountDTO.getType(), userService.getLoggedInUser().getId()));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (get(id) != null) {
            if (get(id).getBalance() > 0) {
                errorMessage = messageSource.getMessage("errors.account.delete", null, locale);
                throw new RuntimeException(errorMessage);
            }
            // TODO: 3/8/2022
            accountRepository.deleteById(id);
        } else {
            errorMessage = messageSource.getMessage("errors.account.delete", null, locale);
            throw new RuntimeException(errorMessage);
        }
    }
}
