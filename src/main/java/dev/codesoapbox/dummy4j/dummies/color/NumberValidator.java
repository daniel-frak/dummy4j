package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;

/**
 * @since 0.4.0
 */
final class NumberValidator {

    public static final float MAX_ANGLE = 360F;
    public static final int MAX_RGB = 0xFFFFFF;
    public static final long MAX_RGBA = 0xFFFFFFFFL;

    private NumberValidator() {
    }

    public static void betweenZeroAndOne(float value) {
        if (value < 0 || value > 1) {
            throw new ValueOutOfRangeException(value, 0F, 1F);
        }
    }

    public static void inRange(float value, float min, float max) {
        if (value < min || value > max) {
            throw new ValueOutOfRangeException(value, min, max);
        }
    }
}
