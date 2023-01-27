package com.amaurote.mapper;

import com.amaurote.domain.entity.Publisher;
import com.amaurote.dto.PublisherDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public record PublisherDTOMapper() implements Function<Publisher, PublisherDTO> {

    @Override
    public PublisherDTO apply(Publisher publisher) {
        if (publisher == null)
            return null;

        return PublisherDTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .overview(publisher.getOverview())
                .build();
    }
}
