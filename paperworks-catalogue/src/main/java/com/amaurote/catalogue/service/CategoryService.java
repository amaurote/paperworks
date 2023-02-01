package com.amaurote.catalogue.service;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

public interface CategoryService {

    void categorize(Book book, Long categoryId);
    void uncategorize(Book book);

    Category getCategoryById(Long id) throws CatalogueException;

    void createSingleCategory(CategoryCreateRequestDTO dto) throws CatalogueException;
    void buildCategoryPath(String path, Long parentId) throws CatalogueException;

    Map<Long, String> generateCategoryPathMap(Long categoryId) throws CatalogueException;
    String generateTree();

    @Data
    public class CategoryCreateRequestDTO {
        @NotEmpty
        @Pattern(regexp = "[a-z_]+")
        private String name;

        private String caption;

        private Long parentId;
    }

}
