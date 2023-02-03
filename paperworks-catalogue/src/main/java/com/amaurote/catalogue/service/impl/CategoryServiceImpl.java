package com.amaurote.catalogue.service.impl;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.helper.CategoryTreeBuilder;
import com.amaurote.catalogue.repository.BookCategoryRepository;
import com.amaurote.catalogue.repository.CategoryRepository;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.BookCategory;
import com.amaurote.domain.entity.Category;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final BookCategoryRepository bookCategoryRepository;

    @Override
    @Transactional
    public void assign(Book book, long categoryId, boolean isMain) throws CatalogueException {
        if (book == null)
            throw new CatalogueException("Book cannot be null");

        var category = getCategoryById(categoryId);

        if (bookCategoryRepository.existsByBookAndCategory(book, category))
            throw new CatalogueException("Category is already assigned");

        if (isMain) {
            makeAllBookCategoriesNotMain(book);
        }

        var bookCategoryToSave = new BookCategory();
        bookCategoryToSave.setBook(book);
        bookCategoryToSave.setCategory(category);
        bookCategoryToSave.setMainCategory(isMain);
        bookCategoryRepository.save(bookCategoryToSave);
    }

    @Override
    public void unassign(Book book, long categoryId) throws CatalogueException {
        if (book == null)
            throw new CatalogueException("Book cannot be null");

        var category = getCategoryById(categoryId);

        bookCategoryRepository.deleteByBookAndCategory(book, category);
        bookCategoryRepository.flush();
    }

    @Override
    public void unassignAll(Book book) throws CatalogueException {
        if (book == null)
            throw new CatalogueException("Book cannot be null");

        bookCategoryRepository.deleteAllByBook(book);
        bookCategoryRepository.flush();
    }

    @Override
    @Transactional
    public void toggleMainCategoryFlag(Book book, long categoryId, boolean isMain) throws CatalogueException {
        if (book == null)
            throw new CatalogueException("Book cannot be null");

        var category = getCategoryById(categoryId);

        var bookCategory = bookCategoryRepository.findByBookAndCategory(book, category)
                .orElseThrow(() -> new CatalogueException("The book does not have this category"));

        if (!bookCategory.isMainCategory() && isMain)
            makeAllBookCategoriesNotMain(book);

        bookCategory.setMainCategory(isMain);
        bookCategoryRepository.save(bookCategory);
    }

    @Override
    public Category getCategoryById(long id) throws CatalogueException {
        return categoryRepository.findById(id).orElseThrow(() -> new CatalogueException("Category does not exist"));
    }

    @Override
    public List<Category> getChildCategories(Long parentId) throws CatalogueException {
        var parent = getParentCategoryById(parentId);
        return categoryRepository.findCategoriesByParent(parent);
    }

    @Override
    @Transactional
    public void createSingleCategory(CategoryCreateRequestDTO dto) throws CatalogueException {
        var parent = getParentCategoryById(dto.getParentId());

        var newCategory = Category.builder()
                .name(dto.getName())
                .caption(dto.getCaption())
                .parent(parent)
                .build();

        categoryRepository.save(newCategory);
    }

    @Override
    @Transactional
    public void buildCategoryPath(String path, Long parentId) throws CatalogueException {
        var names = StringUtils.split(path, '.');

        var parent = getParentCategoryById(parentId);

        for (String name : names) {
            var existing = categoryRepository.findByParentAndName(parent, name).orElse(null);
            if (existing != null) {
                parent = existing;
            } else {
                var category = new Category();
                category.setParent(parent);
                category.setName(name);
                parent = categoryRepository.save(category);
            }
        }
    }

    @Override
    public Map<Long, String> generateCategoryPathMap(long categoryId) throws CatalogueException {
        Map<Long, String> pathMap = new HashMap<>();

        var category = getCategoryById(categoryId);

        pathMap.put(category.getId(), StringUtils.defaultIfEmpty(category.getCaption(), category.getName()));
        var parent = category.getParent();
        while (parent != null) {
            pathMap.put(parent.getId(), StringUtils.defaultIfEmpty(parent.getCaption(), parent.getName()));
            parent = parent.getParent();
        }

        return pathMap;
    }

    @Override
    public String generateTree() {
        var allCategories = categoryRepository.findAll();
        var treeBuilder = new CategoryTreeBuilder(allCategories);
        return treeBuilder.build();
    }

    private void makeAllBookCategoriesNotMain(Book book) {
        var categories = bookCategoryRepository.findAllByBook(book);

        for (var category : categories) {
            category.setMainCategory(false);
        }

        bookCategoryRepository.saveAll(categories);
    }

    private Category getParentCategoryById(Long id) throws CatalogueException {
        return (id == null)
                ? null
                : categoryRepository.findById(id)
                .orElseThrow(() -> new CatalogueException("Parent category does not exist"));
    }

}
