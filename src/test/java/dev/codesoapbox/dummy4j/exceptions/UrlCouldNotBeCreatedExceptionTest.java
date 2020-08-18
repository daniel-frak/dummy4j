package dev.codesoapbox.dummy4j.exceptions;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class UrlCouldNotBeCreatedExceptionTest {

    @Test
    void shouldGiveCorrectMessage() {
        MalformedURLException ex = new MalformedURLException("Invalid port: -2");
        UrlCouldNotBeCreatedException exception = new UrlCouldNotBeCreatedException(ex);
        String expected = "Url could not be created due to the following error: [ Invalid port: -2 ]";
        assertEquals(expected, exception.getMessage());
    }

}