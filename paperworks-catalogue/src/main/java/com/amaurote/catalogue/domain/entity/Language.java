package com.amaurote.catalogue.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LANGUAGE")
public class Language {

    @Id // ISO 639-1
    @Column(length = 2)
    private String code;

    @Column(nullable = false, unique = true)
    private String language;

}