package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * @since 0.4.0
 */
public class DateAndTimeDummy {

    private static final int DEFAULT_MIN_AGE = 18;
    private static final int DEFAULT_MAX_AGE = 80;

    private final Dummy4j dummy4j;
    private final Clock clock;

    public DateAndTimeDummy(Dummy4j dummy4j, Clock clock) {
        this.dummy4j = dummy4j;
        this.clock = clock;
    }

    public LocalDateTime any() {
        return LocalDateTime.ofEpochSecond(dummy4j.random().nextLong(), 0, ZoneOffset.UTC);
    }

    public LocalDate birthday() {
        return birthday(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE);
    }

    public LocalDate birthday(int age) {
        return birthday(age, age);
    }

    public LocalDate birthday(int minAge, int maxAge) {
        LocalDate minDate = LocalDate.now(clock).minus((long) maxAge + 1, ChronoUnit.YEARS)
                .plus(1, ChronoUnit.DAYS);
        LocalDate maxDate = LocalDate.now(clock).minus(minAge, ChronoUnit.YEARS);
        return between(minDate, maxDate);
    }

    public LocalDateTime between(LocalDateTime start, LocalDateTime end) {
        long startEpoch = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(ZoneOffset.UTC);
        long randomEpochSecond = nextLongWithNegativeBounds(startEpoch, endEpoch);
        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
    }

    public LocalDate between(LocalDate start, LocalDate end) {
        long randomDay = nextLongWithNegativeBounds(start.toEpochDay(), end.toEpochDay());

        return LocalDate.ofEpochDay(randomDay);
    }

    private long nextLongWithNegativeBounds(long lowerBound, long upperBound) {
        long diff = Math.abs(lowerBound - upperBound);
        return dummy4j.random().nextLong(0, diff) + lowerBound;
    }

    public LocalDateTime past(long atMost, ChronoUnit unit) {
        return LocalDateTime.now(clock).minus(dummy4j.random().nextLong(0, atMost), unit);
    }

    public LocalDateTime past(long atMost, ChronoUnit unit, LocalDateTime referenceDate) {
        return referenceDate.minus(dummy4j.random().nextLong(0, atMost), unit);
    }

    public LocalDateTime future(long atMost, ChronoUnit unit) {
        return LocalDateTime.now(clock).plus(dummy4j.random().nextLong(0, atMost), unit);
    }

    public LocalDateTime future(long atMost, ChronoUnit unit, LocalDateTime referenceDate) {
        return referenceDate.plus(dummy4j.random().nextLong(0, atMost), unit);
    }
}
