package dev.codesoapbox.dummy4j.dummies.shared;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void shouldCreateAddress() {
        Address actual = new Address("street", "123", "city", "country");

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("street", actual.getStreet()),
                () -> assertEquals("123", actual.getPostCode()),
                () -> assertEquals("city", actual.getCity()),
                () -> assertEquals("country", actual.getCountry()),
                () -> assertEquals("street, 123 city, country", actual.toString())
        );
    }

    @ParameterizedTest
    @CsvSource({
            ",23,city,country",
            "street,,city,country",
            "street,123,,country",
            "street,123,city,",
            ",,,",
            "'',123,city,country",
            "street,'',city,country",
            "street,123,'',country",
            "street,123,city,''",
            "'','','',''"
    })
    void shouldThrowExceptionOnNullOrEmptyValues(String street, String postCode, String city, String country) {
        assertThrows(IllegalArgumentException.class, () -> new Address(street, postCode, city, country));
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Address.class)
                .withNonnullFields("street", "city", "postCode", "country")
                .verify();
    }
}