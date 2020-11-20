package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.math.NumberFormatter;

/**
 * Provides methods for generating random prices according to customizable parameters
 *
 * @since SNAPSHOT
 */
public class PriceBuilder {

    static final float LOWER_BOUNDARY = 1F;
    static final float UPPER_BOUNDARY = 100F;
    static final float LOWER_BOUNDARY_FOR_HIGH_PRICE = 100F;
    static final float UPPER_BOUNDARY_FOR_HIGH_PRICE = 10000F;

    private final Dummy4j dummy4j;

    private String customCurrency;
    private boolean randomizeCurrency;
    private float min = LOWER_BOUNDARY;
    private float max = UPPER_BOUNDARY;

    public PriceBuilder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Sets the currency that will be added to the generated price.
     * E.g. {@code EUR 12.35}
     */
    public PriceBuilder withCurrency(String customCurrency) {
        this.customCurrency = customCurrency;
        randomizeCurrency = false;

        return this;
    }

    /**
     * Sets a random currency that will be added to the generated price.
     * E.g. {@code JPY 12.35}
     */
    public PriceBuilder withRandomCurrency() {
        this.customCurrency = null;
        randomizeCurrency = true;

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
        if (randomizeCurrency) {
            return dummy4j.expressionResolver().resolve(FinanceDummy.CURRENCY_CODE_KEY);
        }
        if (customCurrency != null) {
            return customCurrency;
        }

        return "";
    }

    private String getAmount() {
        return NumberFormatter.toTwoDecimals(dummy4j.number().nextFloat(min, max));
    }
}
