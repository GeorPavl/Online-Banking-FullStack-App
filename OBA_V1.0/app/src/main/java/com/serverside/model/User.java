package com.serverside.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "first_name")
    @NotBlank(message = "The 'First Name' field is required.")
    @Size(min = 3, message = "The First name field must be greater that 3 characters.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "The 'Last Name' field is required.")
    @Size(min = 3, message = "The 'Last Name' field must be greater than 3 characters.")
    private String lastName;

    @Column(name = "email")
    @Email
    @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})",
            message = "Please Enter a valid email.")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "The 'Password' field is required.")
    private String password;

    private String token;

    private String code;

    private int verified;

    private LocalDate verified_at;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    public User(String firstName, String lastName, String email, String hashedPassword, String token, String code) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(hashedPassword);
        this.setToken(token);
        this.setCode(code);
    }
}
