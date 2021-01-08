package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import dev.codesoapbox.dummy4j.exceptions.InvalidIsbnParameterException;

import java.util.function.IntPredicate;
import java.util.regex.Pattern;

/**
 * The reason for this class is to provide basic validation for ISBN parts
 *
 * @since SNAPSHOT
 */
public class IsbnValidator {

    private static final Pattern INVALID_CHAR_PATTERN = Pattern.compile("[^\\d]");
    private static final String INVALID_CHAR_MESSAGE = "Only digits allowed";

    private static final Integer PREFIX_LENGTH = 3;
    private static final IntPredicate INVALID_PREFIX_LENGTH = p -> p != (PREFIX_LENGTH);
    private static final String INVALID_PREFIX_MESSAGE = String.format("Prefix must be: %d digits long", PREFIX_LENGTH);

    private static final Integer GROUP_MIN_LENGTH = 1;
    private static final Integer GROUP_MAX_LENGTH = 5;
    private static final IntPredicate INVALID_GROUP_LENGTH = p -> p < GROUP_MIN_LENGTH || p > GROUP_MAX_LENGTH;
    private static final String INVALID_GROUP_LENGTH_MESSAGE =
            String.format("Registration group must be %d-%d digits long", GROUP_MIN_LENGTH, GROUP_MAX_LENGTH);

    private static final Integer OTHER_MIN_LENGTH = 1;
    private static final Integer OTHER_MAX_LENGTH = 7;
    private static final IntPredicate INVALID_OTHER_LENGTH = p -> p < OTHER_MIN_LENGTH || p > OTHER_MAX_LENGTH;
    private static final String INVALID_OTHER_MESSAGE =
            String.format("This part must be %d-%d digits long", OTHER_MIN_LENGTH, OTHER_MAX_LENGTH);

    void testForInvalidPrefix(String prefix) {
        if (INVALID_PREFIX_LENGTH.test(prefix.length())) {
            throw new InvalidIsbnParameterException(getMessage(INVALID_PREFIX_MESSAGE, prefix));
        }
        if (INVALID_CHAR_PATTERN.matcher(prefix).find()) {
            throw new InvalidIsbnParameterException(getMessage(INVALID_CHAR_MESSAGE, prefix));
        }
    }

    private String getMessage(String reason, String givenValue) {
        return reason + ", given: " + givenValue;
    }

    void testForInvalidRegistrationGroup(String group) {
        if (INVALID_GROUP_LENGTH.test(group.length())) {
            throw new InvalidIsbnParameterException(getMessage(INVALID_GROUP_LENGTH_MESSAGE, group));
        }
        if (INVALID_CHAR_PATTERN.matcher(group).find()) {
            throw new InvalidIsbnParameterException(getMessage(INVALID_CHAR_MESSAGE, group));
        }
    }

    void testForInvalidOtherPart(String part) {
        if (INVALID_OTHER_LENGTH.test(part.length())) {
            throw new InvalidIsbnParameterException(getMessage(INVALID_OTHER_MESSAGE, part));
        }
        if (INVALID_CHAR_PATTERN.matcher(part).find()) {
            throw new InvalidIsbnParameterException(getMessage(INVALID_CHAR_MESSAGE, part));
        }
    }
}
