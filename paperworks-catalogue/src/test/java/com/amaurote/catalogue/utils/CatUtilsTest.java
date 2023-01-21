package com.amaurote.catalogue.utils;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatUtilsTest {

    @Test
    void generateCatalogueNumber() {
        var catNumber = CatUtils.generateCatalogueNumber();
        assertTrue(StringUtils.isNotBlank(catNumber));
        assertEquals(9, catNumber.length());
    }
}