package dev.codesoapbox.dummy4j;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomService {

    private final long seed;
    private final Random random;

    public RandomService(Long seed) {
        this.seed = seed != null ? seed : ThreadLocalRandom.current().nextInt();

        this.random = new Random(this.seed);
    }

    public long getSeed() {
        return seed;
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
