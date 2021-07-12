package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidRangeExceptionTest {

    @Test
    void shouldGiveCorrectMessage() {
        InvalidRangeException exception = new InvalidRangeException(2F, 1F);
        assertEquals("Given range from 2.0 to 1.0 is invalid", exception.getMessage());
    }
}