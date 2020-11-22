package dev.codesoapbox.dummy4j.dummies.finance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ReplaceTest {

    @ParameterizedTest
    @CsvSource({
            "123, 99, 993",
            "1__23, 99, 9__93",
            "123 4 56, 99999, 999 9 96",
            "12-34 56/78, 99999999, 99-99 99/99",
            "'','',''"
    })
    void shouldReplaceNumbers(String source, String replacement, String expected) {
        String actual = Replace.replaceCharactersConditionally(source, replacement, Character::isDigit);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionOnInvalidReplacementLength() {
        assertThrows(IllegalArgumentException.class,
                () -> Replace.replaceCharactersConditionally("1", "123", Character::isDigit));
    }

    @Test
    void shouldStopWhenNoMoreReplacementCharsAvailable() {
        assertDoesNotThrow(
                () -> Replace.replaceCharactersConditionally("123", "9", Character::isDigit));
    }
}