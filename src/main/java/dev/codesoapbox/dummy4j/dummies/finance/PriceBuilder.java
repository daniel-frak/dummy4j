package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.math.NumberFormatter;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides methods for generating random prices according to customizable parameters
 *
 * @since 0.6.0
 */
public class PriceBuilder {

    static final float LOWER_BOUNDARY = 1F;
    static final float UPPER_BOUNDARY = 100F;
    static final float LOWER_BOUNDARY_FOR_HIGH_PRICE = 100F;
    static final float UPPER_BOUNDARY_FOR_HIGH_PRICE = 10000F;

    private final Dummy4j dummy4j;

    private boolean currencyFromDefinitions;
    private List<String> currencies = emptyList();
    private float min = LOWER_BOUNDARY;
    private float max = UPPER_BOUNDARY;

    public PriceBuilder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Sets the currency that will be added to the generated price.
     * E.g. {@code EUR 12.35}.
     * <p>
     * The currency code will be placed before the amount.
     *
     * @see <a href="https://en.wikipedia.org/wiki/ISO_4217#Position_of_ISO_4217_code_in_amounts">
     * Position of ISO 4217 code in amounts</a>
     */
    public PriceBuilder withCurrency(String customCurrency) {
        currencies = singletonList(customCurrency);
        currencyFromDefinitions = false;

        return this;
    }

    /**
     * Sets a random currency that will be added to the generated price.
     * E.g. {@code JPY 12.35}.
     * <p>
     * The currency code will be placed before the amount.
     *
     * @see <a href="https://en.wikipedia.org/wiki/ISO_4217#Position_of_ISO_4217_code_in_amounts">
     * Position of ISO 4217 code in amounts</a>
     */
    public PriceBuilder withRandomCurrency() {
        currencyFromDefinitions = true;
        currencies = emptyList();

        return this;
    }

    /**
     * Sets a currency that will be added to the generated price to one that is randomly chosen from provided arguments.
     * E.g. {@code JPY 12.35}.
     * <p>
     * The currency code will be placed before the amount.
     *
     * @see <a href="https://en.wikipedia.org/wiki/ISO_4217#Position_of_ISO_4217_code_in_amounts">
     * Position of ISO 4217 code in amounts</a>
     * @since SNAPSHOT
     */
    public PriceBuilder withRandomCurrency(String... currencies) {
        this.currencies = asList(currencies);
        currencyFromDefinitions = false;

        return this;
    }

    /**
     * The price will be generated without any currency.
     * E.g. {@code 12.35}.
     * <p>
     * This is the default behavior for this builder.
     *
     * @since SNAPSHOT
     */
    public PriceBuilder withoutCurrency() {
        currencies = emptyList();
        currencyFromDefinitions = false;

        return this;
    }

    /**
     * Sets limits on a randomly generated price from 100 to 10,000.
     * E.g. {@code 1234.56}
     */
    public PriceBuilder high() {
        this.min = LOWER_BOUNDARY_FOR_HIGH_PRICE;
        this.max = UPPER_BOUNDARY_FOR_HIGH_PRICE;

        return this;
    }

    /**
     * Sets limits on a randomly generated price from {@code min} to {@code max}.
     * E.g. {@code 1234.56}
     */
    public PriceBuilder withinRange(float min, float max) {
        this.min = min;
        this.max = max;

        return this;
    }

    /**
     * Generates a random price
     */
    public String build() {
        String currency = getCurrency();

        if (currency.isEmpty()) {
            return getAmount();
        }

        return currency + " " + getAmount();
    }

    private String getCurrency() {
        if (currencyFromDefinitions) {
            return dummy4j.expressionResolver().resolve(FinanceDummy.CURRENCY_CODE_KEY);
        }
        return Optional.ofNullable(dummy4j.of(currencies))
                .orElse("");
    }

    private String getAmount() {
        return NumberFormatter.toTwoDecimals(dummy4j.number().nextFloat(min, max));
    }
}
