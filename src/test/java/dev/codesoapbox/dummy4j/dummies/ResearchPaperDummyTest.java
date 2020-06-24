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
        assertNotNull(dummy4j.researchPaper().title());
        assertFalse(dummy4j.researchPaper().title().isEmpty());
    }

    @Test
    void titleSocial() {
        assertNotNull(dummy4j.researchPaper().titleSocial());
        assertFalse(dummy4j.researchPaper().titleSocial().isEmpty());
    }

    @Test
    void titleAnthropology() {
        assertNotNull(dummy4j.researchPaper().titleAnthropology());
        assertFalse(dummy4j.researchPaper().titleAnthropology().isEmpty());
    }

    @Test
    void titleHistory() {
        assertNotNull(dummy4j.researchPaper().titleHistory());
        assertFalse(dummy4j.researchPaper().titleHistory().isEmpty());
    }

    @Test
    void titleNatural() {
        assertNotNull(dummy4j.researchPaper().titleNatural());
        assertFalse(dummy4j.researchPaper().titleNatural().isEmpty());
    }

    @Test
    void titlePhysics() {
        assertNotNull(dummy4j.researchPaper().titlePhysics());
        assertFalse(dummy4j.researchPaper().titlePhysics().isEmpty());
    }
}