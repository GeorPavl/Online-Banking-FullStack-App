package com.dto;

import com._config._helpers._enums.AccountType;
import com.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

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

    public AccountDTO(Account account) {
        BeanUtils.copyProperties(account, this);
        if (account.getUser() != null) {
            this.userId = account.getUser().getId();
        }
    }
}
