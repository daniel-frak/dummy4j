package dev.codesoapbox.dummy4j.exceptions;

/**
 * @since SNAPSHOT
 */
public class MissingLocaleDefinitionsException extends RuntimeException {

    private final String locale;

    public MissingLocaleDefinitionsException(String locale) {
        this.locale = locale;
    }

    @Override
    public String getMessage() {
        return String.format("Could not find definitions for locale: %s. Make sure its definitions are included " +
                "in the provided paths.", locale);
    }
}
