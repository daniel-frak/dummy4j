package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.LoremDummy;
import dev.codesoapbox.dummy4j.dummies.NameDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternetDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private LoremDummy loremDummy;

    @Mock
    private NameDummy nameDummy;

    @Mock
    private NumberService numberService;

    private InternetDummy internetDummy;

    @BeforeEach
    void setUp() {
        internetDummy = new InternetDummy(dummy4j);
    }

    @Test
    void shouldReturnDefaultUrl() {
        mockSimpleUrl();
        when(dummy4j.oneOf(singletonList(UrlProtocol.HTTPS)))
                .thenReturn(UrlProtocol.HTTPS);

        URL actual = internetDummy.url();

        assertEquals("https://www.test.dev", actual.toString());
    }

    @Test
    void shouldBuildSimpleUrl() {
        mockSimpleUrl();
        when(dummy4j.oneOf(singletonList(UrlProtocol.HTTPS)))
                .thenReturn(UrlProtocol.HTTPS);

        URL actual = internetDummy.urlBuilder().build();

        assertAll(
                () -> assertEquals("https://www.test.dev", actual.toString()),
                () -> assertNotNull(actual.toURI())
        );
    }

    private void mockSimpleUrl() {
        mockExpressionResolver();
        when(expressionResolver.resolve(UrlBuilder.DEFAULT_TOP_LEVEL_DOMAIN_KEY))
                .thenReturn("dev");
        when(expressionResolver.resolve(UrlBuilder.ROOT_DOMAIN_KEY))
                .thenReturn("test");
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void shouldReturnDefaultPassword() {
        mockSimplePassword();

        String actual = internetDummy.password();

        assertAll(
                () -> assertNotNull(actual, "Value is null"),
                () -> assertEquals("passwordpass", actual, "Invalid value"),
                () -> assertEquals(12, actual.length(), "Invalid length")
        );
    }

    private void mockSimplePassword() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(12, 12))
                .thenReturn(12);
        when(dummy4j.lorem())
                .thenReturn(loremDummy);
        when(loremDummy.characters(12))
                .thenReturn("passwordpass");
    }

    @Test
    void shouldBuildSimplePassword() {
        mockSimplePassword();

        String actual = internetDummy.passwordBuilder().build();

        assertAll(
                () -> assertNotNull(actual, "Value is null"),
                () -> assertEquals("passwordpass", actual, "Invalid value"),
                () -> assertEquals(12, actual.length(), "Invalid length")
        );
    }

    @Test
    void shouldReturnDefaultEmail() {
        mockSimpleEmail();
        String actual = internetDummy.email();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("zoe.anderson@gmail.com", actual)
        );
    }

    private void mockSimpleEmail() {
        mockProvider();
        mockUsername();
    }

    private void mockProvider() {
        mockExpressionResolver();
        when(expressionResolver.resolve(EmailBuilder.DOMAIN_KEY))
                .thenReturn("gmail.com");
    }

    private void mockUsername() {
        when(dummy4j.name())
                .thenReturn(nameDummy);
        when(nameDummy.firstName())
                .thenReturn("Zoé");
        when(nameDummy.lastName())
                .thenReturn("Ànderson");
    }

    @Test
    void shouldBuildEmail() {
        mockSimpleEmail();
        String actual = internetDummy.emailBuilder()
                .withSubAddresses("tag")
                .build();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("zoe.anderson+tag@gmail.com", actual)
        );
    }

    @Test
    void shouldReturnUsername() {
        mockExpressionResolver();
        mockUsername();
        when(expressionResolver.resolve(InternetDummy.USERNAME_SEPARATOR_KEY))
                .thenReturn("-");

        String actual = internetDummy.username();

        assertEquals("zoe-anderson", actual);
    }
}