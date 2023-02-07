package com.amaurote.catalog.service.impl;

import com.amaurote.catalog.repository.AuthorRepository;
import com.amaurote.catalog.service.AuthorService;
import com.amaurote.domain.entity.Author;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public record AuthorServiceImpl(AuthorRepository repository) implements AuthorService {

    @Override
    public void addNewAuthor(AuthorCreateRequestDTO dto) {
        throw new NotImplementedException();
    }

    @Override
    public Author getAuthorById() {
        throw new NotImplementedException();
    }
}
