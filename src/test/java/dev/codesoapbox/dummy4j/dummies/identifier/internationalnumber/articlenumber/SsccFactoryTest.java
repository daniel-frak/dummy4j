package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SsccFactoryTest {

    private SsccFactory factory;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ModTenFormula modTenFormula;

    @Mock
    private NumberService numberService;

    @BeforeEach
    void setUp() {
        factory = new SsccFactory(dummy4j, modTenFormula);
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    @Test
    void shouldCreateSSCC() {
        when(numberService.digits(SsccFactory.DATA_PART_LENGTH))
                .thenReturn("123");
        when(modTenFormula.generateCheckDigit("123"))
                .thenReturn(9);

        String actual = factory.createCode();

        assertEquals("(00)1239", actual);
    }
}