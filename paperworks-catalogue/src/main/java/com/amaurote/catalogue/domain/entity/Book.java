package com.amaurote.catalogue.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.catalogue.domain.utils.IdLongGenerator")
    @GeneratedValue(generator = "id")
    private Long id;

    @Column(name = "cat_id", nullable = false, unique = true)
    private Long catalogueId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_created")
    private Date dateCreated;

    @OneToMany
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authorship;

    @OneToOne
    private Publisher publisher;

    @OneToOne
    private Language language;



}
