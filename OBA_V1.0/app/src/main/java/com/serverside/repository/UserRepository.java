package com.serverside.repository;

import com.serverside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByTokenAndCode(String token, String code);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT token FROM users WHERE token = :token" , nativeQuery = true)
    String checkToken(@Param("token")String token);

    @Query(value = "SELECT email FROM users WHERE email = :email", nativeQuery = true)
    String getUserEmail(@Param("email") String email);

    @Query(value = "SELECT password FROM users WHERE email = :email", nativeQuery = true)
    String getUserPassword(@Param("email") String email);

    @Query(value = "SELECT verified FROM users WHERE email = :email", nativeQuery = true)
    int isVerified(@Param("email") String email);

    @Modifying
    @Query(value = "INSERT INTO users (first_name, last_name, email, password, token, code) VALUES" +
            "(:first_name, :last_name, :email, :password, :token, :code)", nativeQuery = true )
    @Transactional
    void registerUser(@Param("first_name")String first_name,
                      @Param("last_name") String last_name,
                      @Param("email")String email,
                      @Param("password")String password,
                      @Param("token") String token,
                      @Param("code")int code);
}
