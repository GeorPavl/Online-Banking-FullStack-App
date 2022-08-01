package com.dto;

import com.entity.Account;
import com.entity.Role;
import com.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private Boolean verified;

    private LocalDateTime verifiedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String token;

    private Integer code;

    private PersonDTO personDTO;

    private List<RoleDTO> roleDTOS = new ArrayList<>();

    private List<AccountDTO> accountDTOS = new ArrayList<>();

    private Double totalBalance = 0.0;

    public UserDTO(User user) {
        BeanUtils.copyProperties(user, this);
        if (user.getPerson() != null) {
            this.personDTO = new PersonDTO(user.getPerson());
        }
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                roleDTOS.add(new RoleDTO(role));
            }
        }
        if (user.getAccounts() != null) {
            for (Account account : user.getAccounts()) {
                accountDTOS.add(new AccountDTO(account));
                this.totalBalance += account.getBalance();
            }

        }
    }
}
