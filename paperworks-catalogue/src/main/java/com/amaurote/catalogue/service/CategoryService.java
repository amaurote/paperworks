package com.amaurote.catalogue.service;

import com.amaurote.catalogue.exception.CatalogueException;

public interface CategoryService {

    void buildCategoryPath(String path) throws CatalogueException;

}
