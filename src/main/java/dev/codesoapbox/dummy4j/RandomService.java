package dev.codesoapbox.dummy4j;

/**
 * Provides random numbers which are generated based on a seed
 *
 * @since 0.5.0
 */
public interface RandomService extends NumberService {

    long getSeed();

    boolean nextBoolean();
}
