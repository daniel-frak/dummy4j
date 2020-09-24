package dev.codesoapbox.dummy4j.dummies.shared.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringValidatorTest {

    @Test
    void shouldReturnTrueOnEmptyValue() {
        assertTrue(StringValidator.isNullOrEmpty(""));
    }

    @Test
    void shouldReturnFalseOnNonEmptyValue() {
        assertFalse(StringValidator.isNullOrEmpty("test"));
    }

}