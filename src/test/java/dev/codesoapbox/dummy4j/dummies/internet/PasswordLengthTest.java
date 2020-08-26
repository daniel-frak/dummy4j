package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.LoremDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordLengthTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NumberService numberService;

    @Mock
    private LoremDummy loremDummy;

    private PasswordBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new PasswordBuilder(dummy4j);
    }

    @Test
    void shouldBuildPasswordWithLength() {
        int length = 15;
        mockPassword(length, length, length, PasswordBuilderTest.DEFAULT_PASS + "qwe");
        String actual = builder
                .withLength(length)
                .build();

        assertEquals(length, actual.length());
    }

    private void mockPassword(int minLength, int maxLength, int actualLength, String expected) {
        mockLoremDummy();
        mockNumberService();
        mockPasswordLength(minLength, maxLength, actualLength);
        when(loremDummy.characters(actualLength))
                .thenReturn(expected);
    }

    private void mockLoremDummy() {
        when(dummy4j.lorem())
                .thenReturn(loremDummy);
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    private void mockPasswordLength(int min, int max, int actual) {
        when(dummy4j.number().nextInt(min, max))
                .thenReturn(actual);
    }

    @Test
    void shouldBuildPasswordBetweenMinAndDefaultMaxLength() {
        int min = 4;
        mockPassword(min, PasswordBuilderTest.DEFAULT_LENGTH, 8, "password");
        String actual = builder
                .withMinLength(min)
                .build();

        assertEquals(8, actual.length());
    }

    @Test
    void shouldBuildPasswordBetweenDefaultMinAndMaxLength() {
        int max = 20;
        mockPassword(PasswordBuilderTest.DEFAULT_LENGTH, max, 16, "passwordpassword");
        String actual = builder
                .withMaxLength(max)
                .build();

        assertEquals(16, actual.length());
    }

    @Test
    void shouldBuildPasswordBetweenMinAndMaxLength() {
        int min = 4;
        int max = 20;
        mockPassword(min, max, 8, "password");
        String actual = builder
                .withMinLength(min)
                .withMaxLength(max)
                .build();

        assertEquals(8, actual.length());
    }

    @Test
    void shouldBuildPasswordWithMinLengthWhenItIsGreaterThanDefaultMaxLength() {
        int min = 16;
        mockPassword(min, min, min, PasswordBuilderTest.DEFAULT_PASS + "qwer");
        String actual = builder
                .withMinLength(min)
                .build();

        assertEquals(min, actual.length());
    }

    @Test
    void shouldBuildPasswordWithMaxLengthWhenItIsSmallerThanDefaultMinLength() {
        int max = 8;
        mockPassword(max, max, max, "password");
        String actual = builder
                .withMaxLength(max)
                .build();

        assertEquals(max, actual.length());
    }

    @Test
    void shouldBuildPasswordWithMinLengthWhenItIsGreaterThanLength() {
        int min = 12;
        int length = 9;
        mockPassword(min, min, min, PasswordBuilderTest.DEFAULT_PASS);
        String actual = builder
                .withLength(length)
                .withMinLength(min)
                .build();

        assertEquals(min, actual.length());
    }

    @Test
    void shouldBuildPasswordWithMaxLengthWhenItIsSmallerThanLength() {
        mockLoremDummy();
        mockNumberService();
        int max = 8;
        int length = 9;
        mockPasswordLength(max, max, max);
        when(loremDummy.characters(max))
                .thenReturn("password");
        String actual = builder
                .withLength(length)
                .withMaxLength(max)
                .build();

        assertEquals(max, actual.length());
    }

    @Test
    void shouldBuildPasswordBetweenMinLengthAndLength() {
        int minLength = 9;
        int length = 13;
        mockPassword(minLength, length, 10, "passwordpa");
        String actual = builder
                .withLength(length)
                .withMinLength(minLength)
                .build();

        assertEquals(10, actual.length());
    }

    @Test
    void shouldBuildPasswordBetweenLengthAndMaxLength() {
        int max = 20;
        int length = 13;
        mockPassword(length, max, 16, "passwordpassword");
        String actual = builder
                .withLength(length)
                .withMaxLength(max)
                .build();

        assertEquals(16, actual.length());
    }

    @Test
    void shouldOverrideMinAndMaxLengthWithLength() {
        int length = 12;
        mockPassword(length, length, length, PasswordBuilderTest.DEFAULT_PASS);
        String actual = builder
                .withMinLength(4)
                .withMaxLength(5)
                .withLength(length)
                .build();

        assertEquals(length, actual.length());
    }

    @Test
    void shouldThrowExceptionWhenLengthCantSatisfyConstraints() {
        PasswordBuilder withOneConstraint = builder
                .withSpecialChars()
                .withLength(1);
        assertThrows(IllegalArgumentException.class, withOneConstraint::build);

        PasswordBuilder withTwoConstraints = builder
                .withSpecialChars()
                .withDigits()
                .withLength(2);
        assertThrows(IllegalArgumentException.class, withTwoConstraints::build);

        PasswordBuilder withThreeConstraints = builder
                .withSpecialChars()
                .withDigits()
                .withUpperCaseChars()
                .withLength(3);
        assertThrows(IllegalArgumentException.class, withThreeConstraints::build);
    }
}