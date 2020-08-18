package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating values related to the internet
 *
 * @since 0.5.0
 */
public class InternetDummy {

    private final Dummy4j dummy4j;

    public InternetDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Provides a builder for random urls generated according to customisable parameters
     * <p>
     * E.g. {@code url().withPort(80).withQueryParams().minLength(70).build()}
     */
    public UrlBuilder url() {
        return new UrlBuilder(dummy4j);
    }

    /**
     * Provides a builder for random passwords generated according to customisable parameters
     * <p>
     * E.g. {@code password().withDigits().withUpperCaseChars().withMinLength(15).build()}
     */
    public PasswordBuilder password() {
        return new PasswordBuilder(dummy4j);
    }
}
