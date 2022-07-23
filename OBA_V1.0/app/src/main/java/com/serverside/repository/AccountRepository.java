package com.serverside.repository;

import com.serverside.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT * FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    Optional<List<Account>> getUserAccountsById(@Param("user_id")int user_id);

    @Query(value = "SELECT balance FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    BigDecimal getTotalBalance(int user_id);
}
