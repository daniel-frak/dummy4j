package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModElevenDashTwoFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicIsniProviderTest {

    private BasicIsniProvider isniProvider;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ModElevenDashTwoFormula modElevenDashTwoFormula;

    @Mock
    private NumberService numberService;

    @BeforeEach
    void setUp() {
        isniProvider = new BasicIsniProvider(dummy4j, modElevenDashTwoFormula);
        mockPartWithoutCheckDigit();
        mockCheckDigit();
    }

    private void mockCheckDigit() {
        when(modElevenDashTwoFormula.generateCheckDigit("123456789012345"))
                .thenReturn(1);
    }

    private void mockPartWithoutCheckDigit() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.digits(BasicIsniProvider.LENGTH))
                .thenReturn("123456789012345");
    }

    @Test
    void shouldBuildIsni() {
        String actual = isniProvider.provide();

        assertEquals("1234567890123451", actual);
    }
}