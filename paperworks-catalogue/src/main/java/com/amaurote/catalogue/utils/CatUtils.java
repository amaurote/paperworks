package com.amaurote.catalogue.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Random;

public class CatUtils {

    private static final Random rand = new Random();

    public static long generateCatalogueNumber9() {
        return 100_000_000L + rand.nextLong(900_000_000L);
    }

    public static String prettifyCatalogueNumber9(long catId) {
        if (catId < 0 || catId > 999_999_999)
            return Long.toString(catId);

        var leadingZeroes = String.format("%09d", catId);
        return leadingZeroes.substring(0, 3) + '-' + leadingZeroes.substring(3, 6) + '-' + leadingZeroes.substring(6);
    }

    public static Long stringToCatalogueNumber9(String catIdStr) {
        if(catIdStr == null)
            return null;

        var numeric = catIdStr.replaceAll("[^0-9]", "");

        if (StringUtils.isBlank(numeric))
            return null;

        return Long.parseLong(numeric);
    }

    public static boolean validateISBN(String isbn) {
        if (StringUtils.isBlank(isbn))
            return false;

        return switch (isbn.length()) {
            case 10 -> validateISBN10(isbn);
            case 13 -> validateISBN13(isbn);
            default -> false;
        };
    }

    private static boolean validateISBN10(String isbn) {
        String first9 = isbn.substring(0, 9);
        if (!first9.matches("[0-9]+"))
            return false;

        String lastChar = isbn.substring(9);
        if (!lastChar.matches("[0-9xX]"))
            return false;

        var result = (StringUtils.isNumeric(lastChar)) ? Integer.parseInt(lastChar) : 10;

        for (int i = 0; i < 9; i++) {
            result += (10 - i) * (isbn.charAt(i) - '0');
        }

        return result % 11 == 0;
    }

    private static boolean validateISBN13(String isbn) {
        if (!StringUtils.isNumeric(isbn))
            return false;

        int lastDigit = isbn.charAt(12) - '0';

        boolean high = false;
        int sum = 0;
        for (int i = 0; i < 12; i++, high = !high) {
            int temp = isbn.charAt(i) - '0';
            sum += (high) ? temp * 3 : temp;
        }

        int checkNum = sum % 10;
        return (checkNum == 0) ? lastDigit == 0 : 10 - checkNum == lastDigit;
    }
}
