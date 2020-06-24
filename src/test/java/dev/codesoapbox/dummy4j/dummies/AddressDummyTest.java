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
        String value = dummy4j.address().city();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void postCode() {
        String value = dummy4j.address().postCode();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void street() {
        String value = dummy4j.address().street();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void country() {
        String value = dummy4j.address().country();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void countryCode() {
        String value = dummy4j.address().countryCode();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}