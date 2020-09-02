package dev.codesoapbox.dummy4j;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A default implementation of RandomService
 *
 * @since 0.5.0
 */
public class DefaultRandomService implements RandomService {

    private static final String UPPER_BOUND_MUST_BE_POSITIVE = "Upper bound must be positive or zero";
    private static final String LOWER_BOUND_MUST_BE_POSITIVE = "Lower bound must be positive or zero";
    private static final String LOWER_BOUND_MUST_NOT_BE_GREATER_THAN_UPPER = "Lower bound must not be greater than " +
            "upper bound";

    private final long seed;
    private final Random random;

    DefaultRandomService(Random random, long seed) {
        this.random = random;
        this.seed = seed;
    }

    /**
     * @since 0.3.0
     */
    public DefaultRandomService() {
        this(null);
    }

    public DefaultRandomService(Long seed) {
        this.seed = seed != null ? seed : ThreadLocalRandom.current().nextInt();

        this.random = new Random(this.seed);
    }

    @Override
    public long getSeed() {
        return seed;
    }

    /**
     * Generates a random positive long
     */
    @Override
    public long nextLong() {
        return random.nextLong() & Long.MAX_VALUE;
    }

    /**
     * Generates a random boolean
     */
    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * Generates a random positive int
     */
    @Override
    public int nextInt() {
        return random.nextInt() & Integer.MAX_VALUE;
    }

    /**
     * Generates a random int between 0 and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Integer.MAX_VALUE} generates a random int between 0 (inclusive)
     * and {@code upperBound} (exclusive).
     */
    @Override
    public int nextInt(int upperBound) {
        return nextInt(0, upperBound);
    }

    /**
     * Generates a random int between {@code lowerBound} and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Integer.MAX_VALUE} generates a random int between
     * {@code lowerBound} (inclusive) and {@code upperBound} (exclusive).
     */
    @Override
    public int nextInt(int lowerBound, int upperBound) {
        if (lowerBound < 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < lowerBound) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_NOT_BE_GREATER_THAN_UPPER);
        }
        if (upperBound == Integer.MAX_VALUE && lowerBound != Integer.MAX_VALUE) {
            upperBound--;
        }

        return lowerBound + random.nextInt(upperBound - lowerBound + 1);
    }

    /**
     * Generates a random long between 0 and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Long.MAX_VALUE} generates a random long between 0 (inclusive)
     * and {@code upperBound} (exclusive).
     */
    @Override
    public long nextLong(long upperBound) {
        return nextLong(0, upperBound);
    }

    /**
     * Generates a random long between @{code lowerBound} and {@code upperBound} (inclusive)
     * <p>
     * If {@code upperBound} equals {@code Long.MAX_VALUE} generates a random long between
     * {@code lowerBound} (inclusive) and {@code upperBound} (exclusive).
     */
    @Override
    public long nextLong(long lowerBound, long upperBound) {
        if (lowerBound < 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < lowerBound) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_NOT_BE_GREATER_THAN_UPPER);
        }
        if (upperBound == Long.MAX_VALUE && lowerBound != Long.MAX_VALUE) {
            upperBound--;
        }

        return lowerBound + (long) (random.nextDouble() * (upperBound - lowerBound + 1));
    }

    /**
     * Generates a random positive double
     */
    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Generates a random double between 0 (inclusive) and {@code upperBound} (exclusive)
     */
    @Override
    public double nextDouble(double bound) {
        return nextDouble(0, bound);
    }

    /**
     * Generates a random double between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
    @Override
    public double nextDouble(double lowerBound, double upperBound) {
        if (lowerBound < 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < lowerBound) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_NOT_BE_GREATER_THAN_UPPER);
        }

        return lowerBound + (upperBound - lowerBound) * random.nextDouble();
    }

    /**
     * Generates a random positive float between 0 and 1 (inclusive)
     */
    @Override
    public float nextFloat() {
        return nextFloat(1);
    }

    /**
     * Generates a random double between 0 (inclusive) and {@code upperBound} (exclusive)
     */
    @Override
    public float nextFloat(float upperBound) {
        return nextFloat(0, upperBound);
    }

    /**
     * Generates a random float between {@code lowerBound} and {@code upperBound} (inclusive)
     */
    @Override
    public float nextFloat(float lowerBound, float upperBound) {
        if (lowerBound < 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < lowerBound) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_NOT_BE_GREATER_THAN_UPPER);
        }

        return (random.nextFloat() * (upperBound - lowerBound)) + lowerBound;
    }
}
