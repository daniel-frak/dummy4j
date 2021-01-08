package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import dev.codesoapbox.dummy4j.exceptions.InvalidIsmnParameterException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IsmnRegistrantRangeTest {

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "1, -1",
            "-1, -1"
    })
    void shouldThrowExceptionOnInvalidRange(int min, int max) {
        assertThrows(InvalidIsmnParameterException.class, () -> IsmnRegistrantRange.from(min, max));
    }

    @Test
    void shouldConvertToString() {
        String expected = "IsmnRegistrantRange{min=1, max=2}";
        String actual = IsmnRegistrantRange.from(1, 2).toString();

        assertEquals(expected, actual);
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(IsmnRegistrantRange.class)
                .withNonnullFields("min", "max")
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}