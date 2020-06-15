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
    }

    @Test
    void suffix() {
        assertNotNull(dummy4j.name().suffix());
    }

    @Test
    void firstName() {
        assertNotNull(dummy4j.name().firstName());
    }

    @Test
    void lastName() {
        assertNotNull(dummy4j.name().lastName());
    }

    @Test
    void fullName() {
        assertNotNull(dummy4j.name().fullName());
    }

    @Test
    void fullNameWithMiddle() {
        assertNotNull(dummy4j.name().fullNameWithMiddle());
    }
}