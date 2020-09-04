package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinanceDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void shouldReturnPrice() {
        String actual = dummy4j.finance().price();
        assertAll(
                () -> assertNotNull(actual),
                () -> assertFalse(actual.isEmpty())
        );
    }

    @Test
    void shouldBuildPrice() {
        String actual = dummy4j.finance().priceBuilder().build();
        assertAll(
                () -> assertNotNull(actual),
                () -> assertFalse(actual.isEmpty())
        );
    }

    @Test
    void shouldReturnCurrencyCode() {
        assertEquals("EUR", dummy4j.finance().currencyCode());
    }

    @Test
    void shouldReturnCurrencyNumericCode() {
        assertEquals("971", dummy4j.finance().currencyNumericCode());
    }

    @Test
    void shouldReturnCurrencyName() {
        assertEquals("Bulgarian Lev", dummy4j.finance().currencyName());
    }

    @Test
    void shouldReturnCurrencySymbol() {
        assertEquals("£", dummy4j.finance().currencySymbol());
    }

    @Test
    void shouldReturnCryptoCurrencyCode() {
        assertEquals("BTC", dummy4j.finance().cryptoCurrencyCode());
    }

    @Test
    void shouldReturnCryptoCurrencyName() {
        assertEquals("Bitcoin", dummy4j.finance().cryptoCurrencyName());
    }

    @Test
    void shouldReturnCryptoCurrencySymbol() {
        assertEquals("ɱ", dummy4j.finance().cryptoCurrencySymbol());
    }

    @Test
    void shouldReturnCreditCardProvider() {
        assertNotNull(dummy4j.finance().creditCardProvider());
    }

    @Test
    void shouldReturnCreditCardNumber() {
        String actual = dummy4j.finance().creditCardNumber();

        assertAll(
                () -> assertNotNull(actual, "Credit card number is null"),
                () -> assertFalse(actual.isEmpty(), "Credit card number is empty")
        );
    }

    @Test
    void shouldReturnCreditCardNumberForGivenProvider() {
        String actual = dummy4j.finance().creditCardNumber(CreditCardProvider.VISA);

        assertEquals("4150259182774861", actual);
    }

    @Test
    void shouldReturnBicNumber() {
        String actual = dummy4j.finance().bic();

        assertTrue(actual.matches("AAAAADAA\\d{3}"));
    }

    @Test
    void shouldReturnBankAccountNumberForAllSupportedCountries() {
        for (CountrySupportingBankAccount code : CountrySupportingBankAccount.values()) {
            String actual = dummy4j.finance().bankAccountNumber(code);

            assertFalse(actual.isEmpty(), "Missing number for " + code);
        }
    }

    @Test
    void shouldReturnIban() {
        String actual = dummy4j.finance().iban();

        assertAll(
                () -> assertNotNull(actual, "IBAN is null"),
                () -> assertFalse(actual.isEmpty(), "IBAN is empty")
        );
    }

    @Test
    void shouldBuildIban() {
        String actual = dummy4j.finance().ibanBuilder().build();

        assertAll(
                () -> assertNotNull(actual, "IBAN is null"),
                () -> assertFalse(actual.isEmpty(), "IBAN is empty")
        );
    }

    @Test
    void shouldReturnBankAccountType() {
        String actual = dummy4j.finance().bankAccountType();

        assertEquals("Deposit", actual);
    }

    @Test
    void shouldReturnPaymentOption() {
        String actual = dummy4j.finance().paymentOption();

        assertEquals("Bank transfer", actual);
    }

    @Test
    void shouldReturnFinancialOperation() {
        String actual = dummy4j.finance().financialOperation();

        assertEquals("Deposit", actual);
    }

    @Test
    void shouldReturnBitcoinAddress() {
        String actual = dummy4j.finance().bitcoinAddress();

        assertAll(
                () -> assertNotNull(actual, "Bitcoin address is null"),
                () -> assertFalse(actual.isEmpty(), "Bitcoin address is empty")
        );
    }
}