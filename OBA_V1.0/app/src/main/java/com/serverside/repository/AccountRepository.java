package com.serverside.repository;

import com.serverside.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByUserId(int userId);

    @Query(value = "SELECT balance FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    BigDecimal getTotalBalance(int user_id);
}
