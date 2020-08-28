package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void city() {
        assertEquals("North Zoeshire", dummy4j.address().city());
    }

    @Test
    void postCode() {
        assertEquals("12345-678", dummy4j.address().postCode());
    }

    @Test
    void street() {
        assertEquals("10 Anderson Canyon", dummy4j.address().street());
    }

    @Test
    void country() {
        assertEquals("Armenia", dummy4j.address().country());
    }

    @Test
    void countryCode() {
        assertEquals("AD", dummy4j.address().countryCode());
    }
}
