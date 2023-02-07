package com.amaurote.catalog.service;

import com.amaurote.catalog.exception.CatalogException;
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

    Category getCategoryById(long id) throws CatalogException;
    List<Category> getAllCategories();
    List<Category> getChildCategories(Long parentId) throws CatalogException;

    void assign(Book book, long categoryId, boolean isMain) throws CatalogException;
    void unassign(Book book, long categoryId) throws CatalogException;
    void unassignAll(Book book) throws CatalogException;
    void toggleMainCategoryFlag(Book book, long categoryId, boolean isMain) throws CatalogException;

    void createSingleCategory(CategoryCreateRequestDTO dto) throws CatalogException;
    void buildCategoryPath(String path, Long parentId) throws CatalogException;

    Map<Long, String> generateCategoryPathMap(long categoryId) throws CatalogException;
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
