package com.amaurote.catalog.service;

import com.amaurote.catalog.exception.CatalogException;
import com.amaurote.domain.entity.Language;

import java.util.List;

public interface LanguageService {

    List<Language> getAllLanguages();

    void addLanguage(String isoCode, String language) throws CatalogException;

}
