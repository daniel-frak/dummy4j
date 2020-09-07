package dev.codesoapbox.dummy4j.dummies.sharedmath;

import java.text.DecimalFormat;

/**
 * Provides convenience methods for number formatting
 *
 * @since SNAPSHOT
 */
public final class NumberFormatter {

    private static final DecimalFormat DECIMAL = new DecimalFormat("0.##");

    private NumberFormatter() {
    }

    /**
     * Formats the given value to two decimal places, without trailing zeros
     */
    public static String toTwoDecimals(float value) {
        return DECIMAL.format(value);
    }

    /**
     * Formats the given value to a percent value
     */
    public static String toPercent(float value) {
        return DECIMAL.format(value * 100F) + "%";
    }
}
