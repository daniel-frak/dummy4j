package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HSLTest {

    @ParameterizedTest
    @CsvSource({
            "200, 1.01, 0.8",
            "200, -0.01, 0.8",
            "200, 0.8, 1.01",
            "200, 0.8, -0.01",
            "360.01, 0.8, 0.8",
            "-360.01, 0.8, 0.8"
    })
    void shouldThrowExceptionOnInvalidValues(float hue, float saturation, float lightness) {
        assertThrows(ValueOutOfRangeException.class, () -> new HSL(hue, saturation, lightness));
    }

    @Test
    void shouldAcceptNegativeHue() {
        HSL hsl = new HSL(-200F, 0.1F, 0.2F);
        assertEquals(-200F, hsl.getHue());
    }

    @Test
    void shouldPutHslValuesToArray() {
        HSL hsl = new HSL(200F, 0.1F, 0.2F);
        float[] expected = new float[]{200.0F, 0.1F, 0.2F};
        assertArrayEquals(expected, hsl.toArray());
    }

    @Test
    void shouldConvertToString() {
        HSL hsl = new HSL(200F, 0.1F, 0.2F);
        assertEquals("hsl(200, 10%, 20%)", hsl.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(HSL.class)
                .usingGetClass()
                .verify();
    }
}