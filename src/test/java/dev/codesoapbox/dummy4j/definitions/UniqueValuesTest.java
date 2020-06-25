package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.exceptions.UniqueValueRetryLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UniqueValuesTest {

    private UniqueValues uniqueValues;

    @BeforeEach
    void setUp() {
        uniqueValues = new UniqueValues();
    }

    @Test
    void shouldThrowExceptionAfterMaxRetriesReached() {
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
    void shouldReturnValueAfterCollision() {
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
    void shouldReturnValue() {
        assertEquals("value", uniqueValues.value("group", () -> "value"));
    }
}