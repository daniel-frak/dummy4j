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
        String value = dummy4j.name().prefix();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void suffix() {
        String value = dummy4j.name().suffix();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void firstName() {
        String value = dummy4j.name().firstName();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void lastName() {
        String value = dummy4j.name().lastName();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void fullName() {
        String value = dummy4j.name().fullName();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void fullNameWithMiddle() {
        String value = dummy4j.name().fullNameWithMiddle();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}