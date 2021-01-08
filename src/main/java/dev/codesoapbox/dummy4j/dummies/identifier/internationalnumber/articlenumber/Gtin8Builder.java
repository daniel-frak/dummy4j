package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;

/**
 * Provides methods for generating a random GTIN-8 (EAN-8) according to customizable parameters
 */
public class Gtin8Builder extends GtinWithGs1PrefixBuilder<Gtin8Builder> {

    private static final Integer DATA_PART_LENGTH = 4;

    public Gtin8Builder(Dummy4j dummy4j, ModTenFormula modTenFormula) {
        super(dummy4j, modTenFormula);
    }

    @Override
    protected Gtin8Builder self() {
        return this;
    }

    @Override
    public String build() {
        return createBasicGtin(DATA_PART_LENGTH);
    }
}
