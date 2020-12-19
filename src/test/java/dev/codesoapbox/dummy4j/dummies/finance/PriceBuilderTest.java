package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceBuilderTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NumberService numberService;

    @Mock
    private ExpressionResolver expressionResolver;

    private PriceBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new PriceBuilder(dummy4j);
        mockNumberService();
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    @Test
    void shouldBuildPrice() {
        mockNextFloat();

        String actual = builder.build();

        assertEquals("12.35", actual);
    }

    private void mockNextFloat() {
        when(numberService.nextFloat(PriceBuilder.LOWER_BOUNDARY, PriceBuilder.UPPER_BOUNDARY))
                .thenReturn(12.345678F);
    }

    @Test
    void shouldBuildPriceWithGivenCurrency() {
        mockNextFloat();
        when(dummy4j.of(singletonList("USD")))
                .thenReturn("USD");

        String actual = builder
                .withCurrency("USD")
                .build();

        assertEquals("USD 12.35", actual);
    }

    @Test
    void shouldBuildPriceWithCurrencyChosenAtRandomFromDefinitions() {
        mockNextFloat();
        mockExpressionResolver();
        mockRandomCurrency();

        String actual = builder
                .withRandomCurrency()
                .build();

        assertEquals("EUR 12.35", actual);
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    private void mockRandomCurrency() {
        when(expressionResolver.resolve(FinanceDummy.CURRENCY_CODE_KEY))
                .thenReturn("EUR");
    }

    @Test
    void shouldBuildPriceWithCurrencyChosenAtRandomFromProvidedArguments() {
        mockNextFloat();
        when(dummy4j.of(asList("JPY", "PLN", "USD")))
                .thenReturn("JPY");

        String actual = builder
                .withRandomCurrency("JPY", "PLN", "USD")
                .build();

        assertEquals("JPY 12.35", actual);
        verify(expressionResolver, never()).resolve(FinanceDummy.CURRENCY_CODE_KEY);
    }

    @Test
    void shouldBuildPriceWithoutCurrency() {
        mockNextFloat();

        String actual = builder
                .withoutCurrency()
                .build();

        assertEquals("12.35", actual);
        verify(expressionResolver, never()).resolve(FinanceDummy.CURRENCY_CODE_KEY);
    }

    @Test
    void shouldBuildHighPrice() {
        mockNextHighFloat();

        String actual = builder
                .high()
                .build();

        assertEquals("1234.57", actual);
    }

    private void mockNextHighFloat() {
        when(numberService.nextFloat(PriceBuilder.LOWER_BOUNDARY_FOR_HIGH_PRICE,
                PriceBuilder.UPPER_BOUNDARY_FOR_HIGH_PRICE))
                .thenReturn(1234.5678F);
    }

    @Test
    void shouldBuildPriceInGivenRange() {
        when(numberService.nextFloat(5F, 15F))
                .thenReturn(12.75123F);

        String actual = builder
                .withinRange(5F, 15F)
                .build();

        assertEquals("12.75", actual);
    }
}