package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.string.StringFormatter;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides methods for generating random IBANs according to customizable parameters
 *
 * @since 0.6.0
 */
public class IbanBuilder {

    private static final String SEPARATOR = " ";
    private static final int NUMBER_OF_CHARS_PER_GROUP = 4;

    private final Dummy4j dummy4j;
    private final IbanFormula ibanFormula;

    private List<BankAccountCountry> countries = emptyList();
    private boolean formatted;

    public IbanBuilder(Dummy4j dummy4j, IbanFormula ibanFormula) {
        this.dummy4j = dummy4j;
        this.ibanFormula = ibanFormula;
    }

    /**
     * Sets a random country for the generated IBAN.
     * <p>
     * This is the default behavior for this builder.
     *
     * @see BankAccountCountry
     * @since 0.7.0
     */
    public IbanBuilder withRandomCountry() {
        this.countries = emptyList();

        return this;
    }

    /**
     * Sets the country for the generated IBAN to one that is randomly chosen from provided arguments
     *
     * @see BankAccountCountry
     */
    public IbanBuilder withRandomCountry(BankAccountCountry... countries) {
        this.countries = asList(countries);

        return this;
    }

    /**
     * Sets the IBAN country
     *
     * @see BankAccountCountry
     */
    public IbanBuilder withCountry(BankAccountCountry country) {
        countries = singletonList(country);

        return this;
    }

    /**
     * Formats the generated IBAN, separating every 4 characters or the remainder with a space.
     * E.g. {@code GL25 3015 7608 5103 56}.
     */
    public IbanBuilder formatted() {
        formatted = true;

        return this;
    }

    /**
     * Generates a random IBAN
     */
    public String build() {
        BankAccountCountry country = Optional.ofNullable(dummy4j.oneOf(countries))
                .orElseGet(() -> dummy4j.nextEnum(BankAccountCountry.class));
        String account = dummy4j.finance().bankAccountNumber(country);
        String countryCode = country.getCode();
        String iban = countryCode + ibanFormula.generateCheckDigits(account, countryCode) + account;

        return format(iban);
    }

    private String format(String iban) {
        if (!formatted) {
            return iban;
        }

        return StringFormatter.insertEveryNthCharacter(iban, SEPARATOR, NUMBER_OF_CHARS_PER_GROUP);
    }

    @Override
    public String toString() {
        return "IbanBuilder{" +
                "countries=" + countries +
                ", formatted=" + formatted +
                '}';
    }
}