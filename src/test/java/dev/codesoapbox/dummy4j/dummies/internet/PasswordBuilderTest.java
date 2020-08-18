package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.LoremDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordBuilderTest {

    static final String DEFAULT_PASS = "passwordpass";
    static final int DEFAULT_LENGTH = 12;
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[!@#$%^&*_\\-?]+.*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d+.*");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile(".*[A-Z]+.*");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile(".*[a-z]+.*");
    private static final String DIFFERENT_THAN_EXPECTED = "Actual value is different than expected";
    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NumberService numberService;

    @Mock
    private LoremDummy loremDummy;

    @Mock
    private ExpressionResolver expressionResolver;

    private PasswordBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new PasswordBuilder(dummy4j);
    }

    @Test
    void shouldBuildDefaultPassword() {
        mockDefaultPassword();
        String actual = builder.build();

        assertEquals(DEFAULT_PASS, actual);
    }

    private void mockDefaultPassword() {
        mockNumberService();
        mockDefaultPasswordLength();
        mockLoremDummy();
        when(loremDummy.characters(DEFAULT_LENGTH))
                .thenReturn(DEFAULT_PASS);
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    private void mockDefaultPasswordLength() {
        when(dummy4j.number().nextInt(DEFAULT_LENGTH, DEFAULT_LENGTH))
                .thenReturn(DEFAULT_LENGTH);
    }

    private void mockLoremDummy() {
        when(dummy4j.lorem())
                .thenReturn(loremDummy);
    }

    @Test
    void shouldBuildPasswordWithDigits() {
        mockDefaultPassword();
        mockRandomDigit();
        String actual = builder
                .withDigits()
                .build();
        Matcher digitMatcher = DIGIT_PATTERN.matcher(actual);

        assertAll(
                () -> assertTrue(digitMatcher.find(), "Digits are missing"),
                () -> assertEquals("5a5s5o5d5a5s", actual, DIFFERENT_THAN_EXPECTED)
        );
    }

    private void mockRandomDigit() {
        when(numberService.nextInt(PasswordBuilder.DIGIT_UPPER_BOUND))
                .thenReturn(5);
    }

    @Test
    void shouldBuildPasswordWithSpecialChars() {
        mockDefaultPassword();
        mockSpecialChar();
        String actual = builder
                .withSpecialChars()
                .build();
        Matcher specialCharMatcher = SPECIAL_CHAR_PATTERN.matcher(actual);

        assertAll(
                () -> assertTrue(specialCharMatcher.find(), "Special characters are missing"),
                () -> assertEquals("?a?s?o?d?a?s", actual, DIFFERENT_THAN_EXPECTED)
        );
    }

    private void mockSpecialChar() {
        mockExpressionResolver();
        when(expressionResolver.resolve(PasswordBuilder.SPECIAL_CHAR_KEY))
                .thenReturn("?");
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void shouldBuildPasswordWithDigitsAndSpecialChars() {
        mockDefaultPassword();
        mockRandomDigit();
        mockSpecialChar();
        String actual = builder
                .withDigits()
                .withSpecialChars()
                .build();
        Matcher specialCharMatcher = SPECIAL_CHAR_PATTERN.matcher(actual);
        Matcher digitMatcher = DIGIT_PATTERN.matcher(actual);

        assertAll(
                () -> assertTrue(digitMatcher.find(), "Digits are missing"),
                () -> assertTrue(specialCharMatcher.find(), "Special characters are missing")
        );
    }

    @Test
    void shouldBuildPasswordWithUpperCaseChars() {
        mockDefaultPassword();
        mockUpperCase();
        String actual = builder
                .withUpperCaseChars()
                .build();
        Matcher upperCaseMatcher = UPPER_CASE_PATTERN.matcher(actual);

        assertAll(
                () -> assertEquals("AaAsAoAdAaAs", actual, DIFFERENT_THAN_EXPECTED),
                () -> assertTrue(upperCaseMatcher.find(), "Upper case characters are missing")
        );
    }

    private void mockUpperCase() {
        when(loremDummy.character())
                .thenReturn("A");
    }

    @Test
    void shouldBuildPasswordWithUpperCaseCharsDigitsAndSpecialChars() {
        mockDefaultPassword();
        mockUpperCase();
        mockRandomDigit();
        mockSpecialChar();
        String actual = builder
                .withUpperCaseChars()
                .withDigits()
                .withSpecialChars()
                .build();
        Matcher specialCharMatcher = SPECIAL_CHAR_PATTERN.matcher(actual);
        Matcher digitMatcher = DIGIT_PATTERN.matcher(actual);
        Matcher upperCaseMatcher = UPPER_CASE_PATTERN.matcher(actual);
        Matcher lowerCaseMatcher = LOWER_CASE_PATTERN.matcher(actual);
        assertAll(
                () -> assertTrue(digitMatcher.find(), "Digits are missing"),
                () -> assertTrue(specialCharMatcher.find(), "Special characters are missing"),
                () -> assertTrue(upperCaseMatcher.find(), "Upper case characters are missing"),
                () -> assertTrue(lowerCaseMatcher.find(), "Lower case characters are missing")
        );
    }

    @Test
    void shouldApplyConstraintOnlyOnce() {
        int maxLength = 4;
        mockNumberService();
        when(dummy4j.number().nextInt(maxLength, maxLength))
                .thenReturn(maxLength);
        mockLoremDummy();
        when(loremDummy.characters(maxLength))
                .thenReturn("aaaa");
        mockRandomDigit();
        mockSpecialChar();
        mockUpperCase();
        builder.withMaxLength(maxLength)
                .withDigits()
                .withSpecialChars()
                .withDigits()
                .withSpecialChars()
                .withUpperCaseChars()
                .withUpperCaseChars()
                .build();

        verify(expressionResolver, times(1)).resolve(PasswordBuilder.SPECIAL_CHAR_KEY);
        verify(numberService, times(1)).nextInt(PasswordBuilder.DIGIT_UPPER_BOUND);
        verify(loremDummy, times(1)).character();
    }
}