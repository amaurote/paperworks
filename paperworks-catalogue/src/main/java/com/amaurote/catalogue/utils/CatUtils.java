package com.amaurote.catalogue.utils;

import com.amaurote.catalogue.exception.CatalogueException;

import java.util.Random;

public class CatUtils {

    private static final Random rand = new Random();

    public static long generateCatalogueNumber9() {
        return 100_000_000L + rand.nextLong(900_000_000L);
    }

    public static String prettifyCatalogueNumber9(long catId) throws CatalogueException {
        if(catId < 0 || catId > 999_999_999)
            throw new CatalogueException("Invalid catalogue number.");

        var leadingZeroes = String.format("%09d", catId);
        return leadingZeroes.substring(0, 3) + '-' + leadingZeroes.substring(3, 6) + '-' + leadingZeroes.substring(6);
    }

}
