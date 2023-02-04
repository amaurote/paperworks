package com.amaurote.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    @Column(name = "role_desc")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = Collections.emptySet();

}
