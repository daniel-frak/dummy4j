package dev.codesoapbox.dummy4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomServiceTest {

    private RandomService randomService;

    @BeforeEach
    void setUp() {
        randomService = new RandomService(1L);
    }

    @Test
    void shouldGetSeed() {
        assertEquals(1L, randomService.getSeed());
    }

    @Test
    void shouldGetRandomBoolean() {
        int trues = 0;
        for(int i = 0; i < 100; i++) {
            if(randomService.nextBoolean()) {
                trues++;
            }
        }
        assertTrue(trues > 40);
    }

    @Test
    void shouldGetRandomInt() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextInt() > 0);
        }
    }

    @Test
    void shouldGetRandomIntWithUpperBound() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextInt(10) < 10);
        }
    }

    @Test
    void shouldGetRandomIntWithLowerAndUpperBound() {
        for(int i = 0; i < 100; i++) {
            int result = randomService.nextInt(10, 15);
            assertTrue(result >= 10);
            assertTrue(result < 15);
        }
    }

    @Test
    void shouldGetRandomLong() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextLong() > 0);
        }
    }

    @Test
    void shouldGetRandomLongWithUpperBound() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextLong(10) < 10);
        }
    }

    @Test
    void shouldGetRandomLongWithLowerAndUpperBound() {
        for(int i = 0; i < 100; i++) {
            long result = randomService.nextLong(10, 15);
            assertTrue(result >= 10);
            assertTrue(result < 15);
        }
    }

    @Test
    void shouldGetRandomDouble() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextDouble() > 0);
        }
    }

    @Test
    void shouldGetRandomDoubleWithUpperBound() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextDouble(10) < 10);
        }
    }

    @Test
    void shouldGetRandomDoubleWithLowerAndUpperBound() {
        for(int i = 0; i < 100; i++) {
            double result = randomService.nextLong(10, 15);
            assertTrue(result >= 10);
            assertTrue(result < 15);
        }
    }

    @Test
    void shouldGetRandomFloat() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextFloat() > 0);
        }
    }

    @Test
    void shouldGetRandomFloatWithUpperBound() {
        for(int i = 0; i < 100; i++) {
            assertTrue(randomService.nextFloat(10) < 10);
        }
    }

    @Test
    void shouldGetRandomFloatWithLowerAndUpperBound() {
        for(int i = 0; i < 100; i++) {
            float result = randomService.nextFloat(10, 15);
            assertTrue(result >= 10);
            assertTrue(result < 15);
        }
    }
}