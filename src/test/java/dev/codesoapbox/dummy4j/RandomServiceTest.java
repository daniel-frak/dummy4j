package dev.codesoapbox.dummy4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomServiceTest {

    private RandomService randomService;

    @Mock
    private Random random;

    @BeforeEach
    void setUp() {
        randomService = new RandomService(random, 1L);
    }

    @Test
    void shouldGetSeed() {
        assertEquals(1L, randomService.getSeed());
    }

    @Test
    void shouldGetRandomUuid() {
        when(random.nextLong())
                .thenReturn(1L);
        String expected = "c4ca4238-a0b9-3382-8dcc-509a6f75849b";
        assertEquals(expected, randomService.uuid());
    }

    @Test
    void shouldGetRandomBoolean() {
        when(random.nextBoolean())
                .thenReturn(false);
        assertFalse(randomService.nextBoolean());
        when(random.nextBoolean())
                .thenReturn(true);
        assertTrue(randomService.nextBoolean());
    }

    @Test
    void shouldGetRandomInt() {
        when(random.nextInt())
                .thenReturn(2);
        assertEquals(2, randomService.nextInt());
    }

    @Test
    void shouldConvertNegativeRandomIntToOtherPositiveNumber() {
        when(random.nextInt())
                .thenReturn(-5);
        assertTrue(randomService.nextInt() >= 0);
    }

    @Test
    void shouldGetRandomIntWhenUpperBoundIsIntegerMax() {
        when(random.nextInt(Integer.MAX_VALUE))
                .thenReturn(2147483646);
        assertEquals(Integer.MAX_VALUE - 1, randomService.nextInt(Integer.MAX_VALUE));
    }

    @Test
    void shouldGetRandomIntWithInclusiveUpperBound() {
        when(random.nextInt(11))
                .thenReturn(10);
        assertEquals(10, randomService.nextInt(10));
    }

    @Test
    void shouldGetRandomIntWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextInt(6))
                .thenReturn(0);
        assertEquals(10, randomService.nextInt(10, 15));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomInteger() {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextInt(-1));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomInteger(int lowerBound, int upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextInt(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomInteger() {
        when(random.nextInt(1))
                .thenReturn(0);
        assertEquals(0, randomService.nextInt(0));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomInteger() {
        when(random.nextInt(1))
                .thenReturn(0);
        assertAll(
                () -> assertEquals(0, randomService.nextInt(0, 0)),
                () -> assertEquals(10, randomService.nextInt(10, 10)),
                () -> assertEquals(Integer.MAX_VALUE, randomService.nextInt(Integer.MAX_VALUE, Integer.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomLong() {
        when(random.nextLong())
                .thenReturn(2L);
        assertEquals(2L, randomService.nextLong());
    }

    @Test
    void shouldConvertNegativeRandomLongToOtherPositiveNumber() {
        when(random.nextLong())
                .thenReturn(-5L);
        assertTrue(randomService.nextLong() >= 0);
    }

    @Test
    void shouldGetRandomLongWhenUpperBoundIsLongMax() {
        when(random.nextDouble())
                .thenReturn(0.9999999999999999D);
        assertEquals(9223372036854774784L, randomService.nextLong(Long.MAX_VALUE));
    }

    @Test
    void shouldGetRandomLongWithInclusiveUpperBound() {
        when(random.nextDouble())
                .thenReturn(0.9999999999999999D);
        assertEquals(17L, randomService.nextLong(17L));
    }

    @Test
    void shouldGetRandomLongWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextDouble())
                .thenReturn(0.01D);
        assertEquals(1, randomService.nextLong(1L, 10L));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomLong() {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextLong(-1L));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomLong(long lowerBound, long upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextLong(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomLong() {
        when(random.nextDouble())
                .thenReturn(0.9D);
        assertEquals(0, randomService.nextLong(0));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomLong() {
        when(random.nextDouble())
                .thenReturn(0.9D);
        assertAll(
                () -> assertEquals(0L, randomService.nextLong(0L, 0L)),
                () -> assertEquals(10L, randomService.nextLong(10L, 10L)),
                () -> assertEquals(Long.MAX_VALUE, randomService.nextLong(Long.MAX_VALUE, Long.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomDouble() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertEquals(0.2D, randomService.nextDouble());
    }

    @Test
    void shouldGetRandomDoubleWithUpperBound() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertAll(
                () -> assertEquals(0.8D, randomService.nextDouble(4D)),
                () -> assertEquals(Double.MAX_VALUE * 0.2D, randomService.nextDouble(Double.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomDoubleWithLowerAndUpperBound() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertEquals(2.8D, randomService.nextDouble(1D, 10D));
    }

    @Test
    void shouldGetRandomDoubleWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextDouble())
                .thenReturn(0D);
        assertEquals(4, randomService.nextDouble(4, 5));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomDouble() {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextDouble(-1D));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomDouble(double lowerBound, double upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextDouble(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomDouble() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertEquals(0, randomService.nextDouble(0));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomDouble() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertAll(
                () -> assertEquals(0, randomService.nextDouble(0, 0)),
                () -> assertEquals(10D, randomService.nextDouble(10D, 10D)),
                () -> assertEquals(Double.MAX_VALUE, randomService.nextDouble(Double.MAX_VALUE, Double.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomFloat() {
        when(random.nextFloat())
                .thenReturn(0.2F);
        assertEquals(0.2F, randomService.nextFloat());
    }

    @Test
    void shouldGetRandomFloatWithUpperBound() {
        when(random.nextFloat())
                .thenReturn(0.8F);
        assertEquals(8F, randomService.nextFloat(10F));
        assertEquals(Float.MAX_VALUE * 0.8F, randomService.nextFloat(Float.MAX_VALUE));
    }

    @Test
    void shouldGetRandomFloatWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextFloat())
                .thenReturn(0F);
        assertEquals(4F, randomService.nextFloat(4F, 5F));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomFloat() {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextFloat(-1F));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomFloat(float lowerBound, float upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> randomService.nextFloat(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomFloat() {
        when(random.nextFloat())
                .thenReturn(0.3F);
        assertEquals(0F, randomService.nextFloat(0F));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomFloat() {
        when(random.nextFloat())
                .thenReturn(0.3F);
        assertAll(
                () -> assertEquals(0F, randomService.nextFloat(0F, 0F)),
                () -> assertEquals(10F, randomService.nextFloat(10F, 10F)),
                () -> assertEquals(Float.MAX_VALUE, randomService.nextFloat(Float.MAX_VALUE, Float.MAX_VALUE))
        );
    }
}