package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber;

import dev.codesoapbox.dummy4j.dummies.shared.string.StringFormatter;
import dev.codesoapbox.dummy4j.dummies.shared.string.StringValidator;
import dev.codesoapbox.dummy4j.exceptions.IvalidIsniParameterException;

/**
 * Provides methods for generating a random ISNI according to customizable parameters
 *
 * @since SNAPSHOT
 */
public class IsniBuilder {

    private static final String DEFAULT_SEPARATOR = " ";
    private static final String DEFAULT_URL_PREFIX = "https://isni.org/isni/";
    private static final String FORMAT_IN_URL_EXCEPTION_MESSAGE = "ISNI can't be both formatted and used in an url";
    private static final int NUMBER_OF_CHARS_PER_GROUP = 4;

    private final BasicIsniProvider isniProvider;

    private String urlPrefix = "";
    private String separator = "";

    public IsniBuilder(BasicIsniProvider isniProvider) {
        this.isniProvider = isniProvider;
    }

    /**
     * Sets the default ISNI separator for formatting.
     * <p>
     * This method can't be combined with the {@code asUrl()} method.
     *
     * @throws IvalidIsniParameterException when used with the {@code asUrl()} method
     */
    public IsniBuilder withSeparator() {
        if (!StringValidator.isNullOrEmpty(urlPrefix)) {
            throw new IvalidIsniParameterException(FORMAT_IN_URL_EXCEPTION_MESSAGE);
        }
        separator = DEFAULT_SEPARATOR;

        return this;
    }

    /**
     * Removes the ISNI separator.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsniBuilder withoutSeparator() {
        separator = "";

        return this;
    }

    /**
     * Adds the {@code https://isni.org/isni/} url to the generated number.
     * <p>
     * This method can't be combined with the {@code withSeparator()} method.
     *
     * @throws IvalidIsniParameterException when used with the {@code withSeparator()} method
     */
    public IsniBuilder asUrl() {
        if (!separator.isEmpty()) {
            throw new IvalidIsniParameterException(FORMAT_IN_URL_EXCEPTION_MESSAGE);
        }
        urlPrefix = DEFAULT_URL_PREFIX;

        return this;
    }

    /**
     * Formats generated ISNI as a plain value and not as an url.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsniBuilder asNumber() {
        urlPrefix = "";

        return this;
    }

    /**
     * Generates a random ISNI
     */
    public String build() {
        String isni = isniProvider.provide();

        if (separator.isEmpty()) {
            return urlPrefix + isni;
        }

        return StringFormatter.insertEveryNthCharacter(isni, DEFAULT_SEPARATOR, NUMBER_OF_CHARS_PER_GROUP);
    }
}