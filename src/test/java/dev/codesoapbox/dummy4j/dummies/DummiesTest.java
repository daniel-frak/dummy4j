package dev.codesoapbox.dummy4j.dummies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DummiesTest {

    private Dummies dummies;

    @BeforeEach
    void setUp() {
        dummies = new Dummies(null);
    }

    @Test
    void name() {
        assertNotNull(dummies.name());
    }

    @Test
    void address() {
        assertNotNull(dummies.address());
    }

    @Test
    void lorem() {
        assertNotNull(dummies.lorem());
    }

    @Test
    void dateAndTime() {
        assertNotNull(dummies.dateAndTime());
    }

    @Test
    void identifier() {
        assertNotNull(dummies.identifier());
    }

    @Test
    void book() {
        assertNotNull(dummies.book());
    }

    @Test
    void researchPaper() {
        assertNotNull(dummies.researchPaper());
    }

    @Test
    void scifi() {
        assertNotNull(dummies.scifi());
    }

    @Test
    void nation() {
        assertNotNull(dummies.nation());
    }

    @Test
    void education() {
        assertNotNull(dummies.education());
    }

    @Test
    void color() {
        assertNotNull(dummies.color());
    }

    @Test
    void numerals() {
        assertNotNull(dummies.numerals());
    }

    @Test
    void medical() {
        assertNotNull(dummies.medical());
    }

    @Test
    void natoPhoneticAlphabet() {
        assertNotNull(dummies.natoPhoneticAlphabet());
    }

    @Test
    void internet() {
        assertNotNull(dummies.internet());
    }
}