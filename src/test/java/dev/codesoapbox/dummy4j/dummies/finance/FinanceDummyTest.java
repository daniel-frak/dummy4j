package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.NationDummy;
import dev.codesoapbox.dummy4j.dummies.shared.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinanceDummyTest {

    @Mock
    IbanBuilder ibanBuilder;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NumberService numberService;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private FinanceBuilderFactory financeBuilderFactory;

    @Mock
    private CreditCardNumberBuilder creditCardNumberBuilder;

    @Mock
    private CreditCardBuilder creditCardBuilder;

    @Mock
    private NationDummy nationDummy;

    private FinanceDummy financeDummy;

    @BeforeEach
    void setUp() {
        financeDummy = new FinanceDummy(dummy4j, financeBuilderFactory);
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
        when(dummy4j.nextEnum(CreditCardProvider.class))
                .thenReturn(CreditCardProvider.AMERICAN_EXPRESS);

        CreditCardProvider actual = financeDummy.creditCardProvider();

        assertEquals(CreditCardProvider.AMERICAN_EXPRESS, actual);
    }

    @Test
    void shouldReturnCreditCardNumber() {
        when(financeBuilderFactory.createCreditCardNumberBuilder())
                .thenReturn(creditCardNumberBuilder);
        when(creditCardNumberBuilder.build())
                .thenReturn("3412 345678 90127");

        String actual = financeDummy.creditCardNumber();

        assertEquals("3412 345678 90127", actual);
    }

    @Test
    void shouldReturnCreditCardNumberBuilder() {
        when(financeBuilderFactory.createCreditCardNumberBuilder())
                .thenReturn(creditCardNumberBuilder);

        CreditCardNumberBuilder actual = financeDummy.creditCardNumberBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnCreditCard() {
        CreditCard card = getCreditCard();
        when(financeBuilderFactory.createCreditCardBuilder())
                .thenReturn(creditCardBuilder);
        when(creditCardBuilder.build())
                .thenReturn(card);

        CreditCard actual = financeDummy.creditCard();

        assertEquals(card, actual);
    }

    private CreditCard getCreditCard() {
        Address address = new Address("street", "123", "city", "country");

        return new CreditCard("3412 345678 90127", CreditCardProvider.AMERICAN_EXPRESS, "Zoe Anderson",
                address, "05/2030", "111");
    }

    @Test
    void shouldReturnCreditCarBuilder() {
        when(financeBuilderFactory.createCreditCardBuilder())
                .thenReturn(creditCardBuilder);

        CreditCardBuilder actual = financeDummy.creditCardBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnBicNumber() {
        mockBankCode();
        mockCountryCode();
        mockLocationCode();
        mockBranchCode();

        String actual = financeDummy.bic();

        assertEquals("ABCDADEF123", actual);
    }

    private void mockBankCode() {
        when(dummy4j.listOf(eq(4), any()))
                .thenReturn(Arrays.asList("A", "B", "C", "D"));
    }

    private void mockCountryCode() {
        when(dummy4j.nation())
                .thenReturn(nationDummy);
        when(nationDummy.countryCode())
                .thenReturn("AD");
    }

    private void mockLocationCode() {
        when(dummy4j.listOf(eq(2), any()))
                .thenReturn(Arrays.asList("E", "F"));
    }

    private void mockBranchCode() {
        mockNumberService();
        when(numberService.nextInt(100, 999))
                .thenReturn(123);
    }

    @Test
    void shouldReturnBankAccountNumber() {
        mockExpressionResolver();
        BankAccountCountry country = BankAccountCountry.GERMANY;
        when(expressionResolver.resolve(FinanceDummy.BANK_ACCOUNT_LETTER_KEY))
                .thenReturn("A");
        when(expressionResolver.resolve(FinanceDummy.PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}"))
                .thenReturn("12345678912345678_");

        String actual = financeDummy.bankAccountNumber(country);

        assertEquals("12345678912345678A", actual);
    }

    @Test
    void shouldReturnIbanForRandomCountry() {
        when(financeBuilderFactory.createIbanBuilder())
                .thenReturn(ibanBuilder);
        when(ibanBuilder.build())
                .thenReturn("DE00123456789123456789");

        String actual = financeDummy.iban();

        assertEquals("DE00123456789123456789", actual);
    }

    @Test
    void shouldReturnIbanBuilder() {
        when(financeBuilderFactory.createIbanBuilder())
                .thenReturn(ibanBuilder);

        IbanBuilder actual = financeDummy.ibanBuilder();

        assertNotNull(actual);
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
        when(numberService.nextInt(FinanceDummy.BITCOIN_ADDRESS_MIN_LENGTH - 3,
                FinanceDummy.BITCOIN_ADDRESS_MAX_LENGTH - 3))
                .thenReturn(23);

        String actual = financeDummy.bitcoinAddress();

        assertEquals("bc1aaaaaaaaaaaaaaaaaaaaaaa", actual);
    }
}