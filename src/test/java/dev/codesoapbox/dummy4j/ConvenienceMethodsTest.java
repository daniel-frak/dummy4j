package dev.codesoapbox.dummy4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

        when(random.nextInt(2))
                .thenReturn(2);

        assertEquals("three", convenienceMethods.of(array));
    }

    @Test
    void shouldReturnRandomElementFromList() {
        List<String> list = asList("one", "two", "three");

        when(random.nextInt(2))
                .thenReturn(2);

        assertEquals("three", convenienceMethods.of(list));
    }

    @Test
    void shouldReturnRandomElementFromSet() {
        Set<String> set = new HashSet<>(asList("one", "two", "three"));

        when(random.nextInt(2))
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

    @ParameterizedTest
    @CsvSource({
            "1,true",
            "9,true",
            "10,true",
            "11,",
            "20,"
    })
    void shouldSupplyRandomlyByChance(int randomInt, Boolean expected) {
        int howMany = 10;
        int in = 20;
        when(random.nextInt(1, in))
                .thenReturn(randomInt);

        assertEquals(expected, convenienceMethods.chance(howMany, in, () -> true));
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",
            "9,true",
            "10,true",
            "11,false",
            "20,false"
    })
    void shouldReturnBooleanRandomlyByChance(int randomInt, boolean expected) {
        int howMany = 10;
        int in = 20;
        when(random.nextInt(1, in))
                .thenReturn(randomInt);

        assertEquals(expected, convenienceMethods.chance(howMany, in));
    }

    @ParameterizedTest
    @CsvSource({
            "0,ONE",
            "1,TWO",
            "2,THREE"
    })
    void shouldGetRandomEnumValue(int index, String expected) {
        when(random.nextInt(2))
                .thenReturn(index);
        assertEquals(TestEnum.valueOf(expected), convenienceMethods.nextEnum(TestEnum.class));
    }

    public enum TestEnum {
        ONE, TWO, THREE
    }
}