package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.dummies.sharedmath.NumberFormatter;
import dev.codesoapbox.dummy4j.dummies.sharedmath.NumberValidator;
import dev.codesoapbox.dummy4j.dummies.sharedmath.Round;

import java.util.Objects;

/**
 * A representation of color in the HSLA (hue, saturation, lightness, alpha) model.
 *
 * @since 0.4.0
 */
public class HSLA extends HSL {

    private final float alpha;

    public HSLA(float hue, float saturation, float lightness, float alpha) {
        super(hue, saturation, lightness);
        NumberValidator.betweenZeroAndOne(alpha);
        this.alpha = Round.toTwoDecimals(alpha);
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public float[] toArray() {
        return new float[]{hue, saturation, lightness, alpha};
    }

    @Override
    public String toString() {
        return "hsla(" +
                NumberFormatter.toTwoDecimals(hue) +
                ", " +
                NumberFormatter.toPercent(saturation) +
                ", " +
                NumberFormatter.toPercent(lightness) +
                ", " +
                NumberFormatter.toTwoDecimals(alpha) +
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
        if (!super.equals(o)) {
            return false;
        }
        HSLA hsla = (HSLA) o;
        return Float.compare(hsla.alpha, alpha) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), alpha);
    }
}
