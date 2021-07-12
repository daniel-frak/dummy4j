package dev.codesoapbox.dummy4j.exceptions;

public class InvalidIsmnParameterException extends RuntimeException {

    public InvalidIsmnParameterException(String message) {
        super(message);
    }

    public InvalidIsmnParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
