package com.amaurote.catalogue.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PUBLISHERS")
public class Publisher {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.catalogue.domain.utils.IdLongGenerator")
    @GeneratedValue(generator = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}