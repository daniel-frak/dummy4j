package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsmnBuilderTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ModTenFormula modTenFormula;

    @Mock
    private NumberService numberService;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private IsmnValidator ismnValidator;

    private IsmnBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new IsmnBuilder(dummy4j, modTenFormula, ismnValidator);
    }

    @Test
    void shouldBuildIsmnWithGivenParts() {
        mockFirstElementFromAnyList();
        mockNumberService();
        mockItem();
        when(modTenFormula.generateCheckDigit("9790111222"))
                .thenReturn(9);

        Ismn actual = builder
                .withRegistrant("111")
                .withSeparator("-")
                .build();

        assertEquals("979-0-111-222-9", actual.toString());
    }

    private void mockFirstElementFromAnyList() {
        when(dummy4j.oneOf(anyList()))
                .thenAnswer(inv -> ((List<?>) inv.getArgument(0)).get(0));
    }

    private void mockItem() {
        when(numberService.digits(anyInt()))
                .thenReturn("222");
    }

    @Test
    void shouldBuildIsmnWithDrawnParts() {
        mockFirstElementFromAnyList();
        mockNumberService();
        mockItem();
        when(modTenFormula.generateCheckDigit("9790111222"))
                .thenReturn(9);

        Ismn actual = builder
                .withRandomRegistrant("111")
                .withRandomSeparator("-")
                .build();

        assertEquals("979-0-111-222-9", actual.toString());
    }

    @Test
    void shouldBuildIsmnWithRandomParts() {
        mockRegistrantRange();
        mockNumberService();
        mockExpressionResolver();
        mockSeparator();
        mockItem();
        when(dummy4j.oneOf(emptyList()))
                .thenReturn(null);
        when(modTenFormula.generateCheckDigit("9790003222"))
                .thenReturn(9);

        Ismn actual = builder
                .withRandomRegistrant()
                .withRandomSeparator()
                .build();

        assertEquals("979-0-003-222-9", actual.toString());
    }

    private void mockRegistrantRange() {
        IsmnRegistrantRange registrantRange = IsmnBuilder.REGISTRANT_RANGES.get(0);
        when(dummy4j.oneOf(IsmnBuilder.REGISTRANT_RANGES))
                .thenReturn(registrantRange);
        when(numberService.nextInt(registrantRange.getMin(), registrantRange.getMax()))
                .thenReturn(3);
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    private void mockSeparator() {
        when(expressionResolver.resolve(IsmnBuilder.ISMN_SEPARATOR_KEY))
                .thenReturn("-");
    }

    @Test
    void shouldBuildIsmnWithGivenPartsWithoutSeparator() {
        mockFirstElementFromAnyList();
        mockNumberService();
        mockItem();
        when(modTenFormula.generateCheckDigit("9790111222"))
                .thenReturn(9);

        Ismn actual = builder
                .withRegistrant("111")
                .withoutSeparator()
                .build();

        assertEquals("97901112229", actual.toString());
    }

    @Test
    void shouldBuildDefaultIsmn() {
        mockRegistrantRange();
        mockNumberService();
        mockExpressionResolver();
        mockSeparator();
        mockItem();
        mockNullableParts();
        when(modTenFormula.generateCheckDigit("9790003222"))
                .thenReturn(9);

        Ismn actual = builder.build();

        assertEquals("979-0-003-222-9", actual.toString());
    }

    private void mockNullableParts() {
        when(dummy4j.oneOf((List<Object>) null))
                .thenReturn(null);
    }
}