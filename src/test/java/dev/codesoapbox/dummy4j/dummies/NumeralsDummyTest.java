package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.RandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumeralsDummyTest {

    private NumeralsDummy numerals;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private RandomService randomService;

    @BeforeEach
    void setUp() {
        numerals = new NumeralsDummy(dummy4j);
    }

    @ParameterizedTest
    @CsvSource({
            "1, I",
            "354, CCCLIV",
            "1422, MCDXXII",
            "4999, MMMMCMXCIX"
    })
    void shouldGenerateRoman(int arabic, String expected) {
        when(dummy4j.random())
                .thenReturn(randomService);
        when(randomService.nextInt(1, NumeralsDummy.ROMAN_NUMERAL_MAX))
                .thenReturn(arabic);
        assertEquals(expected, numerals.roman());
    }

    @Test
    void shouldGenerateRomanWithUpperBound() {
        when(dummy4j.random())
                .thenReturn(randomService);
        when(randomService.nextInt(1, 500))
                .thenReturn(354);
        assertEquals("CCCLIV", numerals.roman(500));
    }

    @Test
    void romanWithUpperBoundShouldThrowExceptionWhenMaxExceeded() {
        assertThrows(IllegalArgumentException.class, () -> numerals.roman(5000));
    }

    @Test
    void shouldGenerateRomanWithLowerAndUpperBound() {
        when(dummy4j.random())
                .thenReturn(randomService);
        when(randomService.nextInt(200, 500))
                .thenReturn(354);
        assertEquals("CCCLIV", numerals.roman(200, 500));
    }

    @Test
    void romanWithLowerAndUpperBoundShouldThrowExceptionWhenMaxExceeded() {
        assertThrows(IllegalArgumentException.class, () -> numerals.roman(5000));
    }

    @Test
    void romanWithLowerAndUpperBoundShouldThrowExceptionWhenLowerBoundLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> numerals.roman(0, 10));
    }
}