package com.serverside.repository;

import com.serverside.model.Transact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactRepository extends JpaRepository<Transact, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO transaction_history(account_id, transaction_type, amount, source, status, reason_code)" +
            "VALUES(:account_id, :transact_type, :amount, :source, :status, :reason_code)", nativeQuery = true)
    void logTransaction(@Param("account_id")int account_id,
                        @Param("transact_type")String transact_type,
                        @Param("amount")double amount,
                        @Param("source")String source,
                        @Param("status")String status,
                        @Param("reason_code")String reason_code);
}
