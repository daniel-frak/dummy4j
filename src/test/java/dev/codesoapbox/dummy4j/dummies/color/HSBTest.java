package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HSBTest {

    @ParameterizedTest
    @CsvSource({
            "200, 1.01, 0.8",
            "200, -0.01, 0.8",
            "200, 0.8, 1.01",
            "200, 0.8, -0.01",
            "360.01, 0.8, 0.8",
            "-360.01, 0.8, 0.8"
    })
    void shouldThrowExceptionOnInvalidValues(float hue, float saturation, float brightness) {
        assertThrows(ValueOutOfRangeException.class, () -> new HSB(hue, saturation, brightness));
    }

    @Test
    void shouldAcceptNegativeHue() {
        HSB hsb = new HSB(-200F, 0.1F, 0.2F);
        assertEquals(-200F, hsb.getHue());
    }

    @Test
    void shouldPutHsbValuesToArray() {
        HSB hsb = new HSB(200F, 0.1F, 0.2F);
        float[] expected = new float[]{200.0F, 0.1F, 0.2F};
        assertArrayEquals(expected, hsb.toArray());
    }

    @Test
    void shouldConvertToString() {
        HSB hsb = new HSB(200F, 0.1F, 0.2F);
        assertEquals("hsb(200, 10%, 20%)", hsb.toString());
    }

    @Test
    void objectsWithSameValuesShouldBeEqual() {
        HSB hsb1 = new HSB(200F, 0.1F, 0.2F);
        HSB hsb2 = new HSB(200F, 0.1F, 0.2F);
        HSB hsb3 = new HSB(200F, 0.1F, 0.2F);
        assertAll(
                () -> assertEquals(hsb1, hsb1),
                () -> assertEquals(hsb1, hsb3),
                () -> assertEquals(hsb2, hsb3),
                () -> assertEquals(hsb2, hsb1)
        );
    }

    @Test
    void objectsWithDifferentValuesShouldNotBeEqual() {
        HSB hsb1 = new HSB(200F, 0.1F, 0.2F);
        HSB hsb2 = new HSB(300F, 0.1F, 0.2F);
        assertNotEquals(hsb1, hsb2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        HSB hsb = new HSB(200F, 0.1F, 0.2F);
        assertNotEquals(hsb, null);
    }

    @Test
    void shouldGenerateCorrectHashcodeForEqualObjects() {
        HSB hsb = new HSB(200F, 0.1F, 0.2F);
        HSB hsb2 = new HSB(200F, 0.1F, 0.2F);
        assertAll(
                () -> assertEquals(hsb, hsb2),
                () -> assertEquals(hsb.hashCode(), hsb2.hashCode()),
                () -> assertEquals(hsb.hashCode(), hsb2.hashCode())
        );
    }
}