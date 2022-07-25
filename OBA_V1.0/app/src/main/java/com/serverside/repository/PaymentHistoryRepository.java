package com.serverside.repository;

import com.serverside.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Integer> {

    @Query(value = "SELECT * FROM v_payments WHERE user_id = :user_id", nativeQuery = true)
    List<PaymentHistory> getPaymentRecordsById(@Param("user_id")int user_id);
}
