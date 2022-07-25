package com.serverside.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {

    @Id
    private int payment_id;
    private int account_id;
    private String beneficiary;
    private String beneficiary_acc_no;
    private double amount;
    private String reference_no;
    private String status;
    private String reason_code;
    private LocalDateTime created_at;
}
