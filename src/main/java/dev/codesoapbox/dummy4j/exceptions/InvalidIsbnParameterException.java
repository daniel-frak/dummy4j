package dev.codesoapbox.dummy4j.exceptions;

public class InvalidIsbnParameterException extends RuntimeException {

    public InvalidIsbnParameterException(String message) {
        super(message);
    }

    public InvalidIsbnParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
