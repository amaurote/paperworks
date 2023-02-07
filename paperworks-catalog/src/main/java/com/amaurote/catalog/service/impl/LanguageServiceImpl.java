package com.amaurote.catalog.service.impl;

import com.amaurote.catalog.exception.CatalogException;
import com.amaurote.catalog.repository.LanguageRepository;
import com.amaurote.catalog.service.LanguageService;
import com.amaurote.domain.entity.Language;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record LanguageServiceImpl(LanguageRepository repository) implements LanguageService {

    @Override
    public List<Language> getAllLanguages() {
        return repository.findAll();
    }

    @Override
    public void addLanguage(String code, String language) throws CatalogException {
        if(code == null || code.length() != 2)
            throw new CatalogException("ISO 639-1 code should have length of 2");

        if(StringUtils.isBlank(language))
            throw new CatalogException("Invalid language name");

        repository.save(new Language(code, language));
    }
}
