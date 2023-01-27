package com.amaurote.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language")
public class Language {

    @Id // ISO 639-1
    @Column(length = 2)
    private String code;

    @Column(length = 25, nullable = false, unique = true)
    private String language;

}
