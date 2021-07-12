package dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas;

import java.util.regex.Pattern;

/**
 * Provides a method to calculate a check digit required in: numeric GS1 Identification Keys, International Standard
 * Music Numbers, International Standard Book Numbers, etc.
 *
 * @see <a href="https://en.wikipedia.org/wiki/International_Article_Number#Check_digit">EAN check digit calculation</a>
 * @see <a href="https://www.gs1.org/services/how-calculate-check-digit-manually">Calculate a check digit manually</a>
 * @since 0.9.0
 */
public class ModTenFormula {

    private static final Pattern NON_DIGIT_CHARS_PATTERN = Pattern.compile("[^\\d]");
    private static final int WEIGHT_1 = 1;
    private static final int WEIGHT_3 = 3;
    private static final int MOD = 10;

    /**
     * Returns a valid check digit
     */
    public Integer generateCheckDigit(String input) {
        char[] sanitizedInput = removeInvalidCharacters(input);
        int sum = sumElementsMultipliedByTheirWeight(sanitizedInput);

        return computeCheckDigit(sum);
    }

    private char[] removeInvalidCharacters(String input) {
        return NON_DIGIT_CHARS_PATTERN.matcher(input).replaceAll("").toCharArray();
    }

    private int sumElementsMultipliedByTheirWeight(char[] input) {
        int sum = 0;
        int completeLength = input.length;

        for (int i = 0; i < input.length; i++) {
            int positionCountedFromRight = completeLength - i;
            sum += Character.getNumericValue(input[i]) * getWeight(positionCountedFromRight);
        }

        return sum;
    }

    private int getWeight(int position) {
        if (position % 2 == 0) {
            return WEIGHT_1;
        }
        return WEIGHT_3;
    }

    private int computeCheckDigit(int sum) {
        int modulus = sum % MOD;

        if (modulus != 0) {
            return MOD - modulus;
        }

        return 0;
    }
}
