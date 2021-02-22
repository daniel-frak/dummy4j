package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides methods for generating random credit card numbers according to customizable parameters
 *
 * @since 0.6.0
 */
public class CreditCardNumberBuilder {

    static final String PARTIAL_CREDIT_CARD_KEY = "#{finance.credit_card_without_check_digit.";
    static final Pattern SANITIZE_NUMBER_PATTERN = Pattern.compile("[^\\d]");

    /**
     * The pattern uses Unicode properties to cover more cases.
     *
     * @see <a href="https://stackoverflow.com/questions/56653323/s-doesnt-actually-capture-all-whitespace-characters/
     * 56654027#56654027">Capture all whitespace characters (StackOverflow)</a>
     */
    static final Pattern SANITIZE_DEFINITION_KEY_PATTERN = Pattern.compile("\\s", Pattern.UNICODE_CHARACTER_CLASS);

    private final Dummy4j dummy4j;
    private final LuhnFormula luhnFormula;

    private boolean withoutFormatting;
    private List<CreditCardProvider> providers = emptyList();

    public CreditCardNumberBuilder(Dummy4j dummy4j, LuhnFormula luhnFormula) {
        this.dummy4j = dummy4j;
        this.luhnFormula = luhnFormula;
    }

    /**
     * Sets the provider for which the number will be generated
     */
    public CreditCardNumberBuilder withProvider(CreditCardProvider provider) {
        providers = singletonList(provider);

        return this;
    }

    /**
     * Sets a provider for which the number will be generated to one that is chosen at random
     * from the {@code CreditCardProvider} enum.
     * <p>
     * This is the default behavior for this builder.
     */
    public CreditCardNumberBuilder withRandomProvider() {
        providers = emptyList();

        return this;
    }

    /**
     * Sets the provider for which the number will be generated to one that is randomly chosen from provided arguments.
     * If there are no arguments, a provider is chosen at random from the {@code CreditCardProvider} enum.
     *
     * @since 0.7.0
     */
    public CreditCardNumberBuilder withRandomProvider(CreditCardProvider... providers) {
        this.providers = asList(providers);

        return this;
    }

    /**
     * Removes formatting from the generated credit card number - only digits will remain
     */
    public CreditCardNumberBuilder withoutFormatting() {
        withoutFormatting = true;

        return this;
    }

    /**
     * Generates a random credit card number
     */
    public String build() {
        String uncheckedNumber = getNumberWithIIN();
        String number = uncheckedNumber + luhnFormula.generateCheckDigit(uncheckedNumber);

        if (!withoutFormatting) {
            return number;
        }

        return SANITIZE_NUMBER_PATTERN.matcher(number).replaceAll("");
    }

    private String getNumberWithIIN() {
        CreditCardProvider provider = Optional.ofNullable(dummy4j.of(providers))
                .orElseGet(() -> dummy4j.nextEnum(CreditCardProvider.class));
        String resolvedDefinition = dummy4j.expressionResolver().resolve(getProviderKey(provider));

        return Replace.replaceCharactersConditionally(resolvedDefinition, getIIN(provider), Character::isDigit);
    }

    private String getProviderKey(CreditCardProvider provider) {
        String providerNameInLowerCase = provider.getName().toLowerCase(Locale.ENGLISH);
        String providerKey = SANITIZE_DEFINITION_KEY_PATTERN.matcher(providerNameInLowerCase)
                .replaceAll("_");

        return PARTIAL_CREDIT_CARD_KEY + providerKey + "}";
    }

    private String getIIN(CreditCardProvider provider) {
        IINRange range = dummy4j.of(provider.getIinRanges());

        return String.valueOf(dummy4j.number().nextInt(range.getMin(), range.getMax()));
    }

    @Override
    public String toString() {
        return "CreditCardNumberBuilder{" +
                "providers=" + providers +
                ", withoutFormatting=" + withoutFormatting +
                '}';
    }
}
