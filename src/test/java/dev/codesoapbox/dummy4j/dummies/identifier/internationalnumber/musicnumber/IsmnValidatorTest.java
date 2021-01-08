package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import dev.codesoapbox.dummy4j.exceptions.InvalidIsmnParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IsmnValidatorTest {

    private IsmnValidator validator;

    @BeforeEach
    void setUp() {
        validator = new IsmnValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "12345678", "", "   "})
    void shouldThrowExceptionOnInvalidRegistrant(String registrant) {
        assertThrows(InvalidIsmnParameterException.class, () -> validator.testForInvalidRegistrant(registrant));
    }

    @Test
    void shouldAcceptValidRegistrant() {
        assertDoesNotThrow(() -> validator.testForInvalidRegistrant("123"));
    }
}