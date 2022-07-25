package com.serverside.repository;

import com.serverside.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Modifying
    @Query(value = " INSERT INTO payments(account_id, beneficiary, beneficiary_acc_no, amount, reference_no, status, reason_code) " +
            "VALUES(:account_id,:beneficiary, :beneficiary_acc_no, :amount, :reference_no, :status, :reason_code)", nativeQuery = true)
    @Transactional
    void makePayment(@Param("account_id") int account_id,
                     @Param("beneficiary") String beneficiary,
                     @Param("beneficiary_acc_no") String beneficiary_acc_no,
                     @Param("amount") double amount,
                     @Param("reference_no") String reference_no,
                     @Param("status") String status,
                     @Param("reason_code") String reason_code);
}
