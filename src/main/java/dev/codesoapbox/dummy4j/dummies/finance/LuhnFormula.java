package dev.codesoapbox.dummy4j.dummies.finance;

/**
 * This class provides methods implementing the Luhn algorithm
 *
 * @see <a href="https://en.wikipedia.org/wiki/Luhn_algorithm">Luhn algorithm definition</a>
 */
public class LuhnFormula {

    /**
     * Returns a valid check digit calculated with the Luhn algorithm for the given number
     */
    public String getCheckDigit(String input) {
        char[] sanitizedInput = removeInvalidCharacters(input);
        int sum = getSum(sanitizedInput);

        return String.valueOf(getCheckDigit(sum));
    }

    private char[] removeInvalidCharacters(String input) {
        String sanitized = input.replaceAll("[^\\d]", "");

        return sanitized.toCharArray();
    }

    private int getSum(char[] chars) {
        int sum = 0;

        for (int i = 0; i < chars.length; i++) {
            int number = getInReverseOrder(chars, i);
            sum += getElementValue(i, number);
        }

        return sum;
    }

    private int getInReverseOrder(char[] chars, int i) {
        int position = chars.length - 1 - i;
        char character = chars[position];

        return Character.getNumericValue(character);
    }

    private int getElementValue(int i, int number) {
        if (indexIsEven(i)) {
            return getOddElementValue(number);
        } else {
            return number;
        }
    }

    private boolean indexIsEven(int i) {
        return i % 2 == 0;
    }

    private int getOddElementValue(int element) {
        int value = element * 2;

        if (value <= 9) {
            return value;
        }

        return value - 9;
    }

    private int getCheckDigit(int sum) {
        int modulo = sum % 10;

        if (modulo == 0) {
            return 0;
        }

        return 10 - modulo;
    }
}
