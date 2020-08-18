package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class InternetDummyIntegrationTest {

    private static final int DEFAULT_PASSWORD_LENGTH = 12;

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void url() {
        URL value = dummy4j.internet().url()
                .withFilePath()
                .withQueryParam()
                .withCountryTopLevelDomain()
                .withRandomProtocol(UrlProtocol.FTP, UrlProtocol.HTTP)
                .build();
        assertAll(
                () -> assertNotNull(value),
                () -> assertNotNull(value.getPath()),
                () -> assertFalse(value.getPath().isEmpty()),
                () -> assertNotNull(value.getProtocol()),
                () -> assertFalse(value.getProtocol().isEmpty()),
                () -> assertEquals(UrlBuilder.DEFAULT_PORT, value.getPort()),
                () -> assertNotNull(value.getHost()),
                () -> assertFalse(value.getHost().isEmpty()),
                () -> assertNotNull(value.getFile()),
                () -> assertFalse(value.getFile().isEmpty()),
                () -> assertNotNull(value.getQuery()),
                () -> assertFalse(value.getQuery().isEmpty())
        );
    }

    @Test
    void password() {
        String value = dummy4j.internet().password()
                .withDigits()
                .withSpecialChars()
                .withUpperCaseChars()
                .build();

        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty()),
                () -> assertEquals(DEFAULT_PASSWORD_LENGTH, value.length(), "Invalid length"),
                () -> assertTrue(value.matches(".*\\d+.*"), "Digits are missing"),
                () -> assertTrue(value.matches(".*[!@#$%^&*_\\-?]+.*"), "Special characters are missing"),
                () -> assertTrue(value.matches(".*[A-Z]+.*"), "Upper case characters are missing"),
                () -> assertTrue(value.matches(".*[a-z]+.*"), "Lower case characters are missing")
        );
    }
}