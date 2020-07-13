package dev.codesoapbox.dummy4j.exceptions;

public class ValueOutOfRangeException extends RuntimeException {

    private final float value;
    private final float min;
    private final float max;

    public ValueOutOfRangeException(float value, float min, float max) {
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @Override
    public String getMessage() {
        return "Given value (" + value + ") is out of range (" + min + "-" + max + ").";
    }
}
