package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides methods for generating random IBANs according to customizable parameters
 *
 * @since 0.6.0
 */
public class IbanBuilder {

    /**
     * The {@code (?<=\G.{4})} pattern explanation:
     * <p>
     * <ul>
     *   <li>
     *     {@code (?<=...)} - positive lookbehind, ensures that the given pattern will match, ending at the current
     *     position in the expression. The pattern must have a fixed width. Does not consume any characters.
     *   </li>
     *   <li>
     *     {@code \G} - takes position at the end of the previous match or the start of the string for the first match.
     *   </li>
     *   <li>
     *     {@code .{4}} - matches any character exactly 4 times.
     *   </li>
     * </ul>
     */
    private static final Pattern SPLIT_EVERY_FOUR_CHARS_PATTERN = Pattern.compile("(?<=\\G.{4})");

    private final Dummy4j dummy4j;
    private final IbanFormula ibanFormula;

    private List<BankAccountCountry> countries = emptyList();
    private boolean formatted;

    public IbanBuilder(Dummy4j dummy4j, IbanFormula ibanFormula) {
        this.dummy4j = dummy4j;
        this.ibanFormula = ibanFormula;
    }

    /**
     * Sets a random country for the generated IBAN, which is the default behavior for this builder
     *
     * @see BankAccountCountry
     * @since SNAPSHOT
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
        BankAccountCountry country = Optional.ofNullable(dummy4j.of(countries))
                .orElse(dummy4j.nextEnum(BankAccountCountry.class));
        String account = dummy4j.finance().bankAccountNumber(country);
        String countryCode = country.getCode();
        String iban = countryCode + ibanFormula.getCheckDigits(account, countryCode) + account;

        return format(iban);
    }

    private String format(String iban) {
        if (!formatted) {
            return iban;
        }

        return splitEveryFourCharacters(iban);
    }

    private String splitEveryFourCharacters(String input) {
        String[] parts = SPLIT_EVERY_FOUR_CHARS_PATTERN.split(input);
        StringJoiner joiner = new StringJoiner(" ");

        for (String p : parts) {
            joiner.add(p);
        }

        return joiner.toString();
    }

    @Override
    public String toString() {
        return "IbanBuilder{" +
                "countries=" + countries +
                ", formatted=" + formatted +
                '}';
    }
}