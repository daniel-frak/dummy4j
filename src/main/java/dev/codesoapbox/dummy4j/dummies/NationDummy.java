package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating values related to countries, languages and nationalities
 *
 * @since 0.2.0
 */
public class NationDummy {

    private final Dummy4j dummy4j;

    public NationDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
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

    /**
     * Provides a random nationality
     */
    public String nationality() {
        return dummy4j.expressionResolver().resolve("#{nation.nationality}");
    }

    /**
     * Provides a random language code according to the ISO 639-1 standard
     *
     * @since 0.4.0
     */
    public String languageCodeTwoLetter() {
        return dummy4j.expressionResolver().resolve("#{nation.language_code_iso_639_1}");
    }

    /**
     * Provides a random language code according to the ISO 639-2 standard
     *
     * @since 0.4.0
     */
    public String languageCodeThreeLetter() {
        return dummy4j.expressionResolver().resolve("#{nation.language_code_iso_639_2}");
    }

    /**
     * Provides a random language name
     *
     * @since 0.4.0
     */
    public String language() {
        return dummy4j.expressionResolver().resolve("#{nation.language}");
    }

    /**
     * Provides a random language name from a set of commonly used languages
     *
     * @since 0.4.0
     */
    public String languageCommon() {
        return dummy4j.expressionResolver().resolve("#{nation.common_language}");
    }

    /**
     * Provides a random language code according to the ISO 639-1 standard from a set of commonly used languages
     *
     * @since 0.4.0
     */
    public String languageCodeTwoLetterCommon() {
        return dummy4j.expressionResolver().resolve("#{nation.common_language_code_iso_639_1}");
    }

    /**
     * Provides a random language code according to the ISO 639-2 standard from a set of commonly used languages
     *
     * @since 0.4.0
     */
    public String languageCodeThreeLetterCommon() {
        return dummy4j.expressionResolver().resolve("#{nation.common_language_code_iso_639_2}");
    }
}
