package dev.codesoapbox.dummy4j.dummies.color;

/**
 * @since 0.4.0
 */
final class Round {

    private Round() {
    }

    public static float toTwoDecimals(float value) {
        return (float) (Math.round(value * 100.0F) / 100.0);
    }
}
