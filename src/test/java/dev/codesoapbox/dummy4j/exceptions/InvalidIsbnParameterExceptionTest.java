package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidIsbnParameterExceptionTest {

    @Test
    void shouldGetMessageWithMissingLocaleInformation() {
        InvalidIsbnParameterException exception = new InvalidIsbnParameterException("message");

        assertEquals("message", exception.getMessage());
    }
}