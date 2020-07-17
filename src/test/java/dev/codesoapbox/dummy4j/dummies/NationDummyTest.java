package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NationDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void country() {
        String value = dummy4j.nation().country();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void countryCode() {
        String value = dummy4j.nation().countryCode();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void nationality() {
        String value = dummy4j.nation().nationality();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void languageCodeTwoLetter() {
        String value = dummy4j.nation().languageCodeTwoLetter();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void languageCodeThreeLetter() {
        String value = dummy4j.nation().languageCodeThreeLetter();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void language() {
        String value = dummy4j.nation().language();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void languageCodeTwoLetterCommon() {
        String value = dummy4j.nation().languageCodeTwoLetterCommon();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void languageCodeThreeLetterCommon() {
        String value = dummy4j.nation().languageCodeThreeLetterCommon();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void languageCommon() {
        String value = dummy4j.nation().languageCommon();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}