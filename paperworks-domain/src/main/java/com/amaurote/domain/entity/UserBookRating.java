package com.amaurote.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_book_ratings", uniqueConstraints = {@UniqueConstraint(columnNames = {"book", "reviewer"})})
public class UserBookRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reviewer", nullable = false)
    private User reviewer;

    @Column(nullable = false)
    private Integer score;

}
