package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.awt.*;

import static dev.codesoapbox.dummy4j.dummies.color.NumberValidator.*;

/**
 * Provides methods for generating random colors in various formats
 *
 * @since 0.4.0
 */
public class ColorDummy {

    private final Dummy4j dummy4j;

    public ColorDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Provides a color name chosen at random from a set of primary colors.
     * E.g. {@code blue}
     */
    public String primaryName() {
        return dummy4j.expressionResolver().resolve("#{color.primary_name}");
    }

    /**
     * Provides a color name chosen at random from a list containing primary and popular colors.
     * E.g. {@code orange}
     */
    public String basicName() {
        return dummy4j.expressionResolver().resolve("#{color.basic_name}");
    }

    /**
     * Provides a color name chosen at random from a list of unconventional colors.
     * E.g. {@code scarlet}
     */
    public String additionalName() {
        return dummy4j.expressionResolver().resolve("#{color.additional_name}");
    }

    /**
     * Provides a color name chosen at random from a list containing primary, popular and unconventional colors.
     * E.g. {@code magenta}
     */
    public String name() {
        return dummy4j.expressionResolver().resolve("#{color.name}");
    }

    /**
     * Generates a random hexadecimal color.
     * E.g. {@code #b58502}
     */
    public String hex() {
        return String.format("#%06x", dummy4j.number().nextInt(MAX_RGB));
    }

    /**
     * Generates a random hexadecimal color with alpha channel.
     * E.g. {@code #e7e247d5}
     */
    public String hexAlpha() {
        return String.format("#%08x", dummy4j.number().nextLong(MAX_RGBA));
    }

    /**
     * Generates an instance of Color with randomized {@code red}, {@code green} and {@code blue} values.
     * E.g. {@code java.awt.Color[r=181,g=133,b=2]}
     * @see Color
     */
    public Color rgb() {
        return new Color(dummy4j.number().nextInt(MAX_RGB));
    }

    /**
     * Generates an instance of Color with randomized {@code red}, {@code green}, {@code blue} and {@code alpha} values.
     * E.g. {@code java.awt.Color[r=226,g=71,b=213], alpha = 231}
     * @see Color
     */
    public Color rgba() {
        int randomValue = (int) dummy4j.number().nextLong(MAX_RGBA);
        return new Color(randomValue, true);
    }

    /**
     * Generates a random HSB color.
     * E.g. {@code hsb(150.17, 34%, 34%)}
     * @see HSB
     */
    public HSB hsb() {
        return new HSB(dummy4j.number().nextFloat(MAX_ANGLE), dummy4j.number().nextFloat(),
                dummy4j.number().nextFloat());
    }

    /**
     * Generates a random HSBA color.
     * E.g. {@code hsba(150.17, 34%, 34%, 0.34)}
     * @see HSBA
     */
    public HSBA hsba() {
        return new HSBA(dummy4j.number().nextFloat(MAX_ANGLE), dummy4j.number().nextFloat(),
                dummy4j.number().nextFloat(), dummy4j.number().nextFloat());
    }

    /**
     * Generates a random HSL color.
     * E.g. {@code hsl(150.17, 34%, 34%)}
     * @see HSL
     */
    public HSL hsl() {
        return new HSL(dummy4j.number().nextFloat(MAX_ANGLE), dummy4j.number().nextFloat(),
                dummy4j.number().nextFloat());
    }

    /**
     * Generates a random HSLA color.
     * E.g. {@code hsla(150.17, 34%, 34%, 0.34)}
     * @see HSLA
     */
    public HSLA hsla() {
        return new HSLA(dummy4j.number().nextFloat(MAX_ANGLE), dummy4j.number().nextFloat(),
                dummy4j.number().nextFloat(), dummy4j.number().nextFloat());
    }

    /**
     * Generates a random CMYK color.
     * E.g. {@code cmyk(15%, 34%, 20%, 75%)}
     * @see CMYK
     */
    public CMYK cmyk() {
        return new CMYK(dummy4j.number().nextFloat(), dummy4j.number().nextFloat(), dummy4j.number().nextFloat(),
                dummy4j.number().nextFloat());
    }
}
