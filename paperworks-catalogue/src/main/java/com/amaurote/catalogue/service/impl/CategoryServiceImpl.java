package com.amaurote.catalogue.service.impl;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.helper.CategoryTreeBuilder;
import com.amaurote.catalogue.repository.BookCategoryRepository;
import com.amaurote.catalogue.repository.CategoryRepository;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.domain.entity.Category;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final BookCategoryRepository bookCategoryRepository;

    @Override
    @Transactional
    public void buildCategoryPath(String path) throws CatalogueException {
        var names = StringUtils.split(path, '.');

        var parent = categoryRepository.findById(1L).orElseThrow(() -> new CatalogueException("Can't find root category"));     // todo rework
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
    public Map<Long, String> generateCategoryPathMap(Category category) {
        Map<Long, String> pathMap = new HashMap<>();

        pathMap.put(category.getId(), StringUtils.defaultIfEmpty(category.getCaption(), category.getName()));
        var parent = category.getParent();
        while (parent != null) {
            pathMap.put(parent.getId(), StringUtils.defaultIfEmpty(parent.getCaption(), parent.getName()));
            parent = parent.getParent();
        }

        return pathMap;     // todo test this out
    }

    @Override
    public String generateTree() {
        var allCategories = categoryRepository.findAll();
        var treeBuilder = new CategoryTreeBuilder(allCategories);
        return treeBuilder.build();
    }

}
