package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Gtin13BuilderTest {

    private final Gs1PrefixRange testPrefixRange = Gs1PrefixRange.FRANCE_MONACO;

    private Gtin13Builder builder;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ModTenFormula modTenFormula;

    @Mock
    private NumberService numberService;

    @BeforeEach
    void setUp() {
        builder = new Gtin13Builder(dummy4j, modTenFormula);
        mockResolvingPrefix();
        mockDataPart();
        mockCheckDigit();
    }

    private void mockResolvingPrefix() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(anyInt(), anyInt()))
                .thenReturn(999);
    }

    private void mockDataPart() {
        when(numberService.digits(anyInt()))
                .thenReturn("1");
    }

    private void mockCheckDigit() {
        when(modTenFormula.generateCheckDigit("9991"))
                .thenReturn(2);
    }

    @Test
    void shouldBuildGtin() {
        when(dummy4j.oneOf((List<Object>) null))
                .thenReturn(null);
        when(dummy4j.nextEnum(Gs1PrefixRange.class))
                .thenReturn(testPrefixRange);

        String actual = builder.build();

        assertEquals("99912", actual);
    }

    @Test
    void shouldBuildGtinWithRandomGs1Prefix() {
        when(dummy4j.oneOf(new ArrayList<Gs1PrefixRange>()))
                .thenReturn(null);
        when(dummy4j.nextEnum(Gs1PrefixRange.class))
                .thenReturn(testPrefixRange);

        String actual = builder
                .withRandomGs1PrefixRange()
                .build();

        assertEquals("99912", actual);
    }

    @Test
    void shouldBuildGtinWithRandomGs1PrefixFromGivenList() {
        when(dummy4j.oneOf(Arrays.asList(testPrefixRange, Gs1PrefixRange.USA)))
                .thenReturn(testPrefixRange);

        String actual = builder
                .withRandomGs1PrefixRange(testPrefixRange, Gs1PrefixRange.USA)
                .build();

        assertEquals("99912", actual);
    }

    @Test
    void shouldBuildGtinWithGivenGs1Prefix() {
        when(dummy4j.oneOf(singletonList(testPrefixRange)))
                .thenReturn(testPrefixRange);

        String actual = builder
                .withGs1PrefixRange(testPrefixRange)
                .build();

        assertEquals("99912", actual);
    }
}