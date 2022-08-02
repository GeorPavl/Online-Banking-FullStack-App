package com.repository;

import com.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value =
            "SELECT DISTINCT t.id, t.accounts_id, t.amount, t.type, t.status, t.reason_code, t.source, t.created_at, p.beneficiary, p.beneficiary_account_number, p.reference_number\n" +
            "FROM users u, accounts a, transactions t, payments p\n" +
            "WHERE u.id = a.users_id and a.id = t.accounts_id and u.id = :userId\n" +
            "ORDER BY created_at DESC", nativeQuery = true)
    List<Transaction> getTransactionsById(@Param("userId")Long userId);
}
