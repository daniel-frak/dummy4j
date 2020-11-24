package dev.codesoapbox.dummy4j.dummies.shared.string;

/**
 * Provides convenience methods for validating strings
 *
 * @since 0.6.0
 */
public final class StringValidator {

    private StringValidator() {
    }

    /**
     * Verifies whether the given String is equal to null or empty
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }
}
