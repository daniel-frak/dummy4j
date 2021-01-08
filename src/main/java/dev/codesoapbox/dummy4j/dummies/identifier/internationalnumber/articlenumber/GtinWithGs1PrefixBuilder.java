package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;
import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

abstract class GtinWithGs1PrefixBuilder<T extends GtinWithGs1PrefixBuilder<T>> {

    final Dummy4j dummy4j;
    final ModTenFormula modTenFormula;

    private List<Gs1PrefixRange> ranges;

    protected GtinWithGs1PrefixBuilder(Dummy4j dummy4j, ModTenFormula modTenFormula) {
        this.dummy4j = dummy4j;
        this.modTenFormula = modTenFormula;
    }

    protected abstract T self();

    /**
     * Sets a random prefix range for the generated GTIN.
     * <p>
     * This is the default behavior for this builder.
     *
     * @see Gs1PrefixRange
     */
    public T withRandomGs1PrefixRange() {
        ranges = emptyList();

        return self();
    }

    /**
     * Sets the random prefix range for the generated GTIN to one that is randomly chosen from provided arguments
     *
     * @see Gs1PrefixRange
     */
    public T withRandomGs1PrefixRange(Gs1PrefixRange... ranges) {
        this.ranges = asList(ranges);

        return self();
    }

    /**
     * Sets the prefix range for the generated GTIN
     *
     * @see Gs1PrefixRange
     */
    public T withGs1PrefixRange(Gs1PrefixRange range) {
        ranges = singletonList(range);

        return self();
    }

    /**
     * Generates a random GTIN
     */
    public abstract String build();

    String createBasicGtin(int dataPartLength) {
        String prefix = getGs1Prefix();
        String dataPart = dummy4j.number().digits(dataPartLength);
        String checkDigit = getCheckDigit(prefix + dataPart);

        return prefix + dataPart + checkDigit;
    }

    String getGs1Prefix() {
        Gs1PrefixRange range = Optional.ofNullable(dummy4j.of(ranges))
                .orElseGet(() -> dummy4j.nextEnum(Gs1PrefixRange.class));

        if (range.isSingleValue()) {
            return Padding.leftPad(String.valueOf(range.getMin()), 3, '0');
        }
        Integer prefix = dummy4j.number().nextInt(range.getMin(), range.getMax());

        return Padding.leftPad(String.valueOf(prefix), 3, '0');
    }

    String getCheckDigit(String input) {
        return String.valueOf(modTenFormula.generateCheckDigit(input));
    }
}
