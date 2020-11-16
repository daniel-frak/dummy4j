package dev.codesoapbox.dummy4j.dummies.shared.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

    @Test
    void shouldReturnTrueOnEmptyValue() {
        assertTrue(StringValidator.isNullOrEmpty(""));
    }

    @Test
    void shouldReturnTrueOnWhitespaces() {
        assertTrue(StringValidator.isNullOrEmpty("    "));
    }

    @Test
    void shouldReturnFalseOnNonEmptyValue() {
        assertFalse(StringValidator.isNullOrEmpty("test"));
    }

}