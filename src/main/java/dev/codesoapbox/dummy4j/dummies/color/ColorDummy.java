package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.awt.*;

import static dev.codesoapbox.dummy4j.dummies.color.NumberValidator.*;

/**
 * @since 0.4.0
 */
public class ColorDummy {

    private final Dummy4j dummy4j;

    public ColorDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String primaryName() {
        return dummy4j.expressionResolver().resolveKey("color.primary_name");
    }

    public String basicName() {
        return dummy4j.expressionResolver().resolve("#{color.basic_name}");
    }

    public String additionalName() {
        return dummy4j.expressionResolver().resolveKey("color.additional_name");
    }

    public String name() {
        return dummy4j.expressionResolver().resolve("#{color.name}");
    }

    public String hex() {
        return String.format("#%06x", dummy4j.random().nextInt(MAX_RGB));
    }

    public String hexAlpha() {
        return String.format("#%08x", dummy4j.random().nextLong(MAX_RGBA));
    }

    public Color rgb() {
        return new Color(dummy4j.random().nextInt(MAX_RGB));
    }

    public Color rgba() {
        int randomValue = (int) dummy4j.random().nextLong(MAX_RGBA);
        return new Color(randomValue, true);
    }

    public HSB hsb() {
        return new HSB(dummy4j.random().nextFloat(MAX_ANGLE), dummy4j.random().nextFloat(),
                dummy4j.random().nextFloat());
    }

    public HSBA hsba() {
        return new HSBA(dummy4j.random().nextFloat(MAX_ANGLE), dummy4j.random().nextFloat(),
                dummy4j.random().nextFloat(), dummy4j.random().nextFloat());
    }

    public HSL hsl() {
        return new HSL(dummy4j.random().nextFloat(MAX_ANGLE), dummy4j.random().nextFloat(),
                dummy4j.random().nextFloat());
    }

    public HSLA hsla() {
        return new HSLA(dummy4j.random().nextFloat(MAX_ANGLE), dummy4j.random().nextFloat(),
                dummy4j.random().nextFloat(), dummy4j.random().nextFloat());
    }

    public CMYK cmyk() {
        return new CMYK(dummy4j.random().nextFloat(), dummy4j.random().nextFloat(), dummy4j.random().nextFloat(),
                dummy4j.random().nextFloat());
    }
}
