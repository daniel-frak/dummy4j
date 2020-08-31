package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void prefix() {
        assertEquals("Dr.", dummy4j.name().prefix());
    }

    @Test
    void suffix() {
        assertEquals("Jr.", dummy4j.name().suffix());
    }

    @Test
    void firstName() {
        assertEquals("Zoe", dummy4j.name().firstName());
    }

    @Test
    void lastName() {
        assertEquals("Anderson", dummy4j.name().lastName());
    }

    @Test
    void fullName() {
        assertEquals("Dr. Zoe Anderson", dummy4j.name().fullName());
    }

    @Test
    void fullNameWithMiddle() {
        assertEquals("Dr. Zoe Anderson Anderson", dummy4j.name().fullNameWithMiddle());
    }
}