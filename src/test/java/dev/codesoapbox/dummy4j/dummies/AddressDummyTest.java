package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void city() {
        assertNotNull(dummy4j.address().city());
    }

    @Test
    void postCode() {
        assertNotNull(dummy4j.address().postCode());
    }

    @Test
    void street() {
        assertNotNull(dummy4j.address().street());
    }

    @Test
    void country() {
        assertNotNull(dummy4j.address().country());
    }

    @Test
    void countryCode() {
        assertNotNull(dummy4j.address().countryCode());
    }
}