package dev.codesoapbox.dummy4j.exceptions;

public class InvalidDefinitionException extends RuntimeException {

    private final String path;
    private final Throwable reason;
    private final String resolvedValue;

    public InvalidDefinitionException(String path, Throwable reason, String resolvedValue) {
        this.path = path;
        this.reason = reason;
        this.resolvedValue = resolvedValue;
    }

    @Override
    public String getMessage() {
        return String.format("Definitions for path: %s are invalid due to the following reason: %s. " +
                "Resolved value: %s", path, reason.getMessage(), resolvedValue);
    }
}
