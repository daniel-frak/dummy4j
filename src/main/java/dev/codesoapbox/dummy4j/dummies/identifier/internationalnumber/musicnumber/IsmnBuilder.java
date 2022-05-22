package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;
import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;
import dev.codesoapbox.dummy4j.exceptions.InvalidIsmnParameterException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides methods for generating a random ISMN according to customizable parameters
 *
 * @since 0.9.0
 */
public class IsmnBuilder {

    static final String ISMN_SEPARATOR_KEY = "#{identifier.ismn.separator}";

    /**
     * @see <a href="https://www.ismn-international.org/files/Web_ISMN_Users_Manual_2016.pdf">
     * ISMN Users's Manual, Chapter 2.2: The number of digits in each element and how to recognize them in an ISMN</a>
     */
    static final List<IsmnRegistrantRange> REGISTRANT_RANGES = Arrays.asList(
            IsmnRegistrantRange.from(0, 99),
            IsmnRegistrantRange.from(1_000, 3_999),
            IsmnRegistrantRange.from(40_000, 69_999),
            IsmnRegistrantRange.from(700_000, 899_999),
            IsmnRegistrantRange.from(9_000_000, 9_999_999)
    );

    private static final Integer ISMN_BASE_LENGTH = 8;

    private final Dummy4j dummy4j;
    private final ModTenFormula modTenFormula;
    private final IsmnValidator validator;

    private List<String> separators;
    private List<String> registrants;

    public IsmnBuilder(Dummy4j dummy4j, ModTenFormula modTenFormula, IsmnValidator ismnValidator) {
        this.dummy4j = dummy4j;
        this.modTenFormula = modTenFormula;
        this.validator = ismnValidator;
    }

    /**
     * Sets a random registrant for the generated ISMN.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsmnBuilder withRandomRegistrant() {
        this.registrants = emptyList();

        return this;
    }

    /**
     * Sets the registrant for the generated ISMN to one that is randomly chosen from provided arguments.
     *
     * @throws InvalidIsmnParameterException when the provided arguments fail validation
     */
    public IsmnBuilder withRandomRegistrant(String... registrants) {
        Arrays.stream(registrants)
                .forEach(validator::testForInvalidRegistrant);
        this.registrants = asList(registrants);

        return this;
    }

    /**
     * Sets the registrant for the generated ISMN.
     *
     * @throws InvalidIsmnParameterException when the provided argument fails validation
     */
    public IsmnBuilder withRegistrant(String registrant) {
        validator.testForInvalidRegistrant(registrant);
        this.registrants = singletonList(registrant);

        return this;
    }

    /**
     * Sets a separator for the generated ISMN to one that is randomly chosen from the yml definitions.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsmnBuilder withRandomSeparator() {
        this.separators = emptyList();

        return this;
    }

    /**
     * Sets the separator for the generated ISMN to one that is randomly chosen from provided arguments
     */
    public IsmnBuilder withRandomSeparator(String... separators) {
        this.separators = asList(separators);

        return this;
    }

    /**
     * Sets the separator used for formatting ISMN
     */
    public IsmnBuilder withSeparator(String separator) {
        this.separators = singletonList(separator);

        return this;
    }

    /**
     * The generated ISMN will contain only digits and no separator
     */
    public IsmnBuilder withoutSeparator() {
        this.separators = singletonList("");

        return this;
    }

    /**
     * Generates a random ISMN
     */
    public Ismn build() {
        String registrant = resolveRegistrant();
        String item = dummy4j.number().digits(ISMN_BASE_LENGTH - registrant.length());
        String checkDigit = String.valueOf(modTenFormula.generateCheckDigit(Ismn.GS1_MUSICLAND +
                Ismn.TENTH_CAPACITY_PREFIX + registrant + item));
        String separator = resolveSeparator();

        return new Ismn(registrant, item, checkDigit, separator);
    }

    private String resolveRegistrant() {
        return Optional.ofNullable(dummy4j.oneOf(registrants))
                .orElseGet(this::generateRegistrant);
    }

    private String generateRegistrant() {
        IsmnRegistrantRange range = dummy4j.oneOf(REGISTRANT_RANGES);
        Integer registrant = dummy4j.number().nextInt(range.getMin(), range.getMax());

        return Padding.leftPad(String.valueOf(registrant), 3, '0');
    }

    private String resolveSeparator() {
        return Optional.ofNullable(dummy4j.oneOf(separators))
                .orElseGet(() -> dummy4j.expressionResolver().resolve(ISMN_SEPARATOR_KEY));
    }
}
