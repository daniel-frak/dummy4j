package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsmnTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1,2,3,-, 979-0-1-2-3",
            "1,2,3, ' ', 979 0 1 2 3",
            "1,2,3,'', 9790123",
            "1,2,3,NIL, 9790123"
    },
            nullValues = "NIL")
    void shouldConvertToString(String registrant, String item, String checkDigit, String separator, String expected) {
        Ismn actual = new Ismn(registrant, item, checkDigit, separator);

        assertEquals(expected, actual.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Ismn.class)
                .withNonnullFields("registrant", "item", "checkDigit")
                .verify();
    }
}