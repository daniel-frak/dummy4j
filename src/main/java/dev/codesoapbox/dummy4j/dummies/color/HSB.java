package dev.codesoapbox.dummy4j.dummies.color;

import java.util.Objects;

import static dev.codesoapbox.dummy4j.dummies.color.NumberValidator.MAX_ANGLE;

/**
 * A representation of color in the HSB (hue, saturation, brightness) model.
 *
 * @since 0.4.0
 */
public class HSB {

    protected final float hue;
    protected final float saturation;
    protected final float brightness;

    public HSB(float hue, float saturation, float brightness) {
        validate(hue, saturation, brightness);
        this.hue = Round.toTwoDecimals(hue);
        this.saturation = Round.toTwoDecimals(saturation);
        this.brightness = Round.toTwoDecimals(brightness);
    }

    protected void validate(float hue, float saturation, float brightness) {
        NumberValidator.inRange(hue, -MAX_ANGLE, MAX_ANGLE);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
