package com.demobank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "{errors.user.firstNameEmpty}")
    @Size(min = 3, message = "")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "")
    @Size(min = 3, message = "")
    private String lastName;

    @Column(name = "email")
    @Email
    @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})",
            message = "")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "The 'Password' field is required.")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "code")
    private String code;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "verified_at")
    @UpdateTimestamp
    private Date verifiedAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
