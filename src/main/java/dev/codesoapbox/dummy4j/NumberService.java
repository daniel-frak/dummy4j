package dev.codesoapbox.dummy4j;

/**
 * @since 0.5.0
 */
public interface NumberService {

    /**
     * Generates a random positive long
     */
    long nextLong();

    /**
     * Generates a random positive int
     */
    int nextInt();

    /**
     * Generates a random int between 0 and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Integer.MAX_VALUE} generates a random int between 0 (inclusive)
     * and {@code upperBound} (exclusive).
     */
    int nextInt(int upperBound);

    /**
     * Generates a random int between {@code lowerBound} and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Integer.MAX_VALUE} generates a random int between
     * {@code lowerBound} (inclusive) and {@code upperBound} (exclusive).
     */
    int nextInt(int lowerBound, int upperBound);

    /**
     * Generates a random long between 0 and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Long.MAX_VALUE} generates a random long between 0 (inclusive)
     * and {@code upperBound} (exclusive).
     */
    long nextLong(long upperBound);

    /**
     * Generates a random long between @{code lowerBound} and {@code upperBound} (inclusive)
     * <p>
     * If {@code upperBound} equals {@code Long.MAX_VALUE} generates a random long between
     * {@code lowerBound} (inclusive) and {@code upperBound} (exclusive).
     */
    long nextLong(long lowerBound, long upperBound);

    /**
     * Generates a random positive double
     */
    double nextDouble();

    /**
     * Generates a random double between 0 (inclusive) and {@code upperBound} (exclusive)
     */
    double nextDouble(double upperBound);

    /**
     * Generates a random double between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
    double nextDouble(double lowerBound, double upperBound);

    /**
     * Generates a random positive float between 0 and 1 (inclusive)
     */
    float nextFloat();

    /**
     * Generates a random float between 0 (inclusive) and {@code upperBound} (exclusive)
     */
    float nextFloat(float upperBound);

    /**
     * Generates a random float between {@code lowerBound} and {@code upperBound} (inclusive)
     */
    float nextFloat(float lowerBound, float upperBound);

    /**
     * Generates a string with a given length composed of random digits.
     * <p>
     * Returns empty string if {@code howMany} is equal or smaller than 0.
     *
     * @since SNAPSHOT
     */
    String digits(int howMany);
}
