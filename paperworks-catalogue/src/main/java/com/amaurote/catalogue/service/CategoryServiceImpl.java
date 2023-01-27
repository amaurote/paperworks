package com.amaurote.catalogue.service;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.repository.CategoryRepository;
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
            if(existing != null) {
                parent = existing;
            } else {
                var category = new Category();
                category.setParent(parent);
                category.setName(name);
                parent = repository.save(category);
            }
        }
    }


}
