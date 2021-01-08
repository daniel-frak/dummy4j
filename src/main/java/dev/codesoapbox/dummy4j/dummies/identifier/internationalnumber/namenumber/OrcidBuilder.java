package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber;

import dev.codesoapbox.dummy4j.dummies.shared.string.StringFormatter;

/**
 * Provides methods for generating a random ORCID according to customizable parameters
 *
 * @since SNAPSHOT
 */
public class OrcidBuilder {

    private static final int NUMBER_OF_CHARS_PER_GROUP = 4;
    private static final String DEFAULT_SEPARATOR = "-";
    private static final String DEFAULT_URL_PREFIX = "https://orcid.org/";
    private final BasicIsniProvider isniProvider;

    private String urlPrefix = "";

    public OrcidBuilder(BasicIsniProvider isniProvider) {
        this.isniProvider = isniProvider;
    }

    /**
     * Formats generated ORCID as an url.
     * E.g. {@code https://orcid.org/9784-7648-0678-9594}
     */
    public OrcidBuilder asUrl() {
        urlPrefix = DEFAULT_URL_PREFIX;

        return this;
    }

    /**
     * Formats generated ORCID as a plain value.
     * E.g. {@code 6994-0298-2935-3670}
     * <p>
     * This is the default behavior for this builder.
     */
    public OrcidBuilder asNumber() {
        urlPrefix = "";

        return this;
    }

    /**
     * Generates a random ORCID
     */
    public String build() {
        String orcid = isniProvider.provide();

        return urlPrefix + StringFormatter.insertEveryNthCharacter(orcid, DEFAULT_SEPARATOR, NUMBER_OF_CHARS_PER_GROUP);
    }
}
