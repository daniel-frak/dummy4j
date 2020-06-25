package dev.codesoapbox.dummy4j.exceptions;

public class UniqueValueRetryLimitExceededException extends RuntimeException {

    private final int maxRetries;
    private final String uniquenessGroup;

    public UniqueValueRetryLimitExceededException(int maxRetries, String uniquenessGroup) {
        this.maxRetries = maxRetries;
        this.uniquenessGroup = uniquenessGroup;
    }

    @Override
    public String getMessage() {
        return "Reached the maximum limit of retries (" + maxRetries + ") for generating a unique value in the '"
                + uniquenessGroup + "' uniqueness group";
    }
}
