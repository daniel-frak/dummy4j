package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResearchPaperDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void title() {
        String value = dummy4j.researchPaper().title();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void titleSocial() {
        String value = dummy4j.researchPaper().titleSocial();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void titleAnthropology() {
        String value = dummy4j.researchPaper().titleAnthropology();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void titleHistory() {
        String value = dummy4j.researchPaper().titleHistory();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void titleNatural() {
        String value = dummy4j.researchPaper().titleNatural();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void titlePhysics() {
        String value = dummy4j.researchPaper().titlePhysics();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void titleBiology() {
        String value = dummy4j.researchPaper().titleBiology();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}