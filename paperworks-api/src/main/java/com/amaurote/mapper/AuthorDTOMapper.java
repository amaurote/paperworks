package com.amaurote.mapper;

import com.amaurote.domain.entity.Author;
import com.amaurote.dto.AuthorDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public record AuthorDTOMapper() implements Function<Author, AuthorDTO> {

    @Override
    public AuthorDTO apply(Author author) {
        if (author == null)
            return null;

        return AuthorDTO.builder()
                .id(author.getId())
                .displayName(getDisplayName(author))
                .bio(author.getBio())
                .build();
    }

    private String getDisplayName(Author author) {
        if(StringUtils.isNotBlank(author.getDisplayName()))
            return author.getDisplayName();

        StringBuilder sb = new StringBuilder();
        sb.append(author.getFirstName()).append(' ');
        if (StringUtils.isNotBlank(author.getMiddleName()))
            sb.append(author.getMiddleName()).append(' ');
        sb.append(author.getLastName());

        return sb.toString();
    }
}
