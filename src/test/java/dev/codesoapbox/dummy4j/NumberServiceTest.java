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
class NumberServiceTest {

    private NumberService numberService;

    @Mock
    private Random random;

    @BeforeEach
    void setUp() {
        numberService = new NumberService(random, 1L);
    }

    @Test
    void shouldGetSeed() {
        assertEquals(1L, numberService.getSeed());
    }

    @Test
    void shouldGetRandomBoolean() {
        when(random.nextBoolean())
                .thenReturn(false);
        assertFalse(numberService.nextBoolean());
        when(random.nextBoolean())
                .thenReturn(true);
        assertTrue(numberService.nextBoolean());
    }

    @Test
    void shouldGetRandomInt() {
        when(random.nextInt())
                .thenReturn(2);
        assertEquals(2, numberService.nextInt());
    }

    @Test
    void shouldConvertNegativeRandomIntToOtherPositiveNumber() {
        when(random.nextInt())
                .thenReturn(-5);
        assertTrue(numberService.nextInt() >= 0);
    }

    @Test
    void shouldGetRandomIntWhenUpperBoundIsIntegerMax() {
        when(random.nextInt(Integer.MAX_VALUE))
                .thenReturn(2147483646);
        assertEquals(Integer.MAX_VALUE - 1, numberService.nextInt(Integer.MAX_VALUE));
    }

    @Test
    void shouldGetRandomIntWithInclusiveUpperBound() {
        when(random.nextInt(11))
                .thenReturn(10);
        assertEquals(10, numberService.nextInt(10));
    }

    @Test
    void shouldGetRandomIntWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextInt(6))
                .thenReturn(0);
        assertEquals(10, numberService.nextInt(10, 15));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomInteger() {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextInt(-1));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomInteger(int lowerBound, int upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextInt(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomInteger() {
        when(random.nextInt(1))
                .thenReturn(0);
        assertEquals(0, numberService.nextInt(0));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomInteger() {
        when(random.nextInt(1))
                .thenReturn(0);
        assertAll(
                () -> assertEquals(0, numberService.nextInt(0, 0)),
                () -> assertEquals(10, numberService.nextInt(10, 10)),
                () -> assertEquals(Integer.MAX_VALUE, numberService.nextInt(Integer.MAX_VALUE, Integer.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomLong() {
        when(random.nextLong())
                .thenReturn(2L);
        assertEquals(2L, numberService.nextLong());
    }

    @Test
    void shouldConvertNegativeRandomLongToOtherPositiveNumber() {
        when(random.nextLong())
                .thenReturn(-5L);
        assertTrue(numberService.nextLong() >= 0);
    }

    @Test
    void shouldGetRandomLongWhenUpperBoundIsLongMax() {
        when(random.nextDouble())
                .thenReturn(0.9999999999999999D);
        assertEquals(9223372036854774784L, numberService.nextLong(Long.MAX_VALUE));
    }

    @Test
    void shouldGetRandomLongWithInclusiveUpperBound() {
        when(random.nextDouble())
                .thenReturn(0.9999999999999999D);
        assertEquals(17L, numberService.nextLong(17L));
    }

    @Test
    void shouldGetRandomLongWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextDouble())
                .thenReturn(0.01D);
        assertEquals(1, numberService.nextLong(1L, 10L));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomLong() {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextLong(-1L));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomLong(long lowerBound, long upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextLong(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomLong() {
        when(random.nextDouble())
                .thenReturn(0.9D);
        assertEquals(0, numberService.nextLong(0));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomLong() {
        when(random.nextDouble())
                .thenReturn(0.9D);
        assertAll(
                () -> assertEquals(0L, numberService.nextLong(0L, 0L)),
                () -> assertEquals(10L, numberService.nextLong(10L, 10L)),
                () -> assertEquals(Long.MAX_VALUE, numberService.nextLong(Long.MAX_VALUE, Long.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomDouble() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertEquals(0.2D, numberService.nextDouble());
    }

    @Test
    void shouldGetRandomDoubleWithUpperBound() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertAll(
                () -> assertEquals(0.8D, numberService.nextDouble(4D)),
                () -> assertEquals(Double.MAX_VALUE * 0.2D, numberService.nextDouble(Double.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomDoubleWithLowerAndUpperBound() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertEquals(2.8D, numberService.nextDouble(1D, 10D));
    }

    @Test
    void shouldGetRandomDoubleWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextDouble())
                .thenReturn(0D);
        assertEquals(4, numberService.nextDouble(4, 5));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomDouble() {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextDouble(-1D));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomDouble(double lowerBound, double upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextDouble(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomDouble() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertEquals(0, numberService.nextDouble(0));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomDouble() {
        when(random.nextDouble())
                .thenReturn(0.2D);
        assertAll(
                () -> assertEquals(0, numberService.nextDouble(0, 0)),
                () -> assertEquals(10D, numberService.nextDouble(10D, 10D)),
                () -> assertEquals(Double.MAX_VALUE, numberService.nextDouble(Double.MAX_VALUE, Double.MAX_VALUE))
        );
    }

    @Test
    void shouldGetRandomFloat() {
        when(random.nextFloat())
                .thenReturn(0.2F);
        assertEquals(0.2F, numberService.nextFloat());
    }

    @Test
    void shouldGetRandomFloatWithUpperBound() {
        when(random.nextFloat())
                .thenReturn(0.8F);
        assertEquals(8F, numberService.nextFloat(10F));
        assertEquals(Float.MAX_VALUE * 0.8F, numberService.nextFloat(Float.MAX_VALUE));
    }

    @Test
    void shouldGetRandomFloatWithLowerAndUpperBoundInclusiveLower() {
        when(random.nextFloat())
                .thenReturn(0F);
        assertEquals(4F, numberService.nextFloat(4F, 5F));
    }

    @Test
    void shouldThrowExceptionOnNegativeUpperBoundForRandomFloat() {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextFloat(-1F));
    }

    @ParameterizedTest
    @CsvSource({
            "-15, 5",
            "15, -5",
            "15, 5"
    })
    void shouldThrowExceptionOnInvalidBoundsForRandomFloat(float lowerBound, float upperBound) {
        assertThrows(IllegalArgumentException.class,
                () -> numberService.nextFloat(lowerBound, upperBound));
    }

    @Test
    void shouldAcceptZeroBoundForRandomFloat() {
        when(random.nextFloat())
                .thenReturn(0.3F);
        assertEquals(0F, numberService.nextFloat(0F));
    }

    @Test
    void shouldAcceptEqualBoundsForRandomFloat() {
        when(random.nextFloat())
                .thenReturn(0.3F);
        assertAll(
                () -> assertEquals(0F, numberService.nextFloat(0F, 0F)),
                () -> assertEquals(10F, numberService.nextFloat(10F, 10F)),
                () -> assertEquals(Float.MAX_VALUE, numberService.nextFloat(Float.MAX_VALUE, Float.MAX_VALUE))
        );
    }
}