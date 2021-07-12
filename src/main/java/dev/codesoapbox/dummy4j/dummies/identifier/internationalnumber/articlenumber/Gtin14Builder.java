package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;

/**
 * Provides methods for generating a random GTIN-14 (EAN-14) according to customizable parameters
 */
public class Gtin14Builder extends GtinWithGs1PrefixBuilder<Gtin14Builder> {

    private static final Integer PACKAGING_INDICATOR_LENGTH = 1;
    private static final Integer DATA_PART_LENGTH = 9;
    private static final String APPLICATION_ID = "(01)";

    private boolean withApplicationId;

    public Gtin14Builder(Dummy4j dummy4j, ModTenFormula modTenFormula) {
        super(dummy4j, modTenFormula);
    }

    @Override
    protected Gtin14Builder self() {
        return this;
    }

    /**
     * Adds the application id {@code (01)} to the generated GTIN-14
     */
    public Gtin14Builder withApplicationIdentifier() {
        withApplicationId = true;

        return this;
    }

    /**
     * Removes the application id {@code (01)} from the generated GTIN-14
     */
    public Gtin14Builder withoutApplicationIdentifier() {
        withApplicationId = false;

        return this;
    }

    @Override
    public String build() {
        String packagingIndicator = dummy4j.number().digits(PACKAGING_INDICATOR_LENGTH);
        String gs1Prefix = getGs1Prefix();
        String productNumber = dummy4j.number().digits(DATA_PART_LENGTH);

        String firstPart = packagingIndicator + gs1Prefix + productNumber;
        String checkDigit = getCheckDigit(firstPart);
        String applicationId = withApplicationId ? APPLICATION_ID : "";

        return applicationId + firstPart + checkDigit;
    }
}
