package com.amaurote.dto;

import com.amaurote.domain.entity.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookDTO {

    private long catalogueId;
    private String catalogueIdPretty;
    private String isbn10;
    private String isbn13;

    private String title;
    private String overview;

    private Integer yearPublished;

    private List<AuthorDTO> authorship;
    private PublisherDTO publisher;
    private Language language;

    private Integer pageCount;
    private Integer weight;

}
