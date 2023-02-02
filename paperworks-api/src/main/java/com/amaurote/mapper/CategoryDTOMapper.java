package com.amaurote.mapper;

import com.amaurote.domain.entity.Category;
import com.amaurote.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.function.Function;

@Service
public record CategoryDTOMapper() implements Function<Category, CategoryDTO> {

    @Override
    public CategoryDTO apply(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .caption(category.getCaption())
                .parent((category.getParent() == null)
                        ? null : Collections.singletonMap(
                        category.getParent().getId(),
                        category.getParent().getName()))
                .build();
    }
}
