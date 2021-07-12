package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import dev.codesoapbox.dummy4j.exceptions.InvalidIsbnParameterException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IsbnRegistrationGroupRangeTest {

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "1, -1",
            "-1, -1",
            "1, 100000"
    })
    void shouldThrowExceptionOnInvalidRange(int min, int max) {
        assertThrows(InvalidIsbnParameterException.class, () -> IsbnRegistrationGroupRange.from(min, max));
    }

    @Test
    void shouldConvertToString() {
        String expected = "IsbnRegistrationGroupRange{min=1, max=2}";
        String actual = IsbnRegistrationGroupRange.from(1, 2).toString();

        assertEquals(expected, actual);
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(IsbnRegistrationGroupRange.class)
                .withNonnullFields("min", "max")
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}