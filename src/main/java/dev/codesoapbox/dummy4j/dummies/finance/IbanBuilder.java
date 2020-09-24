package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static java.util.Collections.singletonList;

/**
 * Provides methods for generating a customized IBAN
 */
public final class IbanBuilder {

    private final Dummy4j dummy4j;
    private final IbanFormula ibanFormula;

    private List<CountrySupportingBankAccount> countries = Collections.emptyList();
    private boolean format;

    public IbanBuilder(Dummy4j dummy4j, IbanFormula ibanFormula) {
        this.dummy4j = dummy4j;
        this.ibanFormula = ibanFormula;
    }

    /**
     * Sets the country for the generated IBAN to one that is randomly chosen from provided arguments.
     * If there are no arguments, a country is chosen at random from the {@code CountrySupportingBankAccount} enum.
     */
    public IbanBuilder withRandomCountry(CountrySupportingBankAccount... countries) {
        this.countries = Arrays.asList(countries);
        return this;
    }

    /**
     * Sets the IBAN country
     */
    public IbanBuilder withCountry(CountrySupportingBankAccount country) {
        countries = singletonList(country);
        return this;
    }

    /**
     * Formats the generated IBAN, separating every 4 characters or the remainder with a space.
     * E.g. {@code GL25 3015 7608 5103 56}.
     */
    public IbanBuilder format() {
        format = true;
        return this;
    }

    /**
     * Returns a randomly generated IBAN
     */
    public String build() {
        CountrySupportingBankAccount country = getCountry();
        String account = dummy4j.finance().bankAccountNumber(country);
        String countryCode = country.getCode();
        String iban = countryCode + ibanFormula.getCheckDigits(account, countryCode) + account;

        return format(iban);
    }

    private CountrySupportingBankAccount getCountry() {
        if (countries.isEmpty()) {
            return dummy4j.nextEnum(CountrySupportingBankAccount.class);
        } else if (countries.size() == 1) {
            return countries.get(0);
        } else {
            int randomIndex = dummy4j.number().nextInt(countries.size() - 1);
            return countries.get(randomIndex);
        }
    }

    private String format(String iban) {
        if (!format) {
            return iban;
        }

        return splitEveryFourCharacters(iban);
    }

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
    private String splitEveryFourCharacters(String iban) {
        String everyFourChars = "(?<=\\G.{4})";
        String[] array = iban.split(everyFourChars);
        StringJoiner joiner = new StringJoiner(" ");

        for (String e : array) {
            joiner.add(e);
        }

        return joiner.toString();
    }
}