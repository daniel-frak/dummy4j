package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.dummies.shared.valueobject.Address;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    @Test
    void shouldReturnCreditCard() {
        Address address = new Address("street", "123", "city", "country");

        CreditCard actual = new CreditCard("123", CreditCardProvider.AMERICAN_EXPRESS, "Zoe Anderson",
                address, "05/2030", "111");

        assertAll(
                () -> assertEquals("123", actual.getNumber()),
                () -> assertEquals(CreditCardProvider.AMERICAN_EXPRESS, actual.getProvider()),
                () -> assertEquals("Zoe Anderson", actual.getOwnerName()),
                () -> assertEquals(address, actual.getOwnerAddress()),
                () -> assertEquals("05/2030", actual.getExpiryDate()),
                () -> assertEquals("111", actual.getSecurityCode()),
                () -> {
                    String expectedString = "CreditCard{number='123', provider=American Express, " +
                            "ownerName='Zoe Anderson', ownerAddress='street, 123 city country', " +
                            "expiryDate='05/2030', securityCode='111'}";
                    assertEquals(expectedString, actual.toString());
                }
        );
    }

    @ParameterizedTest
    @CsvSource(emptyValue = "", value = {
            ",Zoe Anderson,05/2030,111",
            "123,,05/2030,111",
            "123,Zoe Anderson,,111",
            "123,Zoe Anderson,05/2030,",
            "'',Zoe Anderson,05/2030,111",
            "123,'',05/2030,111",
            "123,Zoe Anderson,'',111",
            "123,Zoe Anderson,05/2030,''",
    })
    void shouldThrowExceptionOnNullStringValues(String number, String owner, String expiryDate, String securityCode) {
        Address address = new Address("street", "123", "city", "country");
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCard(number, CreditCardProvider.VISA, owner, address, expiryDate, securityCode));
    }

    @Test
    void shouldThrowExceptionOnNullObjectValues() {
        Address address = new Address("street", "123", "city", "country");

        assertThrows(IllegalArgumentException.class, () ->
                new CreditCard("3", null, "Z", address, "2", "1"));

        assertThrows(IllegalArgumentException.class, () -> new CreditCard("3", CreditCardProvider.VISA,
                "Z", null, "2", "1"));
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(CreditCard.class)
                .withNonnullFields("number", "provider", "ownerName", "ownerAddress", "expiryDate", "securityCode")
                .verify();
    }
}