package dev.codesoapbox.dummy4j.dummies.finance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LuhnFormulaTest {

    private LuhnFormula luhnFormula;

    @BeforeEach
    void setUp() {
        luhnFormula = new LuhnFormula();
    }

    @ParameterizedTest
    @CsvSource({
            "455606909685229, 3",
            "455673758689985, 5",
            "453264032098369, 7",
            "485278910697922026, 8",
            "527702912077386, 0",
            "675931078456122, 0",
            "354369338731413, 1",
            "123, 0"
    })
    void shouldReturnCorrectCheckDigit(String input, String expected) {
        assertEquals(expected, luhnFormula.generateCheckDigit(input));
    }
}