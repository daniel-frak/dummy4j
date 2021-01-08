package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidDefinitionExceptionTest {

    @Test
    void shouldGetMessage() {
        InvalidDefinitionException exception = new InvalidDefinitionException("path", new IOException("reason"),
                "value");
        String expected = "Definitions for path: path are invalid due to the following reason: reason. " +
                "Resolved value: value";
        assertEquals(expected, exception.getMessage());
    }
}