package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void full() {
        Address actual = dummy4j.address().full();

        assertAll(
                () -> assertEquals("North Zoeshire", actual.getCity()),
                () -> assertEquals("10 Anderson Canyon", actual.getStreet()),
                () -> assertEquals("12345-678", actual.getPostCode()),
                () -> assertEquals("Armenia", actual.getCountry())
        );
    }
}
