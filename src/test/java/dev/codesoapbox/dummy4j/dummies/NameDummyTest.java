package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void prefix() {
        assertNotNull(dummy4j.name().prefix());
        assertFalse(dummy4j.name().prefix().isEmpty());
    }

    @Test
    void suffix() {
        assertNotNull(dummy4j.name().suffix());
        assertFalse(dummy4j.name().suffix().isEmpty());
    }

    @Test
    void firstName() {
        assertNotNull(dummy4j.name().firstName());
        assertFalse(dummy4j.name().firstName().isEmpty());
    }

    @Test
    void lastName() {
        assertNotNull(dummy4j.name().lastName());
        assertFalse(dummy4j.name().lastName().isEmpty());
    }

    @Test
    void fullName() {
        assertNotNull(dummy4j.name().fullName());
        assertFalse(dummy4j.name().fullName().isEmpty());
    }

    @Test
    void fullNameWithMiddle() {
        assertNotNull(dummy4j.name().fullNameWithMiddle());
        assertFalse(dummy4j.name().fullNameWithMiddle().isEmpty());
    }
}