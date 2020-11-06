package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardNumberBuilderTest {

    private static final int IIN_MIN = 34;
    private static final int IIN_MAX = 34;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private LuhnFormula luhnFormula;

    @Mock
    private NumberService numberService;

    private CreditCardNumberBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new CreditCardNumberBuilder(dummy4j, luhnFormula);
    }

    @Test
    void shouldGenerateCreditCardNumber() {
        mockCreditCardProvider();
        mockCreditCardNumber();

        String actual = builder.build();

        assertEquals("3412 345678 90127", actual);
    }

    private void mockCreditCardNumber() {
        mockNumberDefinition();
        mockIIN();
        mockCheckDigit();
    }

    private void mockCreditCardProvider() {
        when(dummy4j.nextEnum(CreditCardProvider.class))
                .thenReturn(CreditCardProvider.AMERICAN_EXPRESS);
    }

    private void mockNumberDefinition() {
        mockExpressionResolver();
        when(expressionResolver.resolve(CreditCardNumberBuilder.PARTIAL_CREDIT_CARD_KEY + "american_express}"))
                .thenReturn("0012 345678 9012");
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    private void mockIIN() {
        mockNumberService();
        when(dummy4j.of(CreditCardProvider.AMERICAN_EXPRESS.getIinRanges()))
                .thenReturn(IINRange.from(IIN_MIN, IIN_MAX));
        when(numberService.nextInt(IIN_MIN, IIN_MAX))
                .thenReturn(IIN_MIN);
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    private void mockCheckDigit() {
        when(luhnFormula.getCheckDigit("3412 345678 9012"))
                .thenReturn("7");
    }

    @Test
    void shouldGenerateCreditCardNumberWithProvider() {
        mockCreditCardNumber();

        String actual = builder.withProvider(CreditCardProvider.AMERICAN_EXPRESS).build();

        assertEquals("3412 345678 90127", actual);
    }

    @Test
    void shouldGenerateCreditCardWithRandomProvider() {
        mockCreditCardProvider();
        mockCreditCardNumber();

        String actual = builder
                .withProvider(CreditCardProvider.AMERICAN_EXPRESS)
                .withRandomProvider()
                .build();

        assertEquals("3412 345678 90127", actual);
    }

    @Test
    void shouldGenerateCreditCardNumberWithoutFormatting() {
        mockCreditCardProvider();
        mockCreditCardNumber();

        String actual = builder
                .clearFormatting()
                .build();

        assertEquals("341234567890127", actual);
    }

    @Test
    void shouldGenerateCreditCardWithRandomProviderWithoutFormatting() {
        mockCreditCardProvider();
        mockCreditCardNumber();

        String actual = builder
                .clearFormatting()
                .withRandomProvider()
                .build();

        assertEquals("341234567890127", actual);
    }

    @Test
    void shouldConvertBuilderToString() {
        String expected = "CreditCardNumberBuilder{provider=null, clearFormatting=false}";

        String actual = builder.toString();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReplaceWithLongIINAndKeepFormatting() {
        mockNumberService();
        mockExpressionResolver();
        mockIINRange();
        when(expressionResolver.resolve(CreditCardNumberBuilder.PARTIAL_CREDIT_CARD_KEY + "mastercard}"))
                .thenReturn("0012 3456 7890 123");
        when(luhnFormula.getCheckDigit("2720 9956 7890 123"))
                .thenReturn("7");

        String actual = builder
                .withProvider(CreditCardProvider.MASTER_CARD)
                .build();

        assertEquals("2720 9956 7890 1237", actual);
    }

    private void mockIINRange() {
        when(dummy4j.of(CreditCardProvider.MASTER_CARD.getIinRanges()))
                .thenReturn(IINRange.from(222100, 272099));
        when(numberService.nextInt(222100, 272099))
                .thenReturn(272099);
    }
}