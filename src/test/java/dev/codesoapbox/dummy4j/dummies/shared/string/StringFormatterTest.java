package dev.codesoapbox.dummy4j.dummies.shared.string;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringFormatterTest {

    @ParameterizedTest
    @CsvSource({
            "1111222233, 1111-2222-33, 4",
            "1111222233334444, 1111-2222-3333-4444, 4",
            "12, 1-2, 1"
    })
    void shouldInsertDashEveryNthCharacters(String input, String expected, int everyN) {
        String actual = StringFormatter.insertEveryNthCharacter(input, "-", everyN);

        assertEquals(expected, actual);
    }

    @Test
    void shouldInsertSpace() {
        String actual = StringFormatter.insertEveryNthCharacter("1234", " ", 1);

        assertEquals("1 2 3 4", actual);
    }

    @Test
    void shouldInsertTabulator() {
        String actual = StringFormatter.insertEveryNthCharacter("12", "   ", 1);

        assertEquals("1   2", actual);
    }

    @ParameterizedTest
    @CsvSource({
            "'', '', 4",
            "'', '-', 4",
            "'-', '', 4",
            "'1', '-', 4",
            "123, -, 4",
            "'', '', 0",
            "12, 12, 0",
            "12, 12, -1",
            "1111, 1111, 4"
    })
    void shouldReturnOriginalString(String input, String insertion, int everyN) {
        String actual = StringFormatter.insertEveryNthCharacter(input, insertion, everyN);

        assertEquals(input, actual);
    }

    @ParameterizedTest
    @CsvSource({
            ", '-'",
            "1111, , "
    })
    void shouldNotPerformInsertionWithNullParameters(String input, String insertion) {
        String actual = StringFormatter.insertEveryNthCharacter(input, insertion, 4);

        assertEquals(input, actual);
    }
}