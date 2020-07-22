package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoremDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void characters() {
        String value = dummy4j.lorem().characters(10);
        assertNotNull(value);
        assertFalse(value.isEmpty());
        assertEquals(10, value.length());
    }

    @Test
    void character() {
        String value = dummy4j.lorem().character();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void sentence() {
        String value = dummy4j.lorem().sentence();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void sentenceWithNumberOfWords() {
        String value = dummy4j.lorem().sentence(10);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void sentenceWithMinAndMaxWords() {
        String value = dummy4j.lorem().sentence(5, 10);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void word() {
        String value = dummy4j.lorem().word();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void paragraph() {
        String value = dummy4j.lorem().paragraph();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void paragraphWithNumberOfSentences() {
        String value = dummy4j.lorem().paragraph(10);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}