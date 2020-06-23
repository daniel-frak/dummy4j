package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoremDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void characters() {
        assertNotNull(dummy4j.lorem().characters(10));
        assertFalse(dummy4j.lorem().characters(10).isEmpty());
        assertEquals(10, dummy4j.lorem().characters(10).length());
    }

    @Test
    void character() {
        assertNotNull(dummy4j.lorem().character());
        assertFalse(dummy4j.lorem().character().isEmpty());
    }

    @Test
    void sentence() {
        assertNotNull(dummy4j.lorem().sentence());
        assertFalse(dummy4j.lorem().sentence().isEmpty());
    }

    @Test
    void sentenceWithNumberOfWords() {
        assertNotNull(dummy4j.lorem().sentence(10));
        assertFalse(dummy4j.lorem().sentence(10).isEmpty());
    }

    @Test
    void sentenceWithMinAndMaxWords() {
        assertNotNull(dummy4j.lorem().sentence(5, 10));
        assertFalse(dummy4j.lorem().sentence(5, 10).isEmpty());
    }

    @Test
    void word() {
        assertNotNull(dummy4j.lorem().word());
        assertFalse(dummy4j.lorem().word().isEmpty());
    }

    @Test
    void paragraph() {
        assertNotNull(dummy4j.lorem().paragraph());
        assertFalse(dummy4j.lorem().paragraph().isEmpty());
    }

    @Test
    void paragraphWithNumberOfSentences() {
        assertNotNull(dummy4j.lorem().paragraph(10));
        assertFalse(dummy4j.lorem().paragraph(10).isEmpty());
    }
}