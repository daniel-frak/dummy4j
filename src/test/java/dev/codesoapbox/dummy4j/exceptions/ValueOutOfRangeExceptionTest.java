package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueOutOfRangeExceptionTest {

    @Test
    void shouldGiveCorrectMessage() {
        ValueOutOfRangeException exception = new ValueOutOfRangeException(1F, 2F, 3F);
        assertEquals("Given value (1.0) is out of range (2.0-3.0).", exception.getMessage());
    }
}