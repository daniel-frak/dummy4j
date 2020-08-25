package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Generates a randomized password.
 * <p>
 * Default values:
 * <p>
 * <ul>
 *  <li>min length: 12</li>
 *  <li>max length: 12</li>
 * </ul>
 */
public class PasswordBuilder {

    /**
     * Upper boundary for randomly generated digits
     */
    public static final int DIGIT_UPPER_BOUND = 9;

    /**
     * Points to a list of possible special chars
     */
    public static final String SPECIAL_CHAR_KEY = "#{internet.password_special_char}";

    private final Dummy4j dummy4j;
    private final Map<String, Supplier<Character>> constraintCharacters = new LinkedHashMap<>();

    private int minLength = 12;
    private int maxLength = 12;

    public PasswordBuilder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Adds randomly generated digits
     */
    public PasswordBuilder withDigits() {
        constraintCharacters.put("with digits", this::getDigit);
        return this;
    }

    private Character getDigit() {
        int number = dummy4j.number().nextInt(DIGIT_UPPER_BOUND);
        return (char) (number + '0');
    }

    /**
     * Adds randomly selected special characters
     */
    public PasswordBuilder withSpecialChars() {
        constraintCharacters.put("with special characters", this::getSpecialChar);
        return this;
    }

    private Character getSpecialChar() {
        return dummy4j.expressionResolver().resolve(SPECIAL_CHAR_KEY).charAt(0);
    }

    /**
     * Adds random upper case characters
     */
    public PasswordBuilder withUpperCaseChars() {
        constraintCharacters.put("with upper case characters", this::getUpperCaseChar);
        return this;
    }

    private Character getUpperCaseChar() {
        return dummy4j.lorem().character().toUpperCase(Locale.ENGLISH).charAt(0);
    }

    /**
     * Sets the length of the generated password
     */
    public PasswordBuilder withLength(int length) {
        this.minLength = length;
        this.maxLength = length;
        return this;
    }

    /**
     * Sets the minimum length of the generated password.
     * If the minimum length is greater than the maximum length the password will be exactly {@code minLength} long.
     * Otherwise, the password will be between {@code minLength} and {@code maxLength} characters long.
     */
    public PasswordBuilder withMinLength(int minLength) {
        if (minLength > maxLength) {
            maxLength = minLength;
        }
        this.minLength = minLength;
        return this;
    }

    /**
     * Sets the maximum length of the generated password.
     * If the maximum length is smaller than the minimum length the password will be exactly {@code maxLength} long.
     * Otherwise, the password will be between {@code minLength} and {@code maxLength} characters long.
     */
    public PasswordBuilder withMaxLength(int maxLength) {
        if (maxLength < minLength) {
            minLength = maxLength;
        }
        this.maxLength = maxLength;
        return this;
    }

    /**
     * Returns a randomly generated password
     */
    public String build() {
        verifyMinLengthCanAccommodateConstraints();
        return buildPasswordWithConstraints();
    }

    private void verifyMinLengthCanAccommodateConstraints() {
        if (constraintCharacters.size() >= minLength) {
            String selectedConstraints = String.join(", ", constraintCharacters.keySet());
            throw new IllegalArgumentException("Minimum length of the password (" + minLength + ") must be GREATER " +
                    "than the number of selected constraints (" + constraintCharacters.size() + "). " +
                    "The following constraints were selected: [" + selectedConstraints + "].");
        }
    }

    private String buildPasswordWithConstraints() {
        StringBuilder password = getPasswordWithOnlyLetters();
        applyConstraints(password);
        return password.toString();
    }

    private StringBuilder getPasswordWithOnlyLetters() {
        int length = dummy4j.number().nextInt(minLength, maxLength);
        return new StringBuilder(dummy4j.lorem().characters(length));
    }

    private void applyConstraints(StringBuilder password) {
        int offset = 0;
        int passwordLength = password.toString().length();
        int howManyCharsToSkip = constraintCharacters.size() + 1;
        for (Supplier<Character> supplier : constraintCharacters.values()) {
            for (int i = offset; i < passwordLength; i += howManyCharsToSkip) {
                password.setCharAt(i, supplier.get());
            }
            offset += 1;
        }
    }

    @Override
    public String toString() {
        return "PasswordBuilder{" +
                "constraintCharacters=" + constraintCharacters.keySet() +
                ", minLength=" + minLength +
                ", maxLength=" + maxLength +
                '}';
    }
}
