package dev.codesoapbox.dummy4j;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void equalsContract() {
        EqualsVerifier.forClass(ResolvedValue.class)
                .withNonnullFields("locale", "value")
                .verify();
    }
}