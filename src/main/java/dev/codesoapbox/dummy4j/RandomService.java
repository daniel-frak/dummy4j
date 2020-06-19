package dev.codesoapbox.dummy4j;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides random values which are generated based on a seed
 */
public class RandomService {

    private static final String UPPER_BOUND_MUST_BE_POSITIVE = "Upper bound must be positive";
    private static final String LOWER_BOUND_MUST_BE_POSITIVE = "Lower bound must be positive";

    private final long seed;
    private final Random random;

    public RandomService(Long seed) {
        this.seed = seed != null ? seed : ThreadLocalRandom.current().nextInt();

        this.random = new Random(this.seed);
    }

    public long getSeed() {
        return seed;
    }

    /**
     * Generates a random boolean
     */
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * Generates a random positive int
     */
    public int nextInt() {
        return random.nextInt() & Integer.MAX_VALUE; // zero out the sign bit
    }

    /**
     * Generates a random int between 1 (inclusive) and {@code upperBound} (exclusive)
     */
    public int nextInt(int upperBound) {
        return random.nextInt(upperBound);
    }


    /**
     * Generates a random int between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
    public int nextInt(int lowerBound, int upperBound) {
        return lowerBound + random.nextInt(upperBound - lowerBound);
    }

    /**
     * Generates a random positive long
     */
    public long nextLong() {
        return random.nextLong() & Long.MAX_VALUE;
    }

    /**
     * Generates a random long between 1 (inclusive) and {@code upperBound} (exclusive)
     */
    public long nextLong(long lowerBound, long upperBound) {
        return lowerBound + nextLong(upperBound - lowerBound);
    }

    /**
     * Generates a random long between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
    public long nextLong(long upperBound) {
        /*
        Based on:
        https://stackoverflow.com/a/2546186
         */
        if (upperBound <= 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }

        long bits;
        long val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % upperBound;
        } while (bits - val + (upperBound - 1) < 0L);
        return val;
    }

    /**
     * Generates a random positive double
     */
    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Generates a random double between 1 (inclusive) and {@code upperBound} (exclusive)
     */
    public double nextDouble(double bound) {
        return nextDouble(1, bound);
    }

    /**
     * Generates a random double between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
    public double nextDouble(double lowerBound, double upperBound) {
        if (lowerBound <= 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound <= 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }

        return lowerBound + (upperBound - lowerBound) * random.nextDouble();
    }

    /**
     * Generates a random positive float
     */
    public float nextFloat() {
        return random.nextFloat();
    }

    /**
     * Generates a random double between 1 (inclusive) and {@code upperBound} (exclusive)
     */
    public float nextFloat(float upperBound) {
        return nextFloat(1, upperBound);
    }

    /**
     * Generates a random float between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
    public float nextFloat(float lowerBound, float upperBound) {
        if (lowerBound <= 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound <= 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }

        return (new Random().nextFloat() * (upperBound - lowerBound)) + lowerBound;
    }
}
