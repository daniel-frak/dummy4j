package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;

/**
 * Provides methods for generating a random GTIN-13 (EAN-13) according to customizable parameters
 */
public class Gtin13Builder extends GtinWithGs1PrefixBuilder<Gtin13Builder> {

    private static final Integer DATA_PART_LENGTH = 9;

    public Gtin13Builder(Dummy4j dummy4j, ModTenFormula modTenFormula) {
        super(dummy4j, modTenFormula);
    }

    @Override
    protected Gtin13Builder self() {
        return this;
    }

    @Override
    public String build() {
        return createBasicGtin(DATA_PART_LENGTH);
    }
}
