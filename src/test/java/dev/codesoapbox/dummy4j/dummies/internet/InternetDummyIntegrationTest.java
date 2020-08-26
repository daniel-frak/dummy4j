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
    void shouldReturnDefaultUrl() {
        URL value = dummy4j.internet().url();

        assertAll(
                () -> assertNotNull(value, "Value is null"),
                () -> assertFalse(value.toString().isEmpty(), "Value is empty"),
                () -> assertEquals("", value.getPath(), "Invalid path"),
                () -> assertNotNull(value.getProtocol(), "Protocol is null"),
                () -> assertEquals(UrlBuilder.DEFAULT_PORT, value.getPort(), "Invalid port"),
                () -> assertFalse(value.getHost().isEmpty(), "Host is empty"),
                () -> assertNotNull(value.getFile(), "File is null"),
                () -> assertEquals("", value.getFile(), "File is not empty"),
                () -> assertNull(value.getQuery(), "Query is not null")
        );
    }

    @Test
    void shouldBuildUrl() {
        URL value = dummy4j.internet().urlBuilder()
                .withFilePath()
                .withQueryParam()
                .withCountryTopLevelDomain()
                .withProtocol(UrlProtocol.HTTP)
                .build();

        assertAll(
                () -> assertEquals("/aaaaaaaaaa.html", value.getPath(), "Invalid path"),
                () -> assertEquals(UrlProtocol.HTTP.getValue(), value.getProtocol(), "Invalid protocol"),
                () -> assertEquals(UrlBuilder.DEFAULT_PORT, value.getPort(), "Invalid port"),
                () -> assertEquals("www.test-root-domain.eu", value.getHost(), "Invalid host"),
                () -> assertNotNull(value.getFile(), "File is null"),
                () -> assertFalse(value.getFile().isEmpty(), "File is empty"),
                () -> assertNotNull(value.getQuery(), "Query is null"),
                () -> assertFalse(value.getQuery().isEmpty(), "Query is empty")
        );
    }

    @Test
    void shouldReturnDefaultPassword() {
        String value = dummy4j.internet().password();

        assertAll(
                () -> assertNotNull(value, "Value is null"),
                () -> assertFalse(value.isEmpty(), "Value is empty"),
                () -> assertEquals(DEFAULT_PASSWORD_LENGTH, value.length(), "Invalid length"),
                () -> assertEquals("aaaaaaaaaaaa", value, "Invalid value")
        );
    }

    @Test
    void shouldBuildPasswordWithSpecialAndUpperCaseChars() {
        String value = dummy4j.internet().passwordBuilder()
                .withSpecialChars()
                .withUpperCaseChars()
                .build();

        assertAll(
                () -> assertNotNull(value, "Value is null"),
                () -> assertFalse(value.isEmpty(), "Value is empty"),
                () -> assertEquals(DEFAULT_PASSWORD_LENGTH, value.length(), "Invalid length"),
                () -> assertTrue(value.matches(".*[!@#$%^&*_\\-?]+.*"), "Special characters are missing"),
                () -> assertTrue(value.matches(".*[A-Z]+.*"), "Upper case characters are missing"),
                () -> assertTrue(value.matches(".*[a-z]+.*"), "Lower case characters are missing"),
                () -> assertEquals("$Aa$Aa$Aa$Aa", value, "Invalid value")
        );
    }

    @Test
    void shouldBuildPasswordWithDigits() {
        String value = dummy4j.internet().passwordBuilder()
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