package dev.codesoapbox.dummy4j;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * Provides random values which are generated based on a seed
 */
public class RandomService {

    private static final String UPPER_BOUND_MUST_BE_POSITIVE = "Upper bound must be positive or zero";
    private static final String LOWER_BOUND_MUST_BE_POSITIVE = "Lower bound must be positive or zero";
    private static final String LOWER_BOUND_MUST_NOT_BE_GREATER_THAN_UPPER = "Lower bound must not be greater than " +
            "upper bound";

    private final long seed;
    private final Random random;

    RandomService(Random random, long seed) {
        this.random = random;
        this.seed = seed;
    }

    /**
     * @since 0.3.0
     */
    public RandomService() {
        this(null);
    }

    public RandomService(Long seed) {
        this.seed = seed != null ? seed : ThreadLocalRandom.current().nextInt();

        this.random = new Random(this.seed);
    }

    public long getSeed() {
        return seed;
    }

    /**
     * Has a {@code howMany} in {@code in} chance to supply a value. Otherwise, returns null.
     * <p>
     * E.g. {@code chance(1, 2, () -> "hello")} has a 1-in-2 chance to supply "hello", that is it will be supplied
     * 50% of the time when the method is invoked.
     *
     * @return supplied {@code T} or null
     */
    public <T> T chance(int howMany, int in, Supplier<T> supplier) {
        if (nextInt(1, in) > howMany) {
            return null;
        }

        return supplier.get();
    }

    /**
     * Returns a random enum value
     *
     * @since 0.1.2
     */
    public <T extends Enum<?>> T enumValue(Class<T> clazz) {
        int x = nextInt(clazz.getEnumConstants().length - 1);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Generates a random UUID
     *
     * @since 0.1.2
     */
    public String uuid() {
        return UUID.nameUUIDFromBytes(String.valueOf(nextLong()).getBytes(StandardCharsets.UTF_8)).toString();
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
        return random.nextInt() & Integer.MAX_VALUE;
    }

    /**
     * Generates a random int between 0 and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Integer.MAX_VALUE} generates a random int between 0 (inclusive)
     * and {@code upperBound} (exclusive).
     */
    public int nextInt(int upperBound) {
        return nextInt(0, upperBound);
    }

    /**
     * Generates a random int between {@code lowerBound} and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Integer.MAX_VALUE} generates a random int between
     * {@code lowerBound} (inclusive) and {@code upperBound} (exclusive).
     */
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
     * Generates a random positive long
     */
    public long nextLong() {
        return random.nextLong() & Long.MAX_VALUE;
    }

    /**
     * Generates a random long between 0 and {@code upperBound} (inclusive).
     * <p>
     * If {@code upperBound} equals {@code Long.MAX_VALUE} generates a random long between 0 (inclusive)
     * and {@code upperBound} (exclusive).
     */
    public long nextLong(long upperBound) {
        return nextLong(0, upperBound);
    }

    /**
     * Generates a random long between @{code lowerBound} and {@code upperBound} (inclusive)
     * <p>
     * If {@code upperBound} equals {@code Long.MAX_VALUE} generates a random long between
     * {@code lowerBound} (inclusive) and {@code upperBound} (exclusive).
     */
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
    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Generates a random double between 0 (inclusive) and {@code upperBound} (exclusive)
     */
    public double nextDouble(double bound) {
        return nextDouble(0, bound);
    }

    /**
     * Generates a random double between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
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
     * Generates a random positive float
     */
    public float nextFloat() {
        return random.nextFloat();
    }

    /**
     * Generates a random double between 0 (inclusive) and {@code upperBound} (exclusive)
     */
    public float nextFloat(float upperBound) {
        return nextFloat(0, upperBound);
    }

    /**
     * Generates a random float between {@code lowerBound} (inclusive) and {@code upperBound} (exclusive)
     */
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
