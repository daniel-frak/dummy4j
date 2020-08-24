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
                .withProtocol(UrlProtocol.HTTP)
                .build();
        assertAll(
                () -> {
                    String expected = "http://www.test-root-domain.eu/aaaaaaaaaa.html?test=test";
                    assertEquals(expected, value.toString());
                },
                () -> assertEquals("/aaaaaaaaaa.html", value.getPath(), "Invalid path"),
                () -> assertEquals(UrlProtocol.HTTP.getValue(), value.getProtocol(), "Invalid protocol"),
                () -> assertEquals(UrlBuilder.DEFAULT_PORT, value.getPort(), "Invalid port"),
                () -> assertEquals("www.test-root-domain.eu", value.getHost(), "Invalid host"),
                () -> assertEquals("/aaaaaaaaaa.html?test=test", value.getFile(), "Invalid file"),
                () -> assertEquals("test=test", value.getQuery(), "Invalid query params")
        );
    }

    @Test
    void shouldCreatePasswordWithSpecialAndUpperCaseChars() {
        String value = dummy4j.internet().password()
                .withSpecialChars()
                .withUpperCaseChars()
                .build();

        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty()),
                () -> assertEquals(DEFAULT_PASSWORD_LENGTH, value.length(), "Invalid length"),
                () -> assertTrue(value.matches(".*[!@#$%^&*_\\-?]+.*"), "Special characters are missing"),
                () -> assertTrue(value.matches(".*[A-Z]+.*"), "Upper case characters are missing"),
                () -> assertTrue(value.matches(".*[a-z]+.*"), "Lower case characters are missing"),
                () -> assertEquals("$Aa$Aa$Aa$Aa", value, "Invalid value")
        );
    }

    @Test
    void shouldCreatePasswordWithDigits() {
        String value = dummy4j.internet().password()
                .withDigits()
                .build();

        assertAll(
                () -> assertNotNull(value),
                () -> assertFalse(value.isEmpty()),
                () -> assertEquals(DEFAULT_PASSWORD_LENGTH, value.length(), "Invalid length"),
                () -> assertTrue(value.matches(".*\\d+.*"), "Digits are missing")
        );
    }
}