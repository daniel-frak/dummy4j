package dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModTenFormulaTest {

    private ModTenFormula modTenFormula;

    @BeforeEach
    void setUp() {
        modTenFormula = new ModTenFormula();
    }

    @ParameterizedTest
    @CsvSource({
            "1234567, 0",
            "7351353, 7",
            "3811575, 2",
            "91126083238, 9",
            "80538360311, 0",
            "02338789047, 7",
            "978047111709, 4",
            "400638133393, 1",
    })
    void shouldCalculateCheckDigit(String input, Integer expected) {
        assertEquals(expected, modTenFormula.generateCheckDigit(input));
    }
}