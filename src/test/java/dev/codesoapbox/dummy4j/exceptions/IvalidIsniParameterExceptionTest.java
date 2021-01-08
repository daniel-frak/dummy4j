package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IvalidIsniParameterExceptionTest {

    @Test
    void shouldGiveCorrectMessage() {
        IvalidIsniParameterException exception = new IvalidIsniParameterException("message");
        assertEquals("message", exception.getMessage());
    }
}