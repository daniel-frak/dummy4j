package dev.codesoapbox.dummy4j.dummies.finance;

import java.util.Objects;

final class IINRange {

    private int min;
    private int max;

    private IINRange() {
    }

    static IINRange from(int min, int max) {
        IINRange range = new IINRange();
        range.min = min;
        range.max = max;

        return range;
    }

    int getMin() {
        return min;
    }

    int getMax() {
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
        IINRange iinRange = (IINRange) o;
        return min == iinRange.min &&
                max == iinRange.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "IINRange{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
