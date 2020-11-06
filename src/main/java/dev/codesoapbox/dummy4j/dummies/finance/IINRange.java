package dev.codesoapbox.dummy4j.dummies.finance;

final class IINRange {

    int min;
    int max;

    private IINRange() {
    }

    public static IINRange from(int min, int max) {
        IINRange range = new IINRange();
        range.min = min;
        range.max = max;

        return range;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
