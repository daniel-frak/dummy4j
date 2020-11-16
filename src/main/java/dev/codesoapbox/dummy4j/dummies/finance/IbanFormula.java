package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;

/**
 * Provides a method for calculating IBAN check digits.
 * <p>
 * The check digits are calculated using the MOD-97-10 routine compliant with the ISO/IEC 7064 standard.
 *
 * @see <a href="https://en.wikipedia.org/wiki/International_Bank_Account_Number#Generating_IBAN_check_digits">
 * Generating IBAN check digits</a>
 * @see <a href="https://en.wikipedia.org/wiki/International_Bank_Account_Number#IBAN_formats_by_country">
 * IBAN formats by country</a>
 * @see <a href="https://en.wikipedia.org/wiki/ISO/IEC_7064">ISO/IEC 7064</a>
 */
public class IbanFormula {

    private static final int MOD = 97;
    private static final int CHECK_SUM = 98;
    private static final String CHECK_DIGIT_REPLACEMENT = "00";
    private static final int MIN_NUMERIC_CHAR = 10;
    private static final int ONE_PLACE = 10;
    private static final int TWO_PLACES = 100;
    private static final int MAX_FOR_EIGHT_DIGITS = 99999999;

    /**
     * Returns valid IBAN check digits generated for the given account number and country
     */
    String getCheckDigits(String accountNumber, String countryCode) {
        int modulus = getModulusForNumericValues(accountNumber + countryCode + CHECK_DIGIT_REPLACEMENT);
        int checkDigit = CHECK_SUM - modulus;

        return Padding.leftPad(String.valueOf(checkDigit), 2, '0');
    }

    /**
     * Converts all characters to numeric values and performs {@code input mod 97} in a piece-wise manner.
     *
     * @see <a href="https://en.wikipedia.org/wiki/International_Bank_Account_Number#Modulo_operation_on_IBAN">
     * Modulo operation on IBAN</a>
     */
    private int getModulusForNumericValues(String input) {
        long value = 0;
        for (Character character : input.toCharArray()) {
            if (Character.isLetterOrDigit(character)) {
                int numericValue = getNumericValue(character);
                value = addDigitsToTheRightSide(value, numericValue);
                if (isNineDigitsLong(value)) {
                    value = value % MOD;
                }
            }
        }

        return (int) (value % MOD);
    }

    /**
     * Returns the int value that the specified Unicode character represents.
     * <p>
     * The letters in their lowercase ('\u0061' through '\u007A') are returned as numbers within the 10-35 range.
     */
    private int getNumericValue(Character character) {
        char lowerCaseChar = Character.toLowerCase(character);

        return Character.getNumericValue(lowerCaseChar);
    }

    private long addDigitsToTheRightSide(long originalValue, int digits) {
        if (isLetter(digits)) {
            return allocateSpace(originalValue, TWO_PLACES) + digits;
        }
        return allocateSpace(originalValue, ONE_PLACE) + digits;
    }

    private boolean isLetter(int characterNumericValue) {
        return characterNumericValue >= MIN_NUMERIC_CHAR;
    }

    private long allocateSpace(long originalValue, int howMuch) {
        return originalValue * howMuch;
    }

    private boolean isNineDigitsLong(long value) {
        return value > MAX_FOR_EIGHT_DIGITS;
    }
}
