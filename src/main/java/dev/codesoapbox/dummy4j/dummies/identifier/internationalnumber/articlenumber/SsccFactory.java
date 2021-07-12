package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;

/**
 * This class serves as a provider of Serial Shipping Container Codes for IdentifierDummy
 *
 * @see <a href="https://www.gs1-128.info/sscc-18/">Serial Shipping Container Code</a>
 * @since 0.9.0
 */
public class SsccFactory {

    static final Integer DATA_PART_LENGTH = 17;

    private static final String APPLICATION_ID = "(00)";

    private final Dummy4j dummy4j;
    private final ModTenFormula modTenFormula;

    public SsccFactory(Dummy4j dummy4j, ModTenFormula modTenFormula) {
        this.dummy4j = dummy4j;
        this.modTenFormula = modTenFormula;
    }

    /**
     * Generates a random Serial Shipping Container Code
     */
    public String createCode() {
        String firstPart = dummy4j.number().digits(DATA_PART_LENGTH);
        Integer checkDigit = modTenFormula.generateCheckDigit(firstPart);

        return APPLICATION_ID + firstPart + checkDigit;
    }
}
