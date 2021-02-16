package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.dummies.shared.math.NumberFormatter;
import dev.codesoapbox.dummy4j.dummies.shared.math.NumberValidator;
import dev.codesoapbox.dummy4j.dummies.shared.math.Round;

import java.util.Objects;

/**
 * A representation of color in the HSB (hue, saturation, brightness) model.
 *
 * @since 0.4.0
 */
public class HSB {

    final float hue;
    final float saturation;
    final float brightness;

    public HSB(float hue, float saturation, float brightness) {
        validate(hue, saturation, brightness);
        this.hue = Round.toTwoDecimals(hue);
        this.saturation = Round.toTwoDecimals(saturation);
        this.brightness = Round.toTwoDecimals(brightness);
    }

    private void validate(float hue, float saturation, float brightness) {
        NumberValidator.inRange(hue, -ColorDummy.MAX_ANGLE, ColorDummy.MAX_ANGLE);
        NumberValidator.betweenZeroAndOne(saturation);
        NumberValidator.betweenZeroAndOne(brightness);
    }

    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    public float[] toArray() {
        return new float[]{hue, saturation, brightness};
    }

    @Override
    public String toString() {
        return "hsb(" +
                NumberFormatter.toTwoDecimals(hue) +
                ", " +
                NumberFormatter.toPercent(saturation) +
                ", " +
                NumberFormatter.toPercent(brightness) +
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
        HSB hsb = (HSB) o;
        return Float.compare(hsb.hue, hue) == 0 &&
                Float.compare(hsb.saturation, saturation) == 0 &&
                Float.compare(hsb.brightness, brightness) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hue, saturation, brightness);
    }
}
