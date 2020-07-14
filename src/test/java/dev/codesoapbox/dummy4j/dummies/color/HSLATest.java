package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HSLATest {

    @ParameterizedTest
    @CsvSource({
            "200, 0.8, 0.8, 1.01",
            "200, 0.8, 0.8, -0.01"
    })
    void shouldThrowExceptionOnInvalidAlpha(float hue, float saturation, float lightness, float alpha) {
        assertThrows(ValueOutOfRangeException.class, () -> new HSLA(hue, saturation, lightness, alpha));
    }

    @Test
    void shouldPutHslValuesToArray() {
        HSLA hsla = new HSLA(200F, 0.1F, 0.2F, 0.1F);
        float[] expected = new float[]{200.0F, 0.1F, 0.2F, 0.1F};
        assertArrayEquals(expected, hsla.toArray());
    }

    @Test
    void shouldConvertToString() {
        HSLA hsla = new HSLA(200F, 0.1F, 0.2F, 0.00F);
        assertEquals("hsla(200, 10%, 20%, 0)", hsla.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(HSLA.class)
                .usingGetClass()
                .verify();
    }
}