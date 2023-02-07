package com.amaurote.catalog.service;

import com.amaurote.domain.entity.Author;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.Instant;

public interface AuthorService {

    void addNewAuthor(AuthorCreateRequestDTO dto);

    Author getAuthorById();

    @Data
    class AuthorCreateRequestDTO {
        private String displayName;

        @NotEmpty(message = "firstName is a mandatory field")
        private String firstName;

        @NotEmpty(message = "lastName is a mandatory field")
        private String lastName;

        private String middleName;
        private String bio;
        private Instant dateOfBirth;
    }
}
