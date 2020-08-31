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
        assertEquals("aaa", dummy4j.lorem().characters(3));
    }

    @Test
    void character() {
        assertEquals("a", dummy4j.lorem().character());
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
        assertEquals("test", dummy4j.lorem().word());
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