package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.TreeMap;

/**
 * Provides methods for generating numerals
 *
 * @since 0.4.0
 */
public class NumeralsDummy {

    protected static final int ROMAN_NUMERAL_MAX = 4999;

    private static final String ROMAN_NUMERAL_MAX_EXCEEDED = "Roman numeral may not be greater than "
            + ROMAN_NUMERAL_MAX;
    private static final String ROMAN_NUMERAL_MUST_BE_GREATER_THAN_ZERO = "Roman numeral must be greater than zero";

    /**
     * https://stackoverflow.com/a/19759564
     */
    private static final TreeMap<Integer, String> romanNumerals = new TreeMap<>();

    static {
        romanNumerals.put(1000, "M");
        romanNumerals.put(900, "CM");
        romanNumerals.put(500, "D");
        romanNumerals.put(400, "CD");
        romanNumerals.put(100, "C");
        romanNumerals.put(90, "XC");
        romanNumerals.put(50, "L");
        romanNumerals.put(40, "XL");
        romanNumerals.put(10, "X");
        romanNumerals.put(9, "IX");
        romanNumerals.put(5, "V");
        romanNumerals.put(4, "IV");
        romanNumerals.put(1, "I");
    }

    private final Dummy4j dummy4j;

    public NumeralsDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Provides a random roman numeral
     */
    public String roman() {
        return toRoman(dummy4j.number().nextInt(1, ROMAN_NUMERAL_MAX));
    }

    private String toRoman(int number) {
        int l = romanNumerals.floorKey(number);
        if (number == l) {
            return romanNumerals.get(number);
        }
        return romanNumerals.get(l) + toRoman(number - l);
    }

    /**
     * Provides a random roman numeral between {@code 1} and {@code upperBound}
     */
    public String roman(int upperBound) {
        if (upperBound > ROMAN_NUMERAL_MAX) {
            throw new IllegalArgumentException(ROMAN_NUMERAL_MAX_EXCEEDED);
        }

        return toRoman(dummy4j.number().nextInt(1, upperBound));
    }

    /**
     * Provides a random roman numeral between {@code lowerBound} and {@code upperBound}
     */
    public String roman(int lowerBound, int upperBound) {
        if (upperBound > ROMAN_NUMERAL_MAX) {
            throw new IllegalArgumentException(ROMAN_NUMERAL_MAX_EXCEEDED);
        }
        if (lowerBound <= 0) {
            throw new IllegalArgumentException(ROMAN_NUMERAL_MUST_BE_GREATER_THAN_ZERO);
        }

        return toRoman(dummy4j.number().nextInt(lowerBound, upperBound));
    }
}
