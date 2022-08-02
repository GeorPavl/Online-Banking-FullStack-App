package com.dto;

import com._config._helpers.GenerateAccountNumber;
import com._config._helpers._enums.AccountType;
import com.entity.Account;
import com.entity.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AccountDTO {

    private Long id;

    private String number;

    private String name;

    private AccountType type;

    private Double balance = 0.0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;

    private List<TransactionDTO> transactionDTOS = new ArrayList<>();

    public AccountDTO(Account account) {
        BeanUtils.copyProperties(account, this);
        if (account.getUser() != null) {
            this.userId = account.getUser().getId();
        }
        if (account.getTransactions() != null) {
            for (Transaction transaction : account.getTransactions()) {
                this.transactionDTOS.add(new TransactionDTO(transaction));
            }
        }
    }

    public AccountDTO(String name, AccountType type, Long userId) {
        this.number = String.valueOf(GenerateAccountNumber.generateAccountNumber());
        this.name = name;
        this.type = type;
        this.userId = userId;
    }

    public void deposit(Double depositAmount) {
        this.balance += depositAmount;
    }

    public void withdraw(Double withdrawAmount) {
        this.balance -= withdrawAmount;
    }

    public void transfer(Double transferAmount, AccountDTO transferTo) {
        this.balance -= transferAmount;
        transferTo.balance += transferAmount;
    }

    public void payment(Double paymentAmount) {
        this.balance -= paymentAmount;
    }
}
