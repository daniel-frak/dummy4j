package dev.codesoapbox.dummy4j.exceptions;

public class MissingLocaleException extends RuntimeException {

    private final String locale;

    public MissingLocaleException(String locale) {
        this.locale = locale;
    }

    @Override
    public String getMessage() {
        return "Missing locale: " + locale;
    }
}
