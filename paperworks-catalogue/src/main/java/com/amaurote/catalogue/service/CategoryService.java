package com.amaurote.catalogue.service;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    void assign(Book book, long categoryId, boolean isMain) throws CatalogueException;
    void unassign(Book book, long categoryId) throws CatalogueException;
    void unassignAll(Book book) throws CatalogueException;
    void toggleMainCategoryFlag(Book book, long categoryId, boolean isMain) throws CatalogueException;

    Category getCategoryById(long id) throws CatalogueException;
    List<Category> getChildCategories(Long parentId) throws CatalogueException;

    void createSingleCategory(CategoryCreateRequestDTO dto) throws CatalogueException;
    void buildCategoryPath(String path, Long parentId) throws CatalogueException;

    Map<Long, String> generateCategoryPathMap(long categoryId) throws CatalogueException;
    String generateTree();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class CategoryCreateRequestDTO {
        @NotEmpty
        @Pattern(regexp = "[a-z0-9_]+")
        private String name;

        private String caption;

        private Long parentId;
    }

}
