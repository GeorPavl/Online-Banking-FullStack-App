package com.demobank.repository;

import com.demobank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT token FROM user WHERE token = :token" , nativeQuery = true)
    String checkToken(@Param("token")String token);

    Optional<User> findByTokenAndCode(String token, Integer code);
}
