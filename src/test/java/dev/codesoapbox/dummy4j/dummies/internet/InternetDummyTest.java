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

import java.net.URL;

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
    private NumberService numberService;

    private InternetDummy internetDummy;

    @BeforeEach
    void setUp() {
        internetDummy = new InternetDummy(dummy4j);
    }

    @Test
    void shouldReturnDefaultUrl() {
        mockSimpleUrl();
        URL actual = internetDummy.url();

        assertEquals("https://www.test.dev", actual.toString());
    }

    @Test
    void shouldBuildSimpleUrl() {
        mockSimpleUrl();
        URL actual = internetDummy.urlBuilder()
                .build();

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
        String actual = internetDummy.passwordBuilder()
                .build();

        assertAll(
                () -> assertNotNull(actual, "Value is null"),
                () -> assertEquals("passwordpass", actual, "Invalid value"),
                () -> assertEquals(12, actual.length(), "Invalid length")
        );
    }
}