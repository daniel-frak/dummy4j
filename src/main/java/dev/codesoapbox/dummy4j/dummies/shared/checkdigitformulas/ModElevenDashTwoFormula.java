package dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas;

import java.util.regex.Pattern;

/**
 * Provides a method to calculate a check digit required in an Open Researcher and Contributor ID.
 *
 * @see <a href="https://support.orcid.org/hc/en-us/articles/360006897674-Structure-of-the-ORCID-Identifier">
 * ORCID check digit calculation</a>
 * @since 0.9.0
 */
public class ModElevenDashTwoFormula {

    private static final Pattern NON_DIGIT_CHARS_PATTERN = Pattern.compile("[^\\d]");
    private static final int MOD = 11;

    /**
     * Returns a valid check digit
     */
    public Integer generateCheckDigit(String input) {
        char[] sanitizedInput = removeInvalidCharacters(input);
        int sum = getSum(sanitizedInput);

        return computeCheckDigit(sum);
    }

    private char[] removeInvalidCharacters(String input) {
        return NON_DIGIT_CHARS_PATTERN.matcher(input).replaceAll("").toCharArray();
    }

    private int getSum(char[] input) {
        int sum = 0;
        for (char c : input) {
            int digit = Character.getNumericValue(c);
            sum = (sum + digit) * 2;
        }

        return sum;
    }

    private int computeCheckDigit(int sum) {
        int remainder = sum % MOD;

        return (12 - remainder) % MOD;
    }
}
