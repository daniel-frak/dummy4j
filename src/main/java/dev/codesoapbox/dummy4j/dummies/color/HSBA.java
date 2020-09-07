package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.dummies.shared.math.NumberFormatter;
import dev.codesoapbox.dummy4j.dummies.shared.math.NumberValidator;
import dev.codesoapbox.dummy4j.dummies.shared.math.Round;

import java.util.Objects;

/**
 * A representation of color in the HSBA (hue, saturation, brightness, alpha) model.
 *
 * @since 0.4.0
 */
public class HSBA extends HSB {

    private final float alpha;

    public HSBA(float hue, float saturation, float brightness, float alpha) {
        super(hue, saturation, brightness);
        NumberValidator.betweenZeroAndOne(alpha);
        this.alpha = Round.toTwoDecimals(alpha);
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public float[] toArray() {
        return new float[]{hue, saturation, brightness, alpha};
    }

    @Override
    public String toString() {
        return "hsba(" +
                NumberFormatter.toTwoDecimals(hue) +
                ", " +
                NumberFormatter.toPercent(saturation) +
                ", " +
                NumberFormatter.toPercent(brightness) +
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
        HSBA hsba = (HSBA) o;
        return Float.compare(hsba.alpha, alpha) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), alpha);
    }
}
