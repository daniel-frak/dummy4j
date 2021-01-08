package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.serialnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModElevenFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssnFactoryTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ModElevenFormula modElevenFormula;

    @Mock
    private NumberService numberService;

    private IssnFactory factory;

    @BeforeEach
    void setUp() {
        factory = new IssnFactory(dummy4j, modElevenFormula);
    }

    @ParameterizedTest
    @CsvSource({
            "9, 1234-5679",
            "10, 1234-567X"
    })
    void shouldCreateIssn(Integer checkDigit, String expected) {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.digits(4))
                .thenReturn("1234");
        when(numberService.digits(3))
                .thenReturn("567");
        when(modElevenFormula.generateCheckDigit("1234-567"))
                .thenReturn(checkDigit);

        String actual = factory.createIssn();

        assertEquals(expected, actual);
    }
}