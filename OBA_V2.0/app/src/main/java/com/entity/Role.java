package com.entity;

import com._config._helpers._enums.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}
