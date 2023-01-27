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
@Table(name = "publishers")
public class Publisher {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.domain.utils.IdLongGenerator")
    @GeneratedValue(generator = "id")
    private Long id;

    @Column(name = "publisher", nullable = false)
    private String name;

    @Column
    private String overview;

}
