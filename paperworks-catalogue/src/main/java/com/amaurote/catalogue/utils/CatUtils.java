package com.amaurote.catalogue.utils;

import java.util.Random;

public class CatUtils {

    private static final Random rand = new Random();

    public static String generateCatalogueNumber() {
        return String.format("%09d", rand.nextInt(1000000000));
    }

}
