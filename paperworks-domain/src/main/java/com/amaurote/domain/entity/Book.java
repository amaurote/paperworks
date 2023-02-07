package com.amaurote.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.domain.utils.IdLongGenerator")
    @GeneratedValue(generator = "id")
    private Long id;

    @Column(name = "catalog_id", nullable = false, unique = true)
    private Long catalogId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "overview")
    private String overview;

    @Column(name = "isbn_10", length = 10)
    private String isbn10;

    @Column(name = "isbn_13", length = 13)
    private String isbn13;

    @Column(name = "year_published", length = 4)
    private Integer yearPublished;

    @OneToOne
    private Publisher publisher;

    @OneToOne
    private Language language;

    @Column(name = "page_count")
    private Integer pages;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "date_created")
    private Instant dateCreated;

    @OneToMany
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authorship;

    @OneToMany(mappedBy = "book")
    private List<BookCategory> categories;


}
