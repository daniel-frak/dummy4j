package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating addresses
 */
public class AddressDummy {

    private final Dummy4j dummy4j;

    public AddressDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Generates a random city name
     */
    public String city() {
        return dummy4j.expressionResolver().resolve("#{address.city}");
    }

    /**
     * Generates a random post code
     */
    public String postCode() {
        return dummy4j.expressionResolver().resolve("#{address.postcode}");
    }

    /**
     * Generates a random street name
     */
    public String street() {
        return dummy4j.expressionResolver().resolve("#{address.street}");
    }

    /**
     * Provides a random country name
     */
    public String country() {
        return dummy4j.expressionResolver().resolve("#{nation.country}");
    }

    /**
     * Provides a random country code
     */
    public String countryCode() {
        return dummy4j.expressionResolver().resolve("#{nation.country_code}");
    }
}
