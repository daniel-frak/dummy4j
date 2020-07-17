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
    public String languageCodeTwoLetter() {
        return dummy4j.expressionResolver().resolveKey("nation.language_code_iso_639_1");
    }

    /**
     * @since 0.4.0
     */
    public String languageCodeThreeLetter() {
        return dummy4j.expressionResolver().resolveKey("nation.language_code_iso_639_2");
    }

    /**
     * @since 0.4.0
     */
    public String language() {
        return dummy4j.expressionResolver().resolveKey("nation.language");
    }

    /**
     * @since 0.4.0
     */
    public String languageCommon() {
        return dummy4j.expressionResolver().resolveKey("nation.common_language");
    }

    /**
     * @since 0.4.0
     */
    public String languageCodeTwoLetterCommon() {
        return dummy4j.expressionResolver().resolveKey("nation.common_language_code_iso_639_1");
    }

    /**
     * @since 0.4.0
     */
    public String languageCodeThreeLetterCommon() {
        return dummy4j.expressionResolver().resolveKey("nation.common_language_code_iso_639_2");
    }
}
