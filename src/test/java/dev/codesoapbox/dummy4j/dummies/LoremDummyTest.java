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
    }

    @Test
    void character() {
        assertNotNull(dummy4j.lorem().character());
    }

    @Test
    void sentence() {
        assertNotNull(dummy4j.lorem().sentence());
    }

    @Test
    void sentenceWithNumberOfWords() {
        assertNotNull(dummy4j.lorem().sentence(10));
    }

    @Test
    void sentenceWithMinAndMaxWords() {
        assertNotNull(dummy4j.lorem().sentence(5, 10));
    }

    @Test
    void word() {
        assertNotNull(dummy4j.lorem().word());
    }

    @Test
    void paragraph() {
        assertNotNull(dummy4j.lorem().paragraph());
    }

    @Test
    void paragraphWithNumberOfSentences() {
        assertNotNull(dummy4j.lorem().paragraph(10));
    }
}