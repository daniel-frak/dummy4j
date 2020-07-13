package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
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
    void objectsWithSameValuesShouldBeEqual() {
        HSL hsl1 = new HSL(200F, 0.1F, 0.2F);
        HSL hsl2 = new HSL(200F, 0.1F, 0.2F);
        HSL hsl3 = new HSL(200F, 0.1F, 0.2F);
        assertAll(
                () -> assertEquals(hsl1, hsl1),
                () -> assertEquals(hsl1, hsl3),
                () -> assertEquals(hsl2, hsl3),
                () -> assertEquals(hsl2, hsl1)
        );
    }

    @Test
    void objectsWithDifferentValuesShouldNotBeEqual() {
        HSL hsl1 = new HSL(200F, 0.1F, 0.2F);
        HSL hsl2 = new HSL(300F, 0.1F, 0.2F);
        assertNotEquals(hsl1, hsl2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        HSL hsl = new HSL(200F, 0.1F, 0.2F);
        assertNotEquals(hsl, null);
    }

    @Test
    void shouldGenerateCorrectHashcodeForEqualObjects() {
        HSL hsl = new HSL(0.2F, 0.1F, 0.2F);
        HSL hsl2 = new HSL(0.2F, 0.1F, 0.2F);
        assertAll(
                () -> assertEquals(hsl, hsl2),
                () -> assertEquals(hsl.hashCode(), hsl.hashCode()),
                () -> assertEquals(hsl.hashCode(), hsl2.hashCode())
        );
    }
}