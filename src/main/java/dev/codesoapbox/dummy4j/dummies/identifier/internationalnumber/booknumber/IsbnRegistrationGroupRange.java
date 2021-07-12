package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import dev.codesoapbox.dummy4j.dummies.shared.math.NumberValidator;
import dev.codesoapbox.dummy4j.exceptions.InvalidIsbnParameterException;
import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;

import java.util.Objects;

final class IsbnRegistrationGroupRange {

    private static final int MAX_REGISTRATION_GROUP = 99_999;
    private static final int MIN_REGISTRATION_GROUP = 0;

    private int min;
    private int max;

    private IsbnRegistrationGroupRange() {
    }

    static IsbnRegistrationGroupRange from(int min, int max) {
        validateRange(min, max);
        IsbnRegistrationGroupRange range = new IsbnRegistrationGroupRange();
        range.min = min;
        range.max = max;

        return range;
    }

    private static void validateRange(int min, int max) {
        try {
            NumberValidator.inRange(max, MIN_REGISTRATION_GROUP, MAX_REGISTRATION_GROUP);
            NumberValidator.inRange(min, MIN_REGISTRATION_GROUP, max);
        } catch (ValueOutOfRangeException e) {
            String message = String.format("Invalid registration group range from %d to %d", min, max);
            throw new InvalidIsbnParameterException(message, e);
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IsbnRegistrationGroupRange group = (IsbnRegistrationGroupRange) o;
        return min == group.min && max == group.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "IsbnRegistrationGroupRange{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
