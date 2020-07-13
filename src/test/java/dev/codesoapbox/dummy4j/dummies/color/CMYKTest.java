package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
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
    void objectsWithSameValuesShouldBeEqual() {
        CMYK cmyk1 = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        CMYK cmyk2 = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        CMYK cmyk3 = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        assertAll(
                () -> assertEquals(cmyk1, cmyk1),
                () -> assertEquals(cmyk1, cmyk3),
                () -> assertEquals(cmyk2, cmyk3),
                () -> assertEquals(cmyk2, cmyk1)
        );
    }

    @Test
    void objectsWithDifferentValuesShouldNotBeEqual() {
        CMYK cmyk1 = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        CMYK cmyk2 = new CMYK(0.9F, 0.2F, 0.3F, 0.4F);
        assertNotEquals(cmyk1, cmyk2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        CMYK cmyk = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        assertNotEquals(cmyk, null);
    }

    @Test
    void shouldGenerateCorrectHashcodeForEqualObjects() {
        CMYK cmyk = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        CMYK cmyk2 = new CMYK(0.1F, 0.2F, 0.3F, 0.4F);
        assertAll(
                () -> assertEquals(cmyk, cmyk2),
                () -> assertEquals(cmyk.hashCode(), cmyk2.hashCode()),
                () -> assertEquals(cmyk.hashCode(), cmyk2.hashCode())
        );
    }
}