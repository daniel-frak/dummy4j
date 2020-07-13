package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ColorDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void primaryName() {
        String value = dummy4j.color().primaryName();
        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty())
        );
    }

    @Test
    void basicName() {
        String value = dummy4j.color().basicName();
        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty())
        );
    }

    @Test
    void additionalName() {
        String value = dummy4j.color().additionalName();
        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty())
        );
    }

    @Test
    void name() {
        String value = dummy4j.color().name();
        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty())
        );
    }

    @Test
    void hex() {
        String value = dummy4j.color().hex();
        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty())
        );
    }

    @Test
    void hexAlpha() {
        String value = dummy4j.color().hexAlpha();
        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty())
        );
    }

    @Test
    void rgb() {
        Color value = dummy4j.color().rgb();
        assertNotNull(value);
    }

    @Test
    void rgba() {
        Color value = dummy4j.color().rgba();
        assertNotNull(value);
    }

    @Test
    void hsb() {
        HSB value = dummy4j.color().hsb();
        assertNotNull(value);
    }

    @Test
    void hsba() {
        HSBA value = dummy4j.color().hsba();
        assertNotNull(value);
    }

    @Test
    void hsl() {
        HSL value = dummy4j.color().hsl();
        assertNotNull(value);
    }

    @Test
    void hsla() {
        HSLA value = dummy4j.color().hsla();
        assertNotNull(value);
    }

    @Test
    void cmyk() {
        CMYK value = dummy4j.color().cmyk();
        assertNotNull(value);
    }
}
