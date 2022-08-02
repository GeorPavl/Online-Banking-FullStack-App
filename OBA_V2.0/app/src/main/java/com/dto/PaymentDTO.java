package com.dto;

import com.entity.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter @Setter
@NoArgsConstructor
public class PaymentDTO {

    private Long id;

    private String beneficiary;

    private String beneficiaryAccountNumber;

    private String referenceNumber;

    private Long transactionId;

    public PaymentDTO(Payment payment) {
        BeanUtils.copyProperties(payment, this);
        if (payment.getTransaction().getId() != null) {
            this.transactionId = payment.getTransaction().getId();
        }
    }

    public PaymentDTO(String beneficiary, String beneficiaryAccountNumber, String referenceNumber) {
        this.beneficiary = beneficiary;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.referenceNumber = referenceNumber;
    }
}
