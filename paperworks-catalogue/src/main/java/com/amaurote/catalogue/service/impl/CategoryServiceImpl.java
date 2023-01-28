package com.amaurote.catalogue.service.impl;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.helper.CategoryTreeBuilder;
import com.amaurote.catalogue.repository.CategoryRepository;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.domain.entity.Category;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    @Transactional
    public void buildCategoryPath(String path) throws CatalogueException {
        var names = StringUtils.split(path, '.');

        var parent = repository.findById(1L).orElseThrow(() -> new CatalogueException("Can't find root category"));
        for (String name : names) {
            var existing = repository.findByParentAndName(parent, name).orElse(null);
            if (existing != null) {
                parent = existing;
            } else {
                var category = new Category();
                category.setParent(parent);
                category.setName(name);
                parent = repository.save(category);
            }
        }
    }

    @Override
    public String generateTree() {
        var allCategories = repository.findAll();
        var treeBuilder = new CategoryTreeBuilder(allCategories);
        return treeBuilder.build();
    }

}
