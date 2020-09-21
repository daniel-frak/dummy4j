package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MissingLocaleDefinitionsExceptionTest {

    @Test
    void shouldGetMessageWithMissingLocaleInformation() {
        MissingLocaleDefinitionsException exception = new MissingLocaleDefinitionsException("en");
        String expected = "Could not find definitions for locale: en. Make sure its definitions are included " +
                "in the provided paths.";
        assertEquals(expected, exception.getMessage());
    }
}