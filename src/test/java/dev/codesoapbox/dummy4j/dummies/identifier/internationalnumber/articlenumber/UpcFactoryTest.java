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
class UpcFactoryTest {

    private UpcFactory factory;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ModTenFormula modTenFormula;

    @Mock
    private NumberService numberService;

    @BeforeEach
    void setUp() {
        factory = new UpcFactory(dummy4j, modTenFormula);
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    @Test
    void shouldCreateUPC() {
        Gs1PrefixRange prefixRange = Gs1PrefixRange.UPCA_COMPATIBLE;
        when(numberService.nextInt(prefixRange.getMin(), prefixRange.getMax()))
                .thenReturn(1);
        when(numberService.digits(UpcFactory.DATA_PART_LENGTH))
                .thenReturn("45678901");
        when(modTenFormula.generateCheckDigit("00145678901"))
                .thenReturn(9);

        String actual = factory.createUpc();

        assertEquals("001456789019", actual);
    }
}