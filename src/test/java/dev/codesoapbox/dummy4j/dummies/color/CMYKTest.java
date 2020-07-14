package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CMYKTest {

    @ParameterizedTest
    @CsvSource({
            "1.01, 0.8, 0.8, 0.8",
            "-0.01, 0.8, 0.8, 0.8",
            "0.8, 1.01, 0.8, 0.8",
            "0.8, -0.01, 0.8, 0.8",
            "0.8, 0.8, 1.01, 0.8",
            "0.8, 0.8, -0.01, 0.8",
            "0.8, 0.8, 0.8, 1.01",
            "0.8, 0.8, 0.8, -0.01"
    })
    void shouldThrowExceptionOnInvalidValues(float cyan, float magenta, float yellow, float black) {
        assertThrows(ValueOutOfRangeException.class, () -> new CMYK(cyan, magenta, yellow, black));
    }

    @Test
    void shouldPutValuesToArray() {
        CMYK color = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        float[] expected = new float[]{0.1F, 0.2F, 0.3F, 0.4F};
        assertArrayEquals(expected, color.toArray());
    }

    @Test
    void shouldConvertToString() {
        CMYK color = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        assertEquals("cmyk(10%, 20%, 30%, 40%)", color.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(CMYK.class)
                .verify();
    }
}