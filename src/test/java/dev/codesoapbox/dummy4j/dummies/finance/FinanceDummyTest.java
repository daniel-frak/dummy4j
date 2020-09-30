package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.*;
import dev.codesoapbox.dummy4j.dummies.shared.valueobject.Address;
import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinanceDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NumberService numberService;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private LuhnFormula luhnFormula;

    @Mock
    private IbanFormula ibanFormula;

    @Mock
    private LoremDummy loremDummy;

    @Mock
    private NationDummy nationDummy;

    @Mock
    private AddressDummy addressDummy;

    @Mock
    private NameDummy nameDummy;

    @Mock
    private DateAndTimeDummy dateAndTimeDummy;

    private FinanceDummy financeDummy;

    @BeforeEach
    void setUp() {
        financeDummy = new FinanceDummy(dummy4j, luhnFormula, ibanFormula);
    }

    @Test
    void shouldReturnDefaultPrice() {
        mockNumberService();
        mockNextFloat();

        String actual = financeDummy.price();

        assertEquals("12.35", actual);
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    private void mockNextFloat() {
        when(numberService.nextFloat(PriceBuilder.LOWER_BOUNDARY, PriceBuilder.UPPER_BOUNDARY))
                .thenReturn(12.34567F);
    }

    @Test
    void shouldBuildPrice() {
        mockNumberService();
        mockNextFloat();

        String actual = financeDummy.priceBuilder().build();

        assertEquals("12.35", actual);
    }

    @Test
    void shouldReturnCurrencyCode() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.CURRENCY_CODE_KEY))
                .thenReturn("EUR");

        String actual = financeDummy.currencyCode();

        assertEquals("EUR", actual);
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void shouldReturnCurrencyNumericCode() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.CURRENCY_NUMERIC_CODE_KEY))
                .thenReturn("971");

        String actual = financeDummy.currencyNumericCode();

        assertEquals("971", actual);
    }

    @Test
    void shouldReturnCurrencyName() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.CURRENCY_NAME_KEY))
                .thenReturn("New Zealand Dollar");

        String actual = financeDummy.currencyName();

        assertEquals("New Zealand Dollar", actual);
    }

    @Test
    void shouldReturnCurrencySymbol() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.CURRENCY_SYMBOL_KEY))
                .thenReturn("¥");

        String actual = financeDummy.currencySymbol();

        assertEquals("¥", actual);
    }

    @Test
    void shouldReturnCryptoCurrencyCode() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.CRYPTO_CURRENCY_CODE_KEY))
                .thenReturn("BTC");

        String actual = financeDummy.cryptoCurrencyCode();

        assertEquals("BTC", actual);
    }

    @Test
    void shouldReturnCryptoCurrencyName() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.CRYPTO_CURRENCY_NAME_KEY))
                .thenReturn("Bitcoin");

        String actual = financeDummy.cryptoCurrencyName();

        assertEquals("Bitcoin", actual);
    }

    @Test
    void shouldReturnCryptoCurrencySymbol() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.CRYPTO_CURRENCY_SYMBOL_KEY))
                .thenReturn("ɱ");

        String actual = financeDummy.cryptoCurrencySymbol();

        assertEquals("ɱ", actual);
    }

    @Test
    void shouldReturnCreditCardProvider() {
        mockCreditCardProvider();

        String actual = financeDummy.creditCardProvider();

        assertEquals(CreditCardProvider.AMERICAN_EXPRESS.getValue(), actual);
    }

    private void mockCreditCardProvider() {
        when(dummy4j.nextEnum(CreditCardProvider.class))
                .thenReturn(CreditCardProvider.AMERICAN_EXPRESS);
    }

    @Test
    void shouldReturnCreditCardNumber() {
        mockCreditCardProvider();
        mockCreditCardNumber();

        String actual = financeDummy.creditCardNumber();

        assertEquals("3412345678901237", actual);
    }

    private void mockCreditCardNumber() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_CREDIT_CARD_KEY + "american_express}"))
                .thenReturn("341234567890123");
        when(luhnFormula.getCheckDigit("341234567890123"))
                .thenReturn("7");
    }

    @Test
    void shouldReturnCreditCardNumberForGivenProvider() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_CREDIT_CARD_KEY + "visa}"))
                .thenReturn("415025918277486");
        when(luhnFormula.getCheckDigit("415025918277486"))
                .thenReturn("1");

        String actual = financeDummy.creditCardNumber(CreditCardProvider.VISA);

        assertEquals("4150259182774861", actual);
    }

    @Test
    void shouldReturnCreditCardWithOneDigitMonthPadded() {
        Address address = getAddress();
        mockCreditCardData(address);
        mockExpiryDate(5);

        CreditCard actual = financeDummy.creditCard();

        assertAll(
                () -> assertEquals("3412345678901237", actual.getNumber()),
                () -> assertEquals(CreditCardProvider.AMERICAN_EXPRESS, actual.getProvider()),
                () -> assertEquals("Zoe Anderson", actual.getOwnerName()),
                () -> assertEquals(address, actual.getOwnerAddress()),
                () -> assertEquals("05/2030", actual.getExpiryDate()),
                () -> assertEquals("111", actual.getSecurityCode()),
                () -> {
                    String expectedString = "CreditCard{number='3412345678901237', provider=American Express, " +
                            "ownerName='Zoe Anderson', ownerAddress='street, 123 city, country', " +
                            "expiryDate='05/2030', securityCode='111'}";
                    assertEquals(expectedString, actual.toString());
                }
        );
    }

    private Address getAddress() {
        return new Address("street", "123", "city", "country");
    }

    private void mockCreditCardData(Address address) {
        mockNumberService();
        mockCreditCardProvider();
        mockCreditCardNumber();
        mockOwnerName();
        mockOwnerAddress(address);
        mockSecurityCode();
    }

    private void mockOwnerName() {
        when(dummy4j.name())
                .thenReturn(nameDummy);
        when(nameDummy.firstName())
                .thenReturn("Zoe");
        when(nameDummy.lastName())
                .thenReturn("Anderson");
    }

    private void mockOwnerAddress(Address address) {
        when(dummy4j.address())
                .thenReturn(addressDummy);
        when(addressDummy.street())
                .thenReturn(address.getStreet());
        when(addressDummy.postCode())
                .thenReturn(address.getPostCode());
        when(addressDummy.city())
                .thenReturn(address.getCity());
        when(addressDummy.country())
                .thenReturn(address.getCountry());
    }

    private void mockExpiryDate(int month) {
        when(dummy4j.dateAndTime())
                .thenReturn(dateAndTimeDummy);
        when(dateAndTimeDummy.future(FinanceDummy.MAX_DAYS_FOR_EXPIRY_DATE, ChronoUnit.DAYS))
                .thenReturn(LocalDate.of(2030, month, 15).atStartOfDay());
    }

    private void mockSecurityCode() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(FinanceDummy.MIN_SECURITY_CODE, FinanceDummy.MAX_SECURITY_CODE))
                .thenReturn(111);
    }

    @Test
    void shouldReturnCreditCardWithTwoDigitMonth() {
        Address address = getAddress();
        mockCreditCardData(address);
        mockExpiryDate(10);

        CreditCard actual = financeDummy.creditCard();

        assertEquals("10/2030", actual.getExpiryDate());
    }

    @Test
    void shouldReturnBicNumber() {
        mockLoremDummy();
        mockBankCode();
        mockCountryCode();
        mockLocationCode();
        mockBranchCode();

        String actual = financeDummy.bic();

        assertEquals("ABCDADEF123", actual);
    }

    private void mockLoremDummy() {
        when(dummy4j.lorem())
                .thenReturn(loremDummy);
    }

    private void mockBankCode() {
        when(loremDummy.characters(4))
                .thenReturn("ABCD");
    }

    private void mockCountryCode() {
        when(dummy4j.nation())
                .thenReturn(nationDummy);
        when(nationDummy.countryCode())
                .thenReturn("AD");
    }

    private void mockLocationCode() {
        when(loremDummy.characters(2))
                .thenReturn("EF");
    }

    private void mockBranchCode() {
        mockNumberService();
        when(numberService.nextInt(100, 999))
                .thenReturn(123);
    }

    @Test
    void shouldReturnBankAccountNumber() {
        mockExpressionResolver();
        mockLettersInAccountNumber();
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}"))
                .thenReturn("12345678912345678_");

        String actual = financeDummy.bankAccountNumber(country);

        assertEquals("12345678912345678A", actual);
    }

    private void mockLettersInAccountNumber() {
        mockLoremDummy();
        when(loremDummy.character())
                .thenReturn("a");
    }

    @Test
    void shouldThrowExceptionOnTooShortAccount() {
        mockExpressionResolver();
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}"))
                .thenReturn("123");

        assertThrows(ValueOutOfRangeException.class, () -> financeDummy.bankAccountNumber(country));
    }

    @Test
    void shouldThrowExceptionOnTooLongAccount() {
        mockExpressionResolver();
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}"))
                .thenReturn("12345678901234567890123456789012345");

        assertThrows(ValueOutOfRangeException.class, () -> financeDummy.bankAccountNumber(country));
    }

    @Test
    void shouldReturnIbanForRandomCountry() {
        mockExpressionResolver();
        mockLettersInAccountNumber();
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        when(dummy4j.nextEnum(CountrySupportingBankAccount.class))
                .thenReturn(country);
        String account = "123456789123456789";
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}"))
                .thenReturn(account);
        when(ibanFormula.getCheckDigits(account, country.getCode()))
                .thenReturn("00");
        mockFinanceDummy();

        String actual = financeDummy.iban();

        assertEquals("DE00123456789123456789", actual);
    }

    @Test
    void shouldBuildIbanForGivenCountry() {
        mockExpressionResolver();
        mockLettersInAccountNumber();
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        String account = "123456789123456789";
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}"))
                .thenReturn(account);
        when(ibanFormula.getCheckDigits(account, country.getCode()))
                .thenReturn("00");
        mockFinanceDummy();

        String actual = financeDummy
                .ibanBuilder()
                .withCountry(country)
                .build();

        assertEquals("DE00123456789123456789", actual);
    }

    /**
     * IbanBuilder::build uses FinanceDummy::bankAccountNumber.
     * Therefore, we have to mock dummy4j.finance call from IbanBuilder.
     */
    private void mockFinanceDummy() {
        when(dummy4j.finance())
                .thenReturn(financeDummy);
    }

    @Test
    void shouldReturnBankAccountType() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.BANK_ACCOUNT_TYPE_KEY))
                .thenReturn("Savings");

        String actual = financeDummy.bankAccountType();

        assertEquals("Savings", actual);
    }

    @Test
    void shouldReturnPaymentOption() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.PAYMENT_OPTION_KEY))
                .thenReturn("Cash");

        String actual = financeDummy.paymentOption();

        assertEquals("Cash", actual);
    }

    @Test
    void shouldReturnFinancialOperation() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.FINANCIAL_OPERATION_KEY))
                .thenReturn("Withdrawal");

        String actual = financeDummy.financialOperation();

        assertEquals("Withdrawal", actual);
    }

    @Test
    void shouldReturnBitcoinAddress() {
        mockExpressionResolver();
        when(expressionResolver.resolve(FinanceDummy.BITCOIN_ADDRESS_FORMAT_KEY))
                .thenReturn("bc1");
        when(expressionResolver.resolve(FinanceDummy.BITCOIN_ADDRESS_CHARACTERS_KEY))
                .thenReturn("a");
        mockNumberService();
        when(numberService.nextInt(FinanceDummy.BITCOIN_ADDRESS_MIN_LENGTH,
                FinanceDummy.BITCOIN_ADDRESS_MAX_LENGTH - 3))
                .thenReturn(23);

        String actual = financeDummy.bitcoinAddress();

        assertEquals("bc1aaaaaaaaaaaaaaaaaaaaaaa", actual);
    }
}