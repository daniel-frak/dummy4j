package dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas;

import java.util.regex.Pattern;

/**
 * Provides a method to calculate a check digit required in International Standard Serial Numbers,
 * 10-digit long International Standard Book Numbers, etc.
 *
 * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Serial_Number#Code_format">ISSN Code format</a>
 * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Book_Number#ISBN-10_check_digit_calculation">
 * ISBN 10 check digit calculation</a>
 * @since SNAPSHOT
 */
public class ModElevenFormula {

    private static final Pattern NON_DIGIT_CHARS_PATTERN = Pattern.compile("[^\\d]");
    private static final int MOD = 11;

    /**
     * Returns a valid check digit
     */
    public Integer generateCheckDigit(String input) {
        char[] sanitizedInput = removeInvalidCharacters(input);
        int sum = sumElementsMultipliedByTheirPositions(sanitizedInput);

        return computeCheckDigit(sum);
    }

    private char[] removeInvalidCharacters(String input) {
        return NON_DIGIT_CHARS_PATTERN.matcher(input).replaceAll("").toCharArray();
    }

    private int sumElementsMultipliedByTheirPositions(char[] input) {
        int sum = 0;
        int completeLength = input.length + 1;

        for (int i = 0; i < input.length; i++) {
            int positionCountedFromRight = completeLength - i;
            sum += Character.getNumericValue(input[i]) * positionCountedFromRight;
        }

        return sum;
    }

    private int computeCheckDigit(int sum) {
        int modulus = sum % MOD;

        if (modulus != 0) {
            return MOD - modulus;
        }

        return 0;
    }
}
