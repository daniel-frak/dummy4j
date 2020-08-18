package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.HashMap;
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
     * Upper boundary for randomly generated digit
     */
    public static final int DIGIT_UPPER_BOUND = 9;

    /**
     * Points to a list of possible special chars
     */
    public static final String SPECIAL_CHAR_KEY = "#{internet.password_special_char}";

    private final Dummy4j dummy4j;
    private final Map<String, Supplier<Character>> constraints = new HashMap<>();

    private int minLength = 12;
    private int maxLength = 12;

    public PasswordBuilder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Adds randomly generated digits
     */
    public PasswordBuilder withDigits() {
        constraints.put("with digits", this::getDigit);
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
        constraints.put("with special characters", this::getSpecialChar);
        return this;
    }

    private Character getSpecialChar() {
        return dummy4j.expressionResolver().resolve(SPECIAL_CHAR_KEY).charAt(0);
    }

    /**
     * Adds random upper case characters
     */
    public PasswordBuilder withUpperCaseChars() {
        constraints.put("with upper case characters", this::getUpperCaseChar);
        return this;
    }

    private Character getUpperCaseChar() {
        return dummy4j.lorem().character().toUpperCase(Locale.ENGLISH).charAt(0);
    }

    /**
     * Sets length of the generated password
     */
    public PasswordBuilder withLength(int length) {
        this.minLength = length;
        this.maxLength = length;
        return this;
    }

    /**
     * Sets minimum length of the generated password.
     * If minimum length is greater than maximum length the password will be {@code minLength} long.
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
     * Sets maximum length of the generated password.
     * If maximum length is smaller than minimum length the password will be {@code maxLength} long.
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
        checkMinLengthAgainstConstraints(constraints);
        return buildPasswordWithConstraints(constraints);
    }

    private void checkMinLengthAgainstConstraints(Map<String, Supplier<Character>> constraints) {
        if (constraints.size() >= minLength) {
            String selectedConstraints = String.join(", ", constraints.keySet());
            throw new IllegalArgumentException("Minimum length of the password must be GREATER than the number of " +
                    "selected constraints. The following constraints were selected: [" + selectedConstraints + "] " +
                    "and the specified minimum password length is " + minLength + ".");
        }
    }

    private String buildPasswordWithConstraints(Map<String, Supplier<Character>> constraints) {
        StringBuilder password = getPasswordWithOnlyChars();
        applyConstraints(constraints, password);
        return password.toString();
    }

    private StringBuilder getPasswordWithOnlyChars() {
        int length = dummy4j.number().nextInt(minLength, maxLength);
        return new StringBuilder(dummy4j.lorem().characters(length));
    }

    private void applyConstraints(Map<String, Supplier<Character>> constraints, StringBuilder password) {
        int offset = 0;
        int passwordLength = password.toString().length();
        int howManyToSkip = constraints.size() + 1;
        for (Map.Entry<String, Supplier<Character>> supplier : constraints.entrySet()) {
            for (int i = offset; i < passwordLength; i += howManyToSkip) {
                password.setCharAt(i, supplier.getValue().get());
            }
            offset += 1;
        }
    }
}
