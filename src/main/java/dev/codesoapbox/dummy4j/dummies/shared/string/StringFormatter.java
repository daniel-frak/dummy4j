package dev.codesoapbox.dummy4j.dummies.shared.string;

/**
 * Provides convenience methods for formatting strings
 *
 * @since 0.9.0
 */
public final class StringFormatter {

    private StringFormatter() {
    }

    /**
     * Inserts a given {@code separator} every nth character in the {@code originalInput}.
     * <p>
     * Returns {@code originalInput} unchanged when at least one of the passed strings is {@code null/empty}
     * or the {@code everyN} value is greater than the {@code originalInput} length.
     */
    public static String insertEveryNthCharacter(String originalInput, String separator, int everyN) {
        if (cannotPerformInsertion(originalInput, separator, everyN)) {
            return originalInput;
        }
        char[] input = originalInput.toCharArray();
        if (input.length <= everyN) {
            return originalInput;
        }

        return performInsertion(separator, everyN, input);
    }

    private static boolean cannotPerformInsertion(String originalInput, String separator, int everyN) {
        return everyN <= 0
                || isNullOrEmpty(originalInput)
                || isNullOrEmpty(separator);
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private static String performInsertion(String insertion, int everyN, char[] input) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < input.length; i++) {
            stringBuilder.append(input[i]);
            if (isNthCharacter(i, everyN) && isNotLastCharacter(i, input)) {
                stringBuilder.append(insertion);
            }
        }

        return stringBuilder.toString();
    }

    private static boolean isNthCharacter(int index, int n) {
        return (index + 1) % n == 0;
    }

    private static boolean isNotLastCharacter(int index, char[] input) {
        return index < input.length - 1;
    }
}
