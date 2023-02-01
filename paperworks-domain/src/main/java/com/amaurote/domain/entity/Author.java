package com.amaurote.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.domain.utils.IdLongGenerator")
    @GeneratedValue(generator = "id")
    private Long id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "bio")
    private String bio;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;
}
