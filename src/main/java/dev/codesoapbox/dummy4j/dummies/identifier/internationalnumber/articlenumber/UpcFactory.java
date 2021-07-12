package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;
import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;

/**
 * This class serves as a provider of Universal Product Codes for IdentifierDummy.
 * <p>
 * The UPC number is also known as GTIN-12
 *
 * @see <a href="https://en.wikipedia.org/wiki/Universal_Product_Code">Universal Product Code</a>
 * @see <a href="https://www.barcode.graphics/gtin-12/">GTiN-12</a>
 * @since 0.9.0
 */
public class UpcFactory {

    static final int DATA_PART_LENGTH = 8;

    final Dummy4j dummy4j;
    final ModTenFormula modTenFormula;

    UpcFactory(Dummy4j dummy4j, ModTenFormula modTenFormula) {
        this.dummy4j = dummy4j;
        this.modTenFormula = modTenFormula;
    }

    public static UpcFactory newInstance(Dummy4j dummy4j, ModTenFormula modTenFormula) {
        return new UpcFactory(dummy4j, modTenFormula);
    }

    /**
     * Generates a random Universal Product Code
     */
    public String createUpc() {
        String gs1Prefix = getGs1Prefix();
        String dataPart = dummy4j.number().digits(DATA_PART_LENGTH);
        String firstPart = gs1Prefix + dataPart;
        Integer checkDigit = modTenFormula.generateCheckDigit(firstPart);

        return firstPart + checkDigit;
    }

    String getGs1Prefix() {
        Gs1PrefixRange range = Gs1PrefixRange.UPCA_COMPATIBLE;
        Integer prefix = dummy4j.number().nextInt(range.getMin(), range.getMax());

        return Padding.leftPad(String.valueOf(prefix), 3, '0');
    }
}
