package com.amaurote.catalogue.utils;

import com.amaurote.catalogue.exception.CatalogueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CatUtilsTest {

    @Test
    public void generateCatalogueNumber() {
        var catNumber = CatUtils.generateCatalogueNumber9();
        assertTrue(catNumber >= 0 && catNumber <= 999_999_999);
    }

    @Test
    public void prettifyCatalogueNumber() throws CatalogueException {
        var prettyCatNumber1 = CatUtils.prettifyCatalogueNumber9(0);
        assertEquals("000-000-000", prettyCatNumber1);

        var prettyCatNumber2 = CatUtils.prettifyCatalogueNumber9(123_456_789);
        assertEquals("123-456-789", prettyCatNumber2);
    }


}