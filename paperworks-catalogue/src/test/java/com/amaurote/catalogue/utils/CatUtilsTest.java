package com.amaurote.catalogue.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CatUtilsTest {

    @Test
    public void generateCatalogueNumber() {
        var catNumber = CatUtils.generateCatalogueNumber9();
        assertTrue(catNumber >= 0 && catNumber <= 999_999_999);
    }

    @Test
    public void prettifyCatalogueNumber() {
        var prettyCatNumber1 = CatUtils.prettifyCatalogueNumber9(0);
        assertEquals("000-000-000", prettyCatNumber1);

        var prettyCatNumber2 = CatUtils.prettifyCatalogueNumber9(123_456_789);
        assertEquals("123-456-789", prettyCatNumber2);
    }

    @Test
    public void validateISBN() {
        // invalid
        assertFalse(CatUtils.validateISBN("123456789"));
        assertFalse(CatUtils.validateISBN("12345 6789"));
        assertFalse(CatUtils.validateISBN(""));
        assertFalse(CatUtils.validateISBN(null));

        // ISBN10
        assertTrue(CatUtils.validateISBN("1529034566"));
        assertFalse(CatUtils.validateISBN("1529034567"));

        assertTrue(CatUtils.validateISBN("007462542X"));
        assertTrue(CatUtils.validateISBN("007462542x"));
        assertFalse(CatUtils.validateISBN("007462542F"));

        // ISBN13
        assertTrue(CatUtils.validateISBN("9788097316051"));
        assertFalse(CatUtils.validateISBN("9788097316052"));

        assertTrue(CatUtils.validateISBN("9781784872816"));
        assertFalse(CatUtils.validateISBN("9781784872826"));

        assertTrue(CatUtils.validateISBN("9780241448304"));
        assertTrue(CatUtils.validateISBN("9781847941831"));
    }


}