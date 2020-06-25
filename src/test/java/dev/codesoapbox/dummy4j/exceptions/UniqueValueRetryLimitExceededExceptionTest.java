package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniqueValueRetryLimitExceededExceptionTest {

    @Test
    void shouldGetMessage() {
        String expectedMessage = "Reached the maximum limit of retries (5) for generating a unique value in the " +
                "'someGroup' uniqueness group";

        assertEquals(expectedMessage, new UniqueValueRetryLimitExceededException(5, "someGroup").getMessage());
    }
}