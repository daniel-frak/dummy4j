package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.Locale;
import java.util.regex.Pattern;

public class CreditCardNumberBuilder {

    public static final String PARTIAL_CREDIT_CARD_KEY = "#{finance.credit_card_without_check_digit.";
    static final Pattern SANITIZE_NUMBER_PATTERN = Pattern.compile("[^\\d]");
    static final Pattern SANITIZE_DEFINITION_KEY_PATTERN = Pattern.compile("\\s");

    private final Dummy4j dummy4j;
    private final LuhnFormula luhnFormula;

    private CreditCardProvider provider;
    private boolean clearFormatting;

    public CreditCardNumberBuilder(Dummy4j dummy4j, LuhnFormula luhnFormula) {
        this.dummy4j = dummy4j;
        this.luhnFormula = luhnFormula;
    }

    public CreditCardNumberBuilder withRandomProvider() {
        this.provider = null;
        return this;
    }

    public CreditCardNumberBuilder withProvider(CreditCardProvider provider) {
        this.provider = provider;
        return this;
    }

    public CreditCardNumberBuilder clearFormatting() {
        clearFormatting = true;
        return this;
    }

    CreditCardProvider getProvider() {
        return provider;
    }

    public String build() {
        if (provider == null) {
            provider = dummy4j.nextEnum(CreditCardProvider.class);
        }

        String uncheckedNumber = getNumberWithIIN();
        String number = uncheckedNumber + luhnFormula.getCheckDigit(uncheckedNumber);

        if (!clearFormatting) {
            return number;
        }

        return SANITIZE_NUMBER_PATTERN.matcher(number).replaceAll("");
    }

    private String getNumberWithIIN() {
        String resolvedDefinition = dummy4j.expressionResolver().resolve(getProviderKey());

        return Replace.replaceCharactersConditionally(resolvedDefinition, getIIN(), Character::isDigit);
    }

    private String getProviderKey() {
        String providerNameInLowerCase = provider.getName().toLowerCase(Locale.ENGLISH);
        String providerKey = SANITIZE_DEFINITION_KEY_PATTERN.matcher(providerNameInLowerCase)
                .replaceAll("_");

        return PARTIAL_CREDIT_CARD_KEY + providerKey + "}";
    }

    private String getIIN() {
        IINRange range = dummy4j.of(provider.getIinRanges());

        return String.valueOf(dummy4j.number().nextInt(range.getMin(), range.getMax()));
    }

    @Override
    public String toString() {
        return "CreditCardNumberBuilder{" +
                "provider=" + provider +
                ", clearFormatting=" + clearFormatting +
                '}';
    }
}
