package dev.codesoapbox.dummy4j.dummies.finance;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IINRangeTest {

    @Test
    void shouldConvertToString() {
        IINRange actual = IINRange.from(5, 10);

        assertEquals("IINRange{min=5, max=10}", actual.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.simple()
                .forClass(IINRange.class)
                .verify();
    }
}