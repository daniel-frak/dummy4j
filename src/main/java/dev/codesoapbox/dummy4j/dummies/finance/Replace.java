package dev.codesoapbox.dummy4j.dummies.finance;

import java.util.function.Predicate;

final class Replace {

    private Replace() {
    }

    public static String replaceCharactersConditionally(String source, String replacement,
                                                        Predicate<Character> condition) {
        if (source.length() < replacement.length()) {
            throw new IllegalArgumentException("Source must be long enough to allocate the replacement");
        }
        char[] sourceChars = source.toCharArray();
        char[] replacementChars = replacement.toCharArray();
        int replacementIndex = 0;

        for (int i = 0; i < sourceChars.length; i++) {
            if (condition.test(sourceChars[i])) {
                sourceChars[i] = replacementChars[replacementIndex];
                replacementIndex++;
            }
            if (replacementIndex == replacementChars.length) {
                break;
            }
        }

        return String.valueOf(sourceChars);
    }
}
