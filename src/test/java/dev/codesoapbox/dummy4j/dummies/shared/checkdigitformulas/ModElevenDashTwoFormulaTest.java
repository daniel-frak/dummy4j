package dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ModElevenDashTwoFormulaTest {

    private ModElevenDashTwoFormula modElevenDashTwoFormula;

    @BeforeEach
    void setUp() {
        modElevenDashTwoFormula = new ModElevenDashTwoFormula();
    }

    @ParameterizedTest
    @CsvSource({
            "123, 1",
            "784379434538222, 5",
            "3043 4433 8247 664, 0",
            "7556-0155-8593-624, 10",
            "5665 9580 9314 459, 10",
            "327782431991201, 10",
            "953171893053000, 1"
    })
    void shouldCalculateCheckDigitForIssn(String input, Integer expected) {
        assertEquals(expected, modElevenDashTwoFormula.generateCheckDigit(input));
    }
}