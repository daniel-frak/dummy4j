package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissingLocaleExceptionTest {

    @Test
    void shouldGetMessageWithMissingLocaleInformation() {
        MissingLocaleException exception = new MissingLocaleException("en");
        assertEquals("Missing locale: en", exception.getMessage());
    }
}