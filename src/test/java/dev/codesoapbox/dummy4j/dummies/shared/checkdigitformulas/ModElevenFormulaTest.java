package dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModElevenFormulaTest {

    private ModElevenFormula modElevenFormula;

    @BeforeEach
    void setUp() {
        modElevenFormula = new ModElevenFormula();
    }

    @ParameterizedTest
    @CsvSource({
            "0378-595, 5",
            "0024-931, 9",
            "0032-147, 8",
            "1144-875, 10",
            "2049-363, 0",
            "1234-567, 9"
    })
    void shouldCalculateCheckDigitForIssn(String input, Integer expected) {
        assertEquals(expected, modElevenFormula.generateCheckDigit(input));
    }

    @ParameterizedTest
    @CsvSource({
            "123456789, 10",
            "997150210, 0",
            "030640615, 2",
            "589308217, 6",
            "846200700, 3"
    })
    void shouldCalculateCheckDigitForTenCharsIsbn(String input, Integer expected) {
        assertEquals(expected, modElevenFormula.generateCheckDigit(input));
    }
}