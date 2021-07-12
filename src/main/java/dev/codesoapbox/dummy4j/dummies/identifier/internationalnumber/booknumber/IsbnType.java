package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

/**
 * Enum containing the supported types of ISBN
 *
 * @since 0.9.0
 */
public enum IsbnType {
    ISBN_10(false),
    ISBN_13(true);

    private final boolean requiresPrefix;

    IsbnType(boolean requiresPrefix) {
        this.requiresPrefix = requiresPrefix;
    }

    public boolean requiresPrefix() {
        return requiresPrefix;
    }
}
