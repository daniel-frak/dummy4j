package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import dev.codesoapbox.dummy4j.exceptions.InvalidIsmnParameterException;

import java.util.function.IntPredicate;
import java.util.regex.Pattern;

/**
 * The reason for this class is to provide basic validation for ISMN parts
 *
 * @since 0.9.0
 */
public class IsmnValidator {

    private static final Pattern INVALID_CHAR_PATTERN = Pattern.compile("[^\\d]");
    private static final String INVALID_CHAR_MESSAGE = "Only digits allowed";

    private static final Integer REGISTRANT_MIN_LENGTH = 1;
    private static final Integer REGISTRANT_MAX_LENGTH = 7;
    private static final IntPredicate INVALID_REGISTRANT_LENGTH =
            p -> p < REGISTRANT_MIN_LENGTH || p > REGISTRANT_MAX_LENGTH;
    private static final String INVALID_REGISTRANT_LENGTH_MESSAGE = String.format(
            "Registrant must be %d-%d digits long", REGISTRANT_MIN_LENGTH, REGISTRANT_MAX_LENGTH);

    void testForInvalidRegistrant(String registrant) {
        if (INVALID_REGISTRANT_LENGTH.test(registrant.length())) {
            throw new InvalidIsmnParameterException(getMessage(INVALID_REGISTRANT_LENGTH_MESSAGE, registrant));
        }
        if (INVALID_CHAR_PATTERN.matcher(registrant).find()) {
            throw new InvalidIsmnParameterException(getMessage(INVALID_CHAR_MESSAGE, registrant));
        }
    }

    private String getMessage(String reason, String givenValue) {
        return reason + ", given: " + givenValue;
    }
}
