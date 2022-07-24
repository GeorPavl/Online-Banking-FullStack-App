package com.serverside.repository;

import com.serverside.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT * FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    Optional<List<Account>> getUserAccountsById(@Param("user_id")int user_id);

    @Query(value = "SELECT sum(balance) FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    BigDecimal getTotalBalance(int user_id);

    @Modifying
    @Query(value = "INSERT INTO accounts(user_id, account_number, account_name, account_type) VALUES" +
            "(:user_id, :account_number, :account_name,:account_type )", nativeQuery = true)
    @Transactional
    void createBankAccount(@Param("user_id") int user_id,
                           @Param("account_number") String account_number,
                           @Param("account_name") String account_name,
                           @Param("account_type")String account_type);

    @Query(value = "SELECT balance FROM accounts WHERE user_id = :user_id AND account_id = :account_id", nativeQuery = true)
    double getAccountBalance(@Param("user_id") int user_id,
                             @Param("account_id") int account_id);

    @Modifying
    @Query(value ="UPDATE accounts SET balance = :new_balance WHERE account_id = :account_id" , nativeQuery = true)
    @Transactional
    void changeAccountBalanceById(@Param("new_balance") double new_balance, @Param("account_id") int account_id);
}
