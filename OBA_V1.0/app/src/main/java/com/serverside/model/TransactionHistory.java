package com.serverside.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "v_transaction_history")
@Getter
@Setter
public class TransactionHistory {

    @Id
    private int transaction_id;
    private int account_id;
    private int user_id;
    private String transaction_type;
    private double amount;
    private String  source;
    private String  status;
    private String  reason_code;
    private LocalDateTime created_at;
}
