package com.serverside.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "v_payments")
@Getter
@Setter
public class PaymentHistory {

    @Id
    private int payment_id;
    private int account_id;
    private int user_id;
    private String beneficiary;
    private String beneficiary_acc_no;
    private double amount;
    private String status;
    private String reference_no;
    private String reason_code;
    private LocalDateTime created_at;
}
