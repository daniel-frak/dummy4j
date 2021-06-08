package dev.codesoapbox.dummy4j;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResolvedValueTest {

    @Test
    void ofShouldCreate() {
        String locale = "en";
        String value = "someValue";

        ResolvedValue result = ResolvedValue.of(locale, value);

        assertEquals(locale, result.getLocale());
        assertEquals(value, result.getValue());
    }

    @Test
    void ofShouldThrowExceptionIfLocaleIsNull() {
        String locale = null;
        String value = "someValue";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ResolvedValue.of(locale, value));
        assertEquals("Locale cannot be null", exception.getMessage());
    }

    @Test
    void ofShouldThrowExceptionIfValueIsNull() {
        String locale = "en";
        String value = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ResolvedValue.of(locale, value));
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(ResolvedValue.class)
                .withNonnullFields("locale", "value")
                .verify();
    }
}