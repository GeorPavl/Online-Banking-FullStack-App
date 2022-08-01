package com.entity;

import com._config._helpers._enums.TransactionSource;
import com._config._helpers._enums.TransactionStatus;
import com._config._helpers._enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accounts_id")
    private Account account;

    @Column(name = "amount")
    private Double amount = 0.0;

    @Column(name = "type")
    private TransactionType type;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "reason_code")
    private String reasonCode;

    @Column(name = "source")
    private TransactionSource source;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payments_id", referencedColumnName = "id")
    private Payment payment;
}
