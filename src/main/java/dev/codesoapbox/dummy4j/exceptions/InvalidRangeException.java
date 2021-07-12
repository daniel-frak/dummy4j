package dev.codesoapbox.dummy4j.exceptions;

public class InvalidRangeException extends RuntimeException {

    private final float min;
    private final float max;

    public InvalidRangeException(float min, float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String getMessage() {
        return "Given range from " + min + " to " + max + " is invalid";
    }
}
