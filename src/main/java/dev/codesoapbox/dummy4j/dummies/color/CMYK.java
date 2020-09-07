package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.dummies.shared.math.NumberFormatter;
import dev.codesoapbox.dummy4j.dummies.shared.math.NumberValidator;
import dev.codesoapbox.dummy4j.dummies.shared.math.Round;

import java.util.Objects;

/**
 * A representation of color in the CMYK (cyan, magenta, yellow, black) model.
 *
 * @since 0.4.0
 */
public final class CMYK {

    private final float cyan;
    private final float magenta;
    private final float yellow;
    private final float black;

    public CMYK(float cyan, float magenta, float yellow, float black) {
        validate(cyan, magenta, yellow, black);
        this.cyan = Round.toTwoDecimals(cyan);
        this.magenta = Round.toTwoDecimals(magenta);
        this.yellow = Round.toTwoDecimals(yellow);
        this.black = Round.toTwoDecimals(black);
    }

    private void validate(float cyan, float magenta, float yellow, float black) {
        NumberValidator.betweenZeroAndOne(cyan);
        NumberValidator.betweenZeroAndOne(magenta);
        NumberValidator.betweenZeroAndOne(yellow);
        NumberValidator.betweenZeroAndOne(black);
    }

    public float getCyan() {
        return cyan;
    }

    public float getMagenta() {
        return magenta;
    }

    public float getYellow() {
        return yellow;
    }

    public float getBlack() {
        return black;
    }

    public float[] toArray() {
        return new float[]{cyan, magenta, yellow, black};
    }

    @Override
    public String toString() {
        return "cmyk(" +
                NumberFormatter.toPercent(cyan) +
                ", " +
                NumberFormatter.toPercent(magenta) +
                ", " +
                NumberFormatter.toPercent(yellow) +
                ", " +
                NumberFormatter.toPercent(black) +
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
        CMYK cmyk = (CMYK) o;
        return Float.compare(cmyk.cyan, cyan) == 0 &&
                Float.compare(cmyk.magenta, magenta) == 0 &&
                Float.compare(cmyk.yellow, yellow) == 0 &&
                Float.compare(cmyk.black, black) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cyan, magenta, yellow, black);
    }
}
