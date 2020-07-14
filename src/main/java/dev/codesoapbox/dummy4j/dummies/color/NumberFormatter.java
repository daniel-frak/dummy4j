package dev.codesoapbox.dummy4j.dummies.color;

import java.text.DecimalFormat;

/**
 * @since 0.4.0
 */
final class NumberFormatter {

    static final DecimalFormat DECIMAL = new DecimalFormat("0.##");

    private NumberFormatter() {
    }

    public static String toTwoDecimals(float value) {
        return DECIMAL.format(value);
    }

    public static String toPercent(float value) {
        return DECIMAL.format(value * 100F) + "%";
    }
}
