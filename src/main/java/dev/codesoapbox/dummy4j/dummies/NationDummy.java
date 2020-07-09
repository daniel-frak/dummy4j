package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * @since 0.2.0
 */
public class NationDummy {

    private final Dummy4j dummy4j;

    public NationDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String country() {
        return dummy4j.expressionResolver().resolveKey("nation.country");
    }

    public String countryCode() {
        return dummy4j.expressionResolver().resolveKey("nation.country_code");
    }

    public String nationality() {
        return dummy4j.expressionResolver().resolveKey("nation.nationality");
    }

    /**
     * @since 0.4.0
     */
    public String languageCode() {
        return dummy4j.expressionResolver().resolveKey("nation.language_code");
    }

    /**
     * @since 0.4.0
     */
    public String language() {
        return dummy4j.expressionResolver().resolveKey("nation.language");
    }
}
