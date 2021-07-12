package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber;

import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.LuhnFormula;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModElevenDashTwoFormula;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModElevenFormula;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;

/**
 * This class serves as a provider of check digit formulas for IdentifierDummy
 *
 * @since SNAPSHOT
 */
public class InternationalNumberCheckDigitFormulaProvider {

    private static final ModTenFormula MOD_TEN_FORMULA = new ModTenFormula();
    private static final ModElevenFormula MOD_ELEVEN_FORMULA = new ModElevenFormula();
    private static final ModElevenDashTwoFormula MOD_ELEVEN_DASH_TWO_FORMULA = new ModElevenDashTwoFormula();
    private static final LuhnFormula LUHN_FORMULA = new LuhnFormula();

    public ModTenFormula getModTenFormula() {
        return MOD_TEN_FORMULA;
    }

    public ModElevenFormula getModElevenFormula() {
        return MOD_ELEVEN_FORMULA;
    }

    public ModElevenDashTwoFormula getModElevenDashTwoFormula() {
        return MOD_ELEVEN_DASH_TWO_FORMULA;
    }

    public LuhnFormula getLuhnFormula() {
        return LUHN_FORMULA;
    }
}
