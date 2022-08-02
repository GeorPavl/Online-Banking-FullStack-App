package com.service;

import com.dto.AccountDTO;
import com.dto.TransactionDTO;
import com.dto.UserDTO;
import com.entity.Account;
import com.entity.User;
import com.repository.AccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        UserDTO userDTO = userService.getByUsername(username);

        // Check for empty strings
        if (accountDTO.getName() == null || accountDTO.getName().isEmpty()) {
            errorMessage = messageSource.getMessage("errors.account.emptyName", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (accountDTO.getType() == null) {
            errorMessage = messageSource.getMessage("errors.account.emptyType", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }
        return save(new AccountDTO(accountDTO.getName(), accountDTO.getType(), userDTO.getId()));
    }

    // TODO: 1/8/2022 Check if account's balance > 0
    //  if account's balance > 0   ->  user must transfer or withraw money
    @Override
    public void delete(Long id) throws NotFoundException {
        if (get(id) != null) {
            accountRepository.deleteById(id);
        }
    }
}
