package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.exceptions.UrlCouldNotBeCreatedException;

import java.net.URL;

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
     * Returns a URL instance
     *
     * @throws UrlCouldNotBeCreatedException if the url can't be created
     * @see URL
     */
    public URL url() {
        return new UrlBuilder(dummy4j).build();
    }

    /**
     * Provides a builder for random urls generated according to customisable parameters
     * <p>
     * E.g. {@code urlBuilder().withPort(80).withQueryParams().minLength(70).build()}
     */
    public UrlBuilder urlBuilder() {
        return new UrlBuilder(dummy4j);
    }

    /**
     * Provides a random password
     */
    public String password() {
        return new PasswordBuilder(dummy4j).build();
    }

    /**
     * Provides a builder for random passwords generated according to customisable parameters
     * <p>
     * E.g. {@code passwordBuilder().withDigits().withUpperCaseChars().withMinLength(15).build()}
     */
    public PasswordBuilder passwordBuilder() {
        return new PasswordBuilder(dummy4j);
    }

    /**
     * Provides a random email
     */
    public String email() {
        return new EmailBuilder(dummy4j).build();
    }

    /**
     * Provides a builder for random emails generated according to customisable parameters
     * <p>
     * E.g. {@code emailBuilder().withSubAddresses("custom-tag").safe().build();} may generate an email similar to
     * {@code zoe.anderson+custom-tag@example.com}
     */
    public EmailBuilder emailBuilder() {
        return new EmailBuilder(dummy4j);
    }
}
