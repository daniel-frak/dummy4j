package dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas;

import java.util.function.IntPredicate;

/**
 * Provides a method to replace {@code 10} with {@code X} in some check digit formulas
 *
 * @since 0.9.0
 */
public final class CheckDigit10AsXFormatter {

    private static final IntPredicate ELIGIBLE_FOR_REPLACEMENT = p -> p == 10;
    private static final String REPLACEMENT = "X";

    private CheckDigit10AsXFormatter() {
    }

    /**
     * Replaces {@code 10} with {@code X}
     */
    public static String formatCheckDigit(Integer checkDigit) {
        return ELIGIBLE_FOR_REPLACEMENT.test(checkDigit) ? REPLACEMENT : String.valueOf(checkDigit);
    }
}
