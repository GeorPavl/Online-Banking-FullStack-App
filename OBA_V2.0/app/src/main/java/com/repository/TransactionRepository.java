package com.repository;

import com.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transactions t, users u, accounts a " +
                   "WHERE u.id = a.users_id AND a.id = t.accounts_id AND u.id = :userId " +
                   "ORDER BY t.created_at DESC", nativeQuery = true)
    List<Transaction> getTransactionsById(@Param("userId")Long userId);
}
