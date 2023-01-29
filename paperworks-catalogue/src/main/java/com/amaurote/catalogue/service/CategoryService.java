package com.amaurote.catalogue.service;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.domain.entity.Category;

import java.util.Map;

public interface CategoryService {

    void buildCategoryPath(String path) throws CatalogueException;

    Map<Long, String> generateCategoryPathMap(Category category);

    String generateTree();

}
