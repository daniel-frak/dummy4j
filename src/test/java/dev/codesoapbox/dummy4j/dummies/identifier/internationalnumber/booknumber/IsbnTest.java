package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IsbnTest {

    @Test
    void shouldConstruct() {
        Isbn actual = new Isbn(IsbnType.ISBN_13, "111", "222", "333", "444",
                "-", "8");

        assertAll(
                () -> assertEquals(IsbnType.ISBN_13, actual.getType(), "Invalid type"),
                () -> assertEquals("111", actual.getPrefix(), "Invalid prefix"),
                () -> assertEquals("222", actual.getRegistrationGroup(), "Invalid group"),
                () -> assertEquals("333", actual.getRegistrant(), "Invalid registrant"),
                () -> assertEquals("444", actual.getPublication(), "Invalid publication"),
                () -> assertEquals("8", actual.getCheckDigit(), "Invalid check digit"),
                () -> assertEquals("-", actual.getSeparator(), "Invalid separator")
        );
    }

    @Test
    void shouldReplaceNullSeparatorWithEmptyString() {
        Isbn actual = new Isbn(IsbnType.ISBN_13, "111", "222", "333", "444",
                null, "8");

        assertEquals("1112223334448", actual.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Isbn.class)
                .withNonnullFields("type", "prefix", "registrationGroup", "registrant", "publication", "checkDigit",
                        "separator")
                .verify();
    }
}