package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NationDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void country() {
        assertEquals("Armenia", dummy4j.nation().country());
    }

    @Test
    void countryCode() {
        assertEquals("AD", dummy4j.nation().countryCode());
    }

    @Test
    void nationality() {
        assertEquals("Belarusian", dummy4j.nation().nationality());
    }

    @Test
    void languageCodeTwoLetter() {
        assertEquals("ch", dummy4j.nation().languageCodeTwoLetter());
    }

    @Test
    void languageCodeThreeLetter() {
        assertEquals("alb", dummy4j.nation().languageCodeThreeLetter());
    }

    @Test
    void language() {
        assertEquals("Haitian", dummy4j.nation().language());
    }

    @Test
    void languageCodeTwoLetterCommon() {
        assertEquals("ja", dummy4j.nation().languageCodeTwoLetterCommon());
    }

    @Test
    void languageCodeThreeLetterCommon() {
        assertEquals("spa", dummy4j.nation().languageCodeThreeLetterCommon());
    }

    @Test
    void languageCommon() {
        assertEquals("Portuguese", dummy4j.nation().languageCommon());
    }
}