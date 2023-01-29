package com.amaurote.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.domain.utils.IdLongGenerator")
    @GeneratedValue(generator = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "login_by_email", nullable = false)
    private boolean loginByEmail;

}
