package com.amaurote.catalogue.service.impl;

import com.amaurote.catalogue.repository.AuthorRepository;
import com.amaurote.catalogue.service.AuthorService;
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
