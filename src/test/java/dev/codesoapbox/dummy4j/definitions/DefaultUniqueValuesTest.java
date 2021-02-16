package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.exceptions.UniqueValueRetryLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class DefaultUniqueValuesTest {

    private DefaultUniqueValues uniqueValues;

    @BeforeEach
    void setUp() {
        uniqueValues = new DefaultUniqueValues();
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
    void valueShouldBeThreadSafe() throws InterruptedException {
        uniqueValues.setMaxRetries(1);
        int numOfThreads = 50;
        int numOfAttempts = 10;

        for (int attempt = 0; attempt < numOfAttempts; attempt++) {
            uniqueValues = new DefaultUniqueValues();
            AtomicInteger successes = new AtomicInteger();
            AtomicInteger fails = new AtomicInteger();
            final CountDownLatch latch = new CountDownLatch(1);

            ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
            for (int i = 0; i < numOfThreads; ++i) {
                Runnable runner = () -> {
                    try {
                        latch.await();
                        uniqueValues.value("group", () -> "value");
                        successes.incrementAndGet();
                    } catch (InterruptedException | UniqueValueRetryLimitExceededException e) {
                        fails.incrementAndGet();
                    }
                };
                executor.submit(runner);
            }

            latch.countDown();
            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                fail("Executor timed out");
            }

            assertEquals(1, successes.get());
            assertEquals(numOfThreads - 1, fails.get());
        }
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

        assertThrows(UniqueValueRetryLimitExceededException.class,
                () -> uniqueValues.within(() -> "value", value -> {
                    value.get();
                    value.get();
                }));
    }

    @Test
    void ofShouldProvideUniqueValues() {
        List<String> allValues = Arrays.asList("a", "a", "b");
        List<String> expectedResult = Arrays.asList("a", "b");
        Iterator<String> iterator = allValues.iterator();

        List<String> result = uniqueValues.of(iterator::next, value -> {
            List<String> list = new ArrayList<>();
            list.add(value.get());
            list.add(value.get());
            return list;
        });

        assertEquals(expectedResult, result);
    }

    @Test
    void ofShouldThrowExceptionAfterMaxRetriesReached() {
        int maxRetries = 1;
        uniqueValues.setMaxRetries(maxRetries);

        assertThrows(UniqueValueRetryLimitExceededException.class,
                () -> uniqueValues.of(() -> "value", value -> {
                    value.get();
                    value.get();
                    return null;
                }));
    }
}