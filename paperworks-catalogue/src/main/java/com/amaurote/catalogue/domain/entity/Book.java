package com.amaurote.catalogue.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BOOKS")
@Data
public class Book {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "id", strategy = "com.amaurote.catalogue.domain.utils.IdLongGenerator")
    private Long id;

    @Column(name = "cat_id", nullable = false, unique = true)
    private String catalogueId;

    @Column(name = "name", nullable = false)
    private String name;

}
