package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.serialnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.CheckDigit10AsXFormatter;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModElevenFormula;

/**
 * The reason for this class is to provide ISSN numbers
 *
 * @since 0.9.0
 */
public class IssnFactory {

    static final String ISSN_SEPARATOR = "-";

    final Dummy4j dummy4j;
    final ModElevenFormula modElevenFormula;

    IssnFactory(Dummy4j dummy4j, ModElevenFormula modElevenFormula) {
        this.dummy4j = dummy4j;
        this.modElevenFormula = modElevenFormula;
    }

    public static IssnFactory newInstance(Dummy4j dummy4j, ModElevenFormula modElevenFormula) {
        return new IssnFactory(dummy4j, modElevenFormula);
    }

    /**
     * Returns a random ISSN
     */
    public String createIssn() {
        String firstPart = dummy4j.number().digits(4) + ISSN_SEPARATOR + dummy4j.number().digits(3);
        String checkDigit = getIssnCheckDigit(firstPart);

        return firstPart + checkDigit;
    }

    private String getIssnCheckDigit(String firstPart) {
        Integer checkDigit = modElevenFormula.generateCheckDigit(firstPart);

        return CheckDigit10AsXFormatter.formatCheckDigit(checkDigit);
    }
}
