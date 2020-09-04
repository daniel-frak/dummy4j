package dev.codesoapbox.dummy4j.dummies.finance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LuhnFormulaTest {

    private LuhnFormula luhnFormula;

    @BeforeEach
    void setUp() {
        luhnFormula = new LuhnFormula();
    }

    @Test
    void shouldReturnCorrectCheckDigit() {
        assertAll(
                () -> assertEquals("3", luhnFormula.getCheckDigit("455606909685229")),
                () -> assertEquals("5", luhnFormula.getCheckDigit("455673758689985")),
                () -> assertEquals("7", luhnFormula.getCheckDigit("453264032098369")),
                () -> assertEquals("8", luhnFormula.getCheckDigit("485278910697922026")),
                () -> assertEquals("0", luhnFormula.getCheckDigit("527702912077386")),
                () -> assertEquals("0", luhnFormula.getCheckDigit("675931078456122")),
                () -> assertEquals("1", luhnFormula.getCheckDigit("354369338731413")),
                () -> assertEquals("0", luhnFormula.getCheckDigit("123"))
        );
    }
}