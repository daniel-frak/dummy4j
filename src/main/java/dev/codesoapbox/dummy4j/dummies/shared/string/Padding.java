package dev.codesoapbox.dummy4j.dummies.shared.string;

/**
 * Provides convenience methods for padding strings
 *
 * @since SNAPSHOT
 */
public final class Padding {

    private Padding() {
    }

    /**
     * Left pads the given {@code input} with a specified {@code padChar} until it is {@code desiredLength} long
     */
    public static String leftPad(String input, int desiredLength, char padChar) {
        if (input == null) {
            return null;
        }
        int difference = desiredLength - input.length();
        if (difference <= 0) {
            return input;
        }
        char[] padding = getPadding(padChar, difference);
        return String.valueOf(padding) + input;
    }

    private static char[] getPadding(char padChar, int difference) {
        char[] padding = new char[difference];
        for (int i = 0; i < difference; i++) {
            padding[i] = padChar;
        }

        return padding;
    }
}
