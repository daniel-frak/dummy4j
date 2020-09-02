package dev.codesoapbox.dummy4j;

/**
 * @since 0.5.0
 */
public interface NumberService {

    long nextLong();

    int nextInt();

    int nextInt(int upperBound);

    int nextInt(int lowerBound, int upperBound);

    long nextLong(long upperBound);

    long nextLong(long lowerBound, long upperBound);

    double nextDouble();

    double nextDouble(double bound);

    double nextDouble(double lowerBound, double upperBound);

    float nextFloat();

    float nextFloat(float upperBound);

    float nextFloat(float lowerBound, float upperBound);
}
