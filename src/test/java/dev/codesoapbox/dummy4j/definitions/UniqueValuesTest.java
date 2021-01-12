package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.exceptions.UniqueValueRetryLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UniqueValuesTest {

    private UniqueValues uniqueValues;

    @BeforeEach
    void setUp() {
        uniqueValues = new UniqueValues();
    }

    @Test
    void valueShouldThrowExceptionAfterMaxRetriesReached() {
        Deque<String> stack = new ArrayDeque<>();
        stack.add("abc");
        stack.add("abc");
        stack.add("abc");
        stack.add("def");

        int maxRetries = 1;
        uniqueValues.setMaxRetries(maxRetries);

        assertThrows(UniqueValueRetryLimitExceededException.class, () -> {
            for (int i = 0; i < 2; i++) {
                uniqueValues.value("group", stack::pop);
            }
        });
    }

    @Test
    void valueShouldReturnValueAfterCollision() {
        Deque<String> stack = new ArrayDeque<>();
        stack.add("abc");
        stack.add("abc");
        stack.add("def");

        int maxRetries = 1;
        uniqueValues.setMaxRetries(maxRetries);

        String lastValue = "";
        for (int i = 0; i < 2; i++) {
            lastValue = uniqueValues.value("group", stack::pop);
        }
        assertEquals("def", lastValue);
    }

    @Test
    void valueShouldReturnValue() {
        assertEquals("value", uniqueValues.value("group", () -> "value"));
    }

    @Test
    void withinShouldProvideUniqueValues() {
        List<String> allValues = Arrays.asList("a", "a", "b");
        List<String> expectedResult = Arrays.asList("a", "b");
        Iterator<String> iterator = allValues.iterator();

        List<String> result = new ArrayList<>();
        uniqueValues.within(iterator::next, value -> {
            result.add(value.get());
            result.add(value.get());
        });

        assertEquals(expectedResult, result);
    }

    @Test
    void withinShouldThrowExceptionAfterMaxRetriesReached() {
        int maxRetries = 1;
        uniqueValues.setMaxRetries(maxRetries);

        assertThrows(UniqueValueRetryLimitExceededException.class, () -> {
            uniqueValues.within(() -> "value", value -> {
                value.get();
                value.get();
            });
        });
    }
}