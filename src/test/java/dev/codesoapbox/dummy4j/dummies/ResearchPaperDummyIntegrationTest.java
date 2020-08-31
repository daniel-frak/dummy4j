package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResearchPaperDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void title() {
        assertEquals("An Introduction to the History of Medicine", dummy4j.researchPaper().title());
    }

    @Test
    void titleSocial() {
        assertEquals("An Introduction to the History of Medicine", dummy4j.researchPaper().titleSocial());
    }

    @Test
    void titleAnthropology() {
        assertEquals("The Role of Social Networks in Modern times", dummy4j.researchPaper().titleAnthropology());
    }

    @Test
    void titleHistory() {
        assertEquals("An Introduction to the History of Medicine", dummy4j.researchPaper().titleHistory());
    }

    @Test
    void titleNatural() {
        assertEquals("Transport Phenomena in Cosmic-ray Interactions", dummy4j.researchPaper().titleNatural());
    }

    @Test
    void titlePhysics() {
        assertEquals("Transport Phenomena in Cosmic-ray Interactions", dummy4j.researchPaper().titlePhysics());
    }

    @Test
    void titleBiology() {
        assertEquals("Regulation of Aquaporins in Plants Under Stress", dummy4j.researchPaper().titleBiology());
    }
}