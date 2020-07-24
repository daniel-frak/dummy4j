package dev.codesoapbox.dummy4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConvenienceMethodsTest {

    private ConvenienceMethods convenienceMethods;

    @Mock
    private RandomService random;

    @BeforeEach
    void setUp() {
        convenienceMethods = new ConvenienceMethods(random);
    }

    @Test
    void shouldGenerateRandomList() {
        List<Integer> expectedResult = Arrays.asList(1, 2, 3, 4, 5);

        AtomicInteger i = new AtomicInteger();
        List<Integer> result = convenienceMethods.listOf(5, i::incrementAndGet);

        assertEquals(expectedResult, result);
    }

    @Test
    void shouldGenerateRandomSet() {
        Set<Integer> expectedResult = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            expectedResult.add(i + 1);
        }

        AtomicInteger i = new AtomicInteger();
        Set<Integer> result = convenienceMethods.setOf(5, i::incrementAndGet);

        assertEquals(expectedResult, result);
    }

    @Test
    void shouldReturnRandomElementFromArray() {
        String[] array = { "one", "two", "three" };

        when(random.nextInt(3))
                .thenReturn(2);

        assertEquals("three", convenienceMethods.of(array));
    }

    @Test
    void shouldReturnRandomElementFromList() {
        List<String> list = asList("one", "two", "three");

        when(random.nextInt(3))
                .thenReturn(2);

        assertEquals("three", convenienceMethods.of(list));
    }

    @Test
    void shouldReturnRandomElementFromSet() {
        Set<String> set = new HashSet<>(asList("one", "two", "three"));

        when(random.nextInt(3))
                .thenReturn(2);

        assertEquals("three", convenienceMethods.of(set));
    }

    @Test
    void shouldSupplyFromRandomSupplier() {
        when(random.nextInt(0, 2))
                .thenReturn(1);

        int result = convenienceMethods.of(() -> 1, () -> 2, () -> 3);

        assertEquals(2, result);
    }
}