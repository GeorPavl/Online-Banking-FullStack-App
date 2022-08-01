package com.dto;

import com._config._helpers._enums.TransactionSource;
import com._config._helpers._enums.TransactionStatus;
import com._config._helpers._enums.TransactionType;
import com.entity.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class TransactionDTO {

    private Long id;

    private Long accountId;

    private Double amount = 0.0;

    private TransactionType type;

    private TransactionStatus status;

    private String reasonCode;

    private TransactionSource source;

    private LocalDateTime createdAt;

    private PaymentDTO paymentDTO;

    public TransactionDTO(Transaction transaction) {
        BeanUtils.copyProperties(transaction, this);
        if (transaction.getAccount() != null) {
            this.accountId = transaction.getAccount().getId();
        }
        if (transaction.getPayment() != null) {
            this.paymentDTO = new PaymentDTO(transaction.getPayment());
        }
    }
}
