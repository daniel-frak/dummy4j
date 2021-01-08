package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import dev.codesoapbox.dummy4j.dummies.shared.math.NumberValidator;
import dev.codesoapbox.dummy4j.exceptions.InvalidIsmnParameterException;
import dev.codesoapbox.dummy4j.exceptions.InvalidRangeException;
import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;

import java.util.Objects;

final class IsmnRegistrantRange {

    private static final int MIN_REGISTRANT = 0;

    private int min;
    private int max;

    private IsmnRegistrantRange() {
    }

    static IsmnRegistrantRange from(int min, int max) {
        validateRange(min, max);
        IsmnRegistrantRange range = new IsmnRegistrantRange();
        range.min = min;
        range.max = max;

        return range;
    }

    private static void validateRange(int min, int max) {
        try {
            NumberValidator.inRange(min, MIN_REGISTRANT, max);
        } catch (ValueOutOfRangeException | InvalidRangeException e) {
            String message = String.format("Invalid registrant range from %d to %d", min, max);
            throw new InvalidIsmnParameterException(message, e);
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IsmnRegistrantRange that = (IsmnRegistrantRange) o;
        return min == that.min && max == that.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "IsmnRegistrantRange{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
