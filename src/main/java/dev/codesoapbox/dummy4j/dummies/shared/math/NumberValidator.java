package dev.codesoapbox.dummy4j.dummies.shared.math;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;

/**
 * Provides convenience methods for validating numbers
 *
 * @since SNAPSHOT
 */
public final class NumberValidator {

    private NumberValidator() {
    }

    /**
     * Verifies if the given value fits in the {@code 0-1} range
     *
     * @throws ValueOutOfRangeException if the given value is not within the {@code 0-1} range
     */
    public static void betweenZeroAndOne(float value) {
        if (value < 0 || value > 1) {
            throw new ValueOutOfRangeException(value, 0F, 1F);
        }
    }

    /**
     * Verifies if the given value fits in the {@code min-max} range
     *
     * @throws ValueOutOfRangeException if the given value is not within the {@code min-max} range
     */
    public static void inRange(float value, float min, float max) {
        if (value < min || value > max) {
            throw new ValueOutOfRangeException(value, min, max);
        }
    }
}
