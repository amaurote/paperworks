package com.amaurote.catalogue.service;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

public interface CategoryService {

    void categorize(Book book, long categoryId, boolean isMain) throws CatalogueException;
    void uncategorize(Book book, long categoryId) throws CatalogueException;
    void uncategorizeAll(Book book) throws CatalogueException;
    void toggleBookMainCategoryFlag(Book book, long categoryId, boolean isMain) throws CatalogueException;

    Category getCategoryById(long id) throws CatalogueException;

    void createSingleCategory(CategoryCreateRequestDTO dto) throws CatalogueException;
    void buildCategoryPath(String path, Long parentId) throws CatalogueException;

    Map<Long, String> generateCategoryPathMap(long categoryId) throws CatalogueException;
    String generateTree();

    @Data
    class CategoryCreateRequestDTO {
        @NotEmpty
        @Pattern(regexp = "[a-z0-9_]+")
        private String name;

        private String caption;

        private Long parentId;
    }

}
