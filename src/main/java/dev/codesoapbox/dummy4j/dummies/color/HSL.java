package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.dummies.shared.math.NumberFormatter;
import dev.codesoapbox.dummy4j.dummies.shared.math.NumberValidator;
import dev.codesoapbox.dummy4j.dummies.shared.math.Round;

import java.util.Objects;

/**
 * A representation of color in the HSL (hue, saturation, lightness) model.
 *
 * @since 0.4.0
 */
public class HSL {

    final float hue;
    final float saturation;
    final float lightness;

    public HSL(float hue, float saturation, float lightness) {
        validate(hue, saturation, lightness);
        this.hue = Round.toTwoDecimals(hue);
        this.saturation = Round.toTwoDecimals(saturation);
        this.lightness = Round.toTwoDecimals(lightness);
    }

    private void validate(float hue, float saturation, float lightness) {
        NumberValidator.inRange(hue, -ColorDummy.MAX_ANGLE, ColorDummy.MAX_ANGLE);
        NumberValidator.betweenZeroAndOne(saturation);
        NumberValidator.betweenZeroAndOne(lightness);
    }

    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getLightness() {
        return lightness;
    }

    public float[] toArray() {
        return new float[]{hue, saturation, lightness};
    }

    @Override
    public String toString() {
        return "hsl(" +
                NumberFormatter.toTwoDecimals(hue) +
                ", " +
                NumberFormatter.toPercent(saturation) +
                ", " +
                NumberFormatter.toPercent(lightness) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HSL hsl = (HSL) o;
        return Float.compare(hsl.hue, hue) == 0 &&
                Float.compare(hsl.saturation, saturation) == 0 &&
                Float.compare(hsl.lightness, lightness) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hue, saturation, lightness);
    }
}
