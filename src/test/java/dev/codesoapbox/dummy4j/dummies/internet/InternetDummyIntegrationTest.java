package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class InternetDummyIntegrationTest {

    private static final int DEFAULT_PASSWORD_LENGTH = 12;

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void shouldReturnUrl() {
        URL value = dummy4j.internet().url();

        assertAll(
                () -> assertEquals("www.test-root-domain.test-top-level-domain", value.getHost()),
                () -> assertEquals(UrlBuilder.DEFAULT_PORT, value.getPort(), "Invalid port"),
                () -> assertEquals("", value.getPath(), "Path is not empty"),
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
                .withProtocol(UrlProtocol.HTTPS)
                .build();

        assertAll(
                () -> assertEquals(UrlProtocol.HTTPS.getValue(), value.getProtocol(), "Invalid protocol"),
                () -> assertEquals("www.test-root-domain.eu", value.getHost(), "Invalid host"),
                () -> assertEquals(UrlBuilder.DEFAULT_PORT, value.getPort(), "Invalid port"),
                () -> assertNotNull(value.getQuery(), "Query is null"),
                () -> assertEquals("/aaaaaaaaaa.html", value.getPath(), "Invalid path"),
                () -> {
                    Pattern queryPattern = Pattern.compile(".*/aaaaaaaaaa\\.html\\?param=.*");
                    Matcher queryMatcher = queryPattern.matcher(value.getFile());
                    assertTrue(queryMatcher.matches(), "Invalid file");
                }
        );
    }

    @Test
    void shouldReturnPassword() {
        String value = dummy4j.internet().password();

        assertEquals("aaaaaaaaaaaa", value, "Invalid password");
    }

    @Test
    void shouldBuildPasswordWithSpecialAndUpperCaseChars() {
        String value = dummy4j.internet().passwordBuilder()
                .withSpecialChars()
                .withUpperCaseChars()
                .build();

        assertAll(
                () -> assertNotNull(value, "Password is null"),
                () -> assertEquals("$Aa$Aa$Aa$Aa", value, "Invalid password"),
                () -> assertEquals(DEFAULT_PASSWORD_LENGTH, value.length(), "Invalid length"),
                () -> assertTrue(value.matches(".*[!@#$%^&*_\\-?]+.*"), "Special characters are missing"),
                () -> assertTrue(value.matches(".*[A-Z]+.*"), "Upper case characters are missing"),
                () -> assertTrue(value.matches(".*[a-z]+.*"), "Lower case characters are missing")
        );
    }

    @Test
    void shouldBuildPasswordWithDigitsAndLength() {
        String value = dummy4j.internet().passwordBuilder()
                .withDigits()
                .withLength(10)
                .build();

        assertAll(
                () -> assertNotNull(value, "Password is null"),
                () -> assertEquals(10, value.length(), "Invalid length"),
                () -> assertTrue(value.matches(".*\\d+.*"), "Digits are missing")
        );
    }

    @Test
    void shouldReturnDefaultEmail() {
        String value = dummy4j.internet().email();

        assertEquals("zoe.anderson@gmail.com", value);
    }

    @Test
    void shouldBuildEmail() {
        String value = dummy4j.internet().emailBuilder()
                .withRandomSubAddress()
                .safe()
                .build();

        assertEquals("zoe.anderson+tag1@example.com", value);
    }
}