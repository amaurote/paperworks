package com.amaurote.catalogue.service;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.domain.entity.Language;

import java.util.List;

public interface LanguageService {

    List<Language> getAllLanguages();

    void addLanguage(String isoCode, String language) throws CatalogueException;

}
