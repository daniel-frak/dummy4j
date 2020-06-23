package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

public class AddressDummy {

    private final Dummy4j dummy4j;

    public AddressDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String city() {
        return dummy4j.getExpressionResolver().resolve("#{address.city}");
    }

    public String postCode() {
        return dummy4j.getExpressionResolver().resolve("#{address.postcode}");
    }

    public String street() {
        return dummy4j.getExpressionResolver().resolve("#{address.street}");
    }

    public String country() {
        return dummy4j.getExpressionResolver().resolve("#{nation.country}");
    }

    public String countryCode() {
        return dummy4j.getExpressionResolver().resolve("#{nation.country_code}");
    }
}
