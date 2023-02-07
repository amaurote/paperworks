package com.amaurote.catalog.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogUtilsTest {

    @Test
    public void generateCatalogNumber() {
        var catNumber = CatalogUtils.generateCatalogNumber9();
        assertTrue(catNumber >= 0 && catNumber <= 999_999_999);
    }

    @Test
    public void prettifyCatalogNumber() {
        var prettyCatNumber1 = CatalogUtils.prettifyCatalogNumber9(0);
        assertEquals("000-000-000", prettyCatNumber1);

        var prettyCatNumber2 = CatalogUtils.prettifyCatalogNumber9(123_456_789);
        assertEquals("123-456-789", prettyCatNumber2);
    }

    @Test
    public void validateISBN() {
        // invalid
        assertFalse(CatalogUtils.validateISBN("123456789"));
        assertFalse(CatalogUtils.validateISBN("12345 6789"));
        assertFalse(CatalogUtils.validateISBN(""));
        assertFalse(CatalogUtils.validateISBN(null));

        // ISBN10
        assertTrue(CatalogUtils.validateISBN("1529034566"));
        assertFalse(CatalogUtils.validateISBN("1529034567"));

        assertTrue(CatalogUtils.validateISBN("007462542X"));
        assertTrue(CatalogUtils.validateISBN("007462542x"));
        assertFalse(CatalogUtils.validateISBN("007462542F"));

        // ISBN13
        assertTrue(CatalogUtils.validateISBN("9788097316051"));
        assertFalse(CatalogUtils.validateISBN("9788097316052"));

        assertTrue(CatalogUtils.validateISBN("9781784872816"));
        assertFalse(CatalogUtils.validateISBN("9781784872826"));

        assertTrue(CatalogUtils.validateISBN("9780241448304"));
        assertTrue(CatalogUtils.validateISBN("9781847941831"));
    }


}