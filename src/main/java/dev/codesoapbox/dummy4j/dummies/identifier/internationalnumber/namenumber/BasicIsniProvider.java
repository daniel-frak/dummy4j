package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.CheckDigit10AsXFormatter;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModElevenDashTwoFormula;

/**
 * This class serves as a provider of name identifiers for IdentifierDummy
 *
 * @since 0.9.0
 */
public class BasicIsniProvider {

    static final int LENGTH = 15;

    private final Dummy4j dummy4j;
    private final ModElevenDashTwoFormula modElevenDashTwoFormula;

    public BasicIsniProvider(Dummy4j dummy4j, ModElevenDashTwoFormula modElevenDashTwoFormula) {
        this.dummy4j = dummy4j;
        this.modElevenDashTwoFormula = modElevenDashTwoFormula;
    }

    String provide() {
        String withoutCheckDigit = dummy4j.number().digits(LENGTH);

        return withoutCheckDigit + getCheckDigit(withoutCheckDigit);
    }

    private String getCheckDigit(String input) {
        Integer checkDigit = modElevenDashTwoFormula.generateCheckDigit(input);

        return CheckDigit10AsXFormatter.formatCheckDigit(checkDigit);
    }
}
