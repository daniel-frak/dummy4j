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

    private final long seed;
    private final Random random;

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
        if(nextInt(1, in) > howMany) {
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
        int x = nextInt(clazz.getEnumConstants().length);
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
        return random.nextInt() & Integer.MAX_VALUE; // zero out the sign bit
    }

    /**
     * Generates a random int between 0 and {@code upperBound} (inclusive)
     */
    public int nextInt(int upperBound) {
        return nextInt(0, upperBound);
    }

    /**
     * Generates a random int between {@code lowerBound} and {@code upperBound} (inclusive)
     */
    public int nextInt(int lowerBound, int upperBound) {
        return lowerBound + random.nextInt(upperBound - lowerBound + 1);
    }

    /**
     * Generates a random positive long
     */
    public long nextLong() {
        return random.nextLong() & Long.MAX_VALUE;
    }

    /**
     * Generates a random long between @{code lowerBound} and {@code upperBound} (inclusive)
     */
    public long nextLong(long lowerBound, long upperBound) {
        return lowerBound + nextLong(upperBound - lowerBound);
    }

    /**
     * Generates a random long between 0 and {@code upperBound} inclusive
     */
    public long nextLong(long upperBound) {
        /*
        Based on:
        https://stackoverflow.com/a/2546186
        Modified to make upperBound inclusive.
         */
        if (upperBound < 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }

        long bits;
        long val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % upperBound + 1;
        } while (bits - val + upperBound < 0L);
        return val;
    }

    /**
     * Generates a random positive double
     */
    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Generates a random double between 0 and {@code upperBound} (inclusive)
     */
    public double nextDouble(double bound) {
        return nextDouble(0, bound);
    }

    /**
     * Generates a random double between {@code lowerBound} and {@code upperBound} (inclusive)
     */
    public double nextDouble(double lowerBound, double upperBound) {
        if (lowerBound < 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }

        return lowerBound + (upperBound - lowerBound) * random.nextDouble();
    }

    /**
     * Generates a random positive float between 0 and 1 (inclusive)
     */
    public float nextFloat() {
        return this.nextFloat(1);
    }

    /**
     * Generates a random double between 0 and {@code upperBound} (inclusive)
     */
    public float nextFloat(float upperBound) {
        return nextFloat(0, upperBound);
    }

    /**
     * Generates a random float between {@code lowerBound} and {@code upperBound} (inclusive)
     */
    public float nextFloat(float lowerBound, float upperBound) {
        if (lowerBound < 0) {
            throw new IllegalArgumentException(LOWER_BOUND_MUST_BE_POSITIVE);
        }
        if (upperBound < 0) {
            throw new IllegalArgumentException(UPPER_BOUND_MUST_BE_POSITIVE);
        }

        return (random.nextFloat() * (upperBound - lowerBound)) + lowerBound;
    }
}
