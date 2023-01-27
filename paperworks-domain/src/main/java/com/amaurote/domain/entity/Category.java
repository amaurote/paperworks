package com.amaurote.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "parent"})})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "caption")
    private String caption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    private Category parent;

}
