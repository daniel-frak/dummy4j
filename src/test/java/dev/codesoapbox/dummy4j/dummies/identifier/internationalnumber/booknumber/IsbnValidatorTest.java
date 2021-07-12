package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import dev.codesoapbox.dummy4j.exceptions.InvalidIsbnParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IsbnValidatorTest {

    private IsbnValidator validator;

    @BeforeEach
    void setUp() {
        validator = new IsbnValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "1234", "12", "   "})
    void shouldThrowExceptionOnInvalidPrefix(String prefix) {
        assertThrows(InvalidIsbnParameterException.class, () -> validator.testForInvalidPrefix(prefix));
    }

    @Test
    void shouldAcceptValidPrefix() {
        assertDoesNotThrow(() -> validator.testForInvalidPrefix("123"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "123456", "", "   "})
    void shouldThrowExceptionOnInvalidRegistrationGroup(String group) {
        assertThrows(InvalidIsbnParameterException.class, () -> validator.testForInvalidRegistrationGroup(group));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12345"})
    void shouldAcceptValidRegistrationGroup(String group) {
        assertDoesNotThrow(() -> validator.testForInvalidRegistrationGroup(group));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "12345678", "", "   "})
    void shouldThrowExceptionOnInvalidOtherPart(String part) {
        assertThrows(InvalidIsbnParameterException.class, () -> validator.testForInvalidOtherPart(part));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "1234567"})
    void shouldAcceptValidOtherPart(String part) {
        assertDoesNotThrow(() -> validator.testForInvalidOtherPart(part));
    }
}