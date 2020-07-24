package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
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

    private InternetDummy internetDummy;

    @BeforeEach
    void setUp() {
        internetDummy = new InternetDummy(dummy4j);
    }

    @Test
    void shouldReturnSimpleUrl() {
        mockSimpleUrl();
        URL actual = internetDummy.url()
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
}