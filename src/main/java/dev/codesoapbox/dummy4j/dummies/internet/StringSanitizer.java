package dev.codesoapbox.dummy4j.dummies.internet;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Provides methods for normalizing strings and removing unwanted characters from them
 *
 * @since 0.5.0
 */
final class StringSanitizer {

    /**
     * Pattern that matches non ASCII characters, whitespaces, backslashes and quotes.
     * <p>
     * The pattern uses Unicode properties to cover more cases.
     *
     * @see <a href="https://stackoverflow.com/questions/56653323/s-doesnt-actually-capture-all-whitespace-characters/
     * 56654027#56654027">Capture all whitespace characters (StackOverflow)</a>
     */
    public static final Pattern SANITIZE_EMAIL_PATTERN = Pattern.compile("[^\\p{ASCII}]+|[\\s\\\\\"]+",
            Pattern.UNICODE_CHARACTER_CLASS);

    private StringSanitizer() {
    }

    /**
     * Replaces non ASCII characters with appropriate substitutions.
     * Removes whitespaces, backslashes, quotes and those non ASCII characters that couldn't be replaced.
     */
    public static String sanitizeForEmail(String input) {
        String normalized = normalize(input);
        return SANITIZE_EMAIL_PATTERN.matcher(normalized).replaceAll("");
    }

    /**
     * Normalizes the input according to the {@code NFKD} normalization norm by separating all of the accent marks
     * from the characters.
     *
     * @see Normalizer
     */
    private static String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFKD);
    }
}
