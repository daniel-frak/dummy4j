package dev.codesoapbox.dummy4j.dummies.shared.math;

import dev.codesoapbox.dummy4j.exceptions.ValueOutOfRangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberValidatorTest {

    @ParameterizedTest
    @ValueSource(floats = {-0.001F, -1F, 1.1F, 100F})
    void shouldThrowExceptionWhenValueNotBetweenZeroAndOne(float value) {
        assertThrows(ValueOutOfRangeException.class, () -> NumberValidator.betweenZeroAndOne(value));
    }

    @Test
    void shouldThrowExceptionWhenValueNotInRange() {
        assertThrows(ValueOutOfRangeException.class,
                () -> NumberValidator.inRange(360.01F, -360F, 360F));
        assertThrows(ValueOutOfRangeException.class,
                () -> NumberValidator.inRange(-360.01F, -360F, 360F));
    }
}