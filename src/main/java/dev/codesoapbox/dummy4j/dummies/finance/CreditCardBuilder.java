package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.address.Address;
import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides methods for generating random credit cards according to customizable parameters
 *
 * @since 0.6.0
 */
public class CreditCardBuilder {

    static final int MAX_DAYS_FOR_EXPIRY_DATE = 3650;
    static final int MIN_SECURITY_CODE = 100;
    static final int MAX_SECURITY_CODE = 999;

    private final Dummy4j dummy4j;
    private final CreditCardNumberBuilder numberBuilder;

    private List<CreditCardProvider> providers = emptyList();

    public CreditCardBuilder(Dummy4j dummy4j, CreditCardNumberBuilder numberBuilder) {
        this.dummy4j = dummy4j;
        this.numberBuilder = numberBuilder;
    }

    /**
     * Sets a provider for which the number will be generated to one that is chosen at random
     * from the {@code CreditCardProvider} enum.
     * <p>
     * This is the default behavior for this builder.
     */
    public CreditCardBuilder withRandomProvider() {
        providers = emptyList();

        return this;
    }

    /**
     * Sets the provider for which the number will be generated to one that is randomly chosen from provided arguments.
     * If there are no arguments, a provider is chosen at random from the {@code CreditCardProvider} enum.
     *
     * @since SNAPSHOT
     */
    public CreditCardBuilder withRandomProvider(CreditCardProvider... providers) {
        this.providers = asList(providers);

        return this;
    }

    /**
     * Sets the provider for which the credit card number will be generated
     */
    public CreditCardBuilder withProvider(CreditCardProvider provider) {
        providers = singletonList(provider);

        return this;
    }

    /**
     * Removes formatting from the generated credit card number - only digits will remain
     */
    public CreditCardBuilder withoutNumberFormatting() {
        numberBuilder.withoutFormatting();

        return this;
    }

    /**
     * Generates a random credit card
     */
    public CreditCard build() {
        CreditCardProvider provider = Optional.ofNullable(dummy4j.of(providers))
                .orElse(dummy4j.nextEnum(CreditCardProvider.class));
        String number = numberBuilder.withProvider(provider).build();
        String ownerName = dummy4j.name().firstName() + " " + dummy4j.name().lastName();
        Address ownerAddress = dummy4j.address().full();
        String expiryDate = generateExpiryDate();
        String securityCode = String.valueOf(dummy4j.number().nextInt(MIN_SECURITY_CODE, MAX_SECURITY_CODE));

        return new CreditCard(number, provider, ownerName, ownerAddress, expiryDate, securityCode);
    }

    private String generateExpiryDate() {
        LocalDate date = dummy4j.dateAndTime().future(MAX_DAYS_FOR_EXPIRY_DATE, ChronoUnit.DAYS).toLocalDate();
        int month = date.getMonthValue();
        int year = date.getYear();

        return Padding.leftPad(String.valueOf(month), 2, '0') + "/" + year;
    }

    @Override
    public String toString() {
        return "CreditCardBuilder{" +
                "numberBuilder=" + numberBuilder +
                ", providers=" + providers +
                '}';
    }
}
