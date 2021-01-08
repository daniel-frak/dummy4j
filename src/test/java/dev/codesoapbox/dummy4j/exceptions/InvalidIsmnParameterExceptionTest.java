package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidIsmnParameterExceptionTest {

    @Test
    void shouldGetMessageWithMissingLocaleInformation() {
        InvalidIsmnParameterException exception = new InvalidIsmnParameterException("message");

        assertEquals("message", exception.getMessage());
    }
}