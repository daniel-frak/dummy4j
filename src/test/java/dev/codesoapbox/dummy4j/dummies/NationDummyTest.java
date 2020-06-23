package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NationDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void country() {
        assertNotNull(dummy4j.nation().country());
        assertFalse(dummy4j.nation().country().isEmpty());
    }

    @Test
    void countryCode() {
        assertNotNull(dummy4j.nation().countryCode());
        assertFalse(dummy4j.nation().countryCode().isEmpty());
    }

    @Test
    void nationality() {
        assertNotNull(dummy4j.nation().nationality());
        assertFalse(dummy4j.nation().nationality().isEmpty());
    }
}