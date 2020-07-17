package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HSBATest {

    @ParameterizedTest
    @CsvSource({
            "200, 0.8, 0.8, 1.01",
            "200, 0.8, 0.8, -0.01"
    })
    void shouldThrowExceptionOnInvalidAlpha(float hue, float saturation, float brightness, float alpha) {
        assertThrows(ValueOutOfRangeException.class, () -> new HSBA(hue, saturation, brightness, alpha));
    }

    @Test
    void shouldPutHslValuesToArray() {
        HSBA hsba = new HSBA(200F, 0.1F, 0.2F, 0.1F);
        float[] expected = new float[]{200.0F, 0.1F, 0.2F, 0.1F};
        assertArrayEquals(expected, hsba.toArray());
    }

    @Test
    void shouldConvertToString() {
        HSBA hsba = new HSBA(200F, 0.1F, 0.2F, 0.00F);
        assertEquals("hsba(200, 10%, 20%, 0)", hsba.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(HSBA.class)
                .usingGetClass()
                .verify();
    }
}