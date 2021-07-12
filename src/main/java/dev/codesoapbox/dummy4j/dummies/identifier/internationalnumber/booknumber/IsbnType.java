package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

/**
 * Enum containing the supported types of ISBN
 *
 * @since SNAPSHOT
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
