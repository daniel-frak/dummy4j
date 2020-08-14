package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.LoremDummy;
import dev.codesoapbox.dummy4j.exceptions.UrlCouldNotBeCreatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlBuilderTest {

    private static final String FILENAME = "filename";
    private static final String FILEPATH = "/" + FILENAME + ".html";

    @Mock
    Dummy4j dummy4j;

    @Mock
    private LoremDummy loremDummy;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private NumberService numberService;

    private UrlBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new UrlBuilder(dummy4j);
    }

    @Test
    void shouldReturnSimpleUrl() {
        mockDomain();
        URL actual = builder.build();

        assertAll(
                () -> assertEquals("https://www.test.dev", actual.toString()),
                () -> assertEquals("www.test.dev", actual.getHost()),
                () -> assertEquals("https", actual.getProtocol()),
                () -> assertEquals(UrlBuilder.DEFAULT_PORT, actual.getPort()),
                () -> assertNull(actual.getQuery()),
                () -> assertTrue(actual.getFile().isEmpty()),
                () -> assertTrue(actual.getPath().isEmpty()),
                () -> assertNotNull(actual.toURI())
        );
    }

    private void mockDomain() {
        mockExpressionResolver();
        when(expressionResolver.resolve(UrlBuilder.ROOT_DOMAIN_KEY))
                .thenReturn("test");
        when(expressionResolver.resolve(UrlBuilder.DEFAULT_TOP_LEVEL_DOMAIN_KEY))
                .thenReturn("dev");
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    private void mockLoremDummy() {
        when(dummy4j.lorem())
                .thenReturn(loremDummy);
    }

    @Test
    void shouldNotAddExtraCharsWhenMinLengthSatisfied() {
        mockDomain();
        int minLength = 20;
        URL actual = builder
                .minLength(minLength)
                .build();

        assertEquals("https://www.test.dev", actual.toString());
        assertEquals(minLength, actual.toString().length());
    }

    @Test
    void shouldReturnUrlWithoutHostPrefix() {
        mockDomain();
        URL actual = builder
                .withoutWwwPrefix()
                .build();

        assertAll(
                () -> assertEquals("https://test.dev", actual.toString()),
                () -> assertEquals("test.dev", actual.getHost())
        );
    }

    @Test
    void shouldReturnUrlWithGivenPort() {
        mockDomain();
        URL actual = builder
                .withPort(12)
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev:12", actual.toString()),
                () -> assertEquals(12, actual.getPort())
        );
    }

    @Test
    void shouldReturnUrlWithRandomPort() {
        mockDomain();
        mockNumberService();
        when(numberService.nextInt(UrlBuilder.MIN_PORT, UrlBuilder.MAX_PORT))
                .thenReturn(9999);
        URL actual = builder
                .withRandomPort()
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev:9999", actual.toString()),
                () -> assertEquals(9999, actual.getPort())
        );
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    @Test
    void shouldReturnUrlWithFile() {
        mockDomain();
        mockLoremDummy();
        when(loremDummy.characters(10))
                .thenReturn(FILENAME);
        URL actual = builder
                .withFilePath()
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev/filename.html", actual.toString()),
                () -> assertEquals(FILEPATH, actual.getFile()),
                () -> assertEquals(FILEPATH, actual.getPath())
        );
    }

    @Test
    void shouldReturnUrlWithFileAndMinLength() {
        mockDomain();
        mockLoremDummy();
        when(loremDummy.characters(6))
                .thenReturn("aaaaaa");
        when(loremDummy.characters(10))
                .thenReturn(FILENAME);
        int minLength = 40;
        URL actual = builder
                .withFilePath()
                .minLength(minLength)
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev/filenameaaaaaa.html", actual.toString()),
                () -> assertEquals(minLength, actual.toString().length()),
                () -> assertEquals("/filenameaaaaaa.html", actual.getFile()),
                () -> assertEquals("/filenameaaaaaa.html", actual.getPath())
        );
    }

    @Test
    void shouldReturnUrlWithWordsInQueryParam() {
        mockDomain();
        mockQueryParam();
        when(dummy4j.chance(UrlBuilder.CHANCE_OF_PARAM_VALUE_AS_STRING, UrlBuilder.CHANCE_IN_PARAM_VALUE_AS_STRING))
                .thenReturn(true);
        URL actual = builder
                .withQueryParam()
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev?test=test", actual.toString()),
                () -> assertEquals("test=test", actual.getQuery()),
                () -> assertEquals("?test=test", actual.getFile()),
                () -> assertTrue(actual.getPath().isEmpty())
        );
    }

    @Test
    void shouldReturnUrlWithDigitsInQueryParam() {
        mockDomain();
        mockQueryParam();
        mockNumberService();
        when(dummy4j.chance(UrlBuilder.CHANCE_OF_PARAM_VALUE_AS_STRING, UrlBuilder.CHANCE_IN_PARAM_VALUE_AS_STRING))
                .thenReturn(false);
        when(numberService.nextInt())
                .thenReturn(123);
        URL actual = builder
                .withQueryParam()
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev?test=123", actual.toString()),
                () -> assertEquals("test=123", actual.getQuery())
        );
    }

    private void mockQueryParam() {
        when(expressionResolver.resolve(UrlBuilder.PARAM_KEY))
                .thenReturn("test");
        when(expressionResolver.resolve(UrlBuilder.PARAM_VALUE_KEY))
                .thenReturn("test");
    }

    @Test
    void shouldReturnUrlWithMultipleQueryParams() {
        mockDomain();
        mockQueryParam();
        when(dummy4j.chance(UrlBuilder.CHANCE_OF_PARAM_VALUE_AS_STRING, UrlBuilder.CHANCE_IN_PARAM_VALUE_AS_STRING))
                .thenReturn(true);
        URL actual = builder
                .withQueryParams(2)
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev?test=test&test=test", actual.toString()),
                () -> assertEquals("test=test&test=test", actual.getQuery())
        );
    }

    @Test
    void shouldReturnUrlWithQueryParamAndMinLength() {
        mockDomain();
        mockQueryParam();
        mockLoremDummy();
        when(dummy4j.chance(UrlBuilder.CHANCE_OF_PARAM_VALUE_AS_STRING, UrlBuilder.CHANCE_IN_PARAM_VALUE_AS_STRING))
                .thenReturn(true);
        when(loremDummy.characters(1))
                .thenReturn("a");
        int minLength = 31;
        URL actual = builder
                .withQueryParam()
                .minLength(minLength)
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev?test=testa", actual.toString()),
                () -> assertEquals("test=testa", actual.getQuery()),
                () -> assertEquals(minLength, actual.toString().length())
        );
    }

    @Test
    void shouldReturnUrlWithMultipleQueryParamsAndFile() {
        mockDomain();
        mockQueryParam();
        mockLoremDummy();
        when(dummy4j.chance(UrlBuilder.CHANCE_OF_PARAM_VALUE_AS_STRING, UrlBuilder.CHANCE_IN_PARAM_VALUE_AS_STRING))
                .thenReturn(true);
        when(loremDummy.characters(10))
                .thenReturn(FILENAME);
        URL actual = builder
                .withQueryParams(2)
                .withFilePath()
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev/filename.html?test=test&test=test", actual.toString()),
                () -> assertEquals("test=test&test=test", actual.getQuery()),
                () -> assertEquals(FILEPATH, actual.getPath())
        );
    }

    @Test
    void shouldReturnUrlWithRandomProtocolMultipleQueryParamsAndFileAndMinLength() {
        mockDomain();
        mockQueryParam();
        mockLoremDummy();
        when(dummy4j.nextEnum(UrlProtocol.class))
                .thenReturn(UrlProtocol.HTTP);
        when(dummy4j.chance(UrlBuilder.CHANCE_OF_PARAM_VALUE_AS_STRING, UrlBuilder.CHANCE_IN_PARAM_VALUE_AS_STRING))
                .thenReturn(true);
        when(loremDummy.characters(2))
                .thenReturn("aa");
        when(loremDummy.characters(10))
                .thenReturn(FILENAME);
        int minLength = 55;
        URL actual = builder
                .withRandomProtocol()
                .withQueryParams(2)
                .withFilePath()
                .minLength(minLength)
                .build();

        assertAll(
                () -> assertEquals("http://www.test.dev/filename.html?test=test&test=testaa",
                        actual.toString()),
                () -> assertEquals(minLength, actual.toString().length()),
                () -> assertEquals("test=test&test=testaa", actual.getQuery()),
                () -> assertEquals(FILEPATH, actual.getPath())
        );
    }

    @Test
    void shouldReturnUrlWithSpecifiedProtocol() {
        mockDomain();
        URL actual = builder
                .withProtocol(UrlProtocol.HTTPS)
                .build();

        assertAll(
                () -> assertEquals("https://www.test.dev", actual.toString()),
                () -> assertEquals("https", actual.getProtocol())
        );
    }

    @Test
    void shouldReturnUrlWithSpecifiedProtocolAndMinimumLength() {
        mockDomain();
        mockLoremDummy();
        when(loremDummy.characters(4))
                .thenReturn("aaaa");
        int minLength = 22;
        URL actual = builder
                .withProtocol(UrlProtocol.FTP)
                .minLength(minLength)
                .build();

        assertAll(
                () -> assertEquals("ftp://www.testaaaa.dev", actual.toString()),
                () -> assertEquals(minLength, actual.toString().length()),
                () -> assertEquals("www.testaaaa.dev", actual.getHost()),
                () -> assertEquals("ftp", actual.getProtocol())
        );
    }

    @Test
    void shouldReturnUrlWithSpecifiedProtocolWithoutWwwPrefixWithWithRandomPortQueryParamsAndFileAndMinLength() {
        mockDomain();
        mockQueryParam();
        mockNumberService();
        mockLoremDummy();
        when(numberService.nextInt(UrlBuilder.MIN_PORT, UrlBuilder.MAX_PORT))
                .thenReturn(1001);
        when(dummy4j.chance(UrlBuilder.CHANCE_OF_PARAM_VALUE_AS_STRING, UrlBuilder.CHANCE_IN_PARAM_VALUE_AS_STRING))
                .thenReturn(true);
        when(loremDummy.characters(4))
                .thenReturn("aaaa");
        when(loremDummy.characters(10))
                .thenReturn(FILENAME);
        int minLength = 48;
        URL actual = builder
                .withProtocol(UrlProtocol.FILE)
                .withoutWwwPrefix()
                .withRandomPort()
                .withQueryParam()
                .withFilePath()
                .minLength(minLength)
                .build();

        assertAll(
                () -> assertEquals("file://test.dev:1001/filename.html?test=testaaaa", actual.toString()),
                () -> assertEquals(minLength, actual.toString().length()),
                () -> assertEquals("file", actual.getProtocol()),
                () -> assertEquals("test.dev", actual.getHost()),
                () -> assertEquals(1001, actual.getPort()),
                () -> assertEquals("test=testaaaa", actual.getQuery()),
                () -> assertEquals(FILEPATH, actual.getPath())
        );
    }

    @Test
    void shouldThrowExceptionOnInvalidUrl() {
        mockDomain();
        UrlBuilder actual = builder
                .withPort(-2);

        assertThrows(UrlCouldNotBeCreatedException.class, actual::build);
    }

    @Test
    void shouldReturnUrlForAllProtocolTypes() {
        mockDomain();
        for (UrlProtocol protocol : UrlProtocol.values()) {
            URL url = builder.withProtocol(protocol).build();

            assertEquals(protocol.getValue(), url.getProtocol());
        }
    }

    @Test
    void shouldReturnUrlWithRandomProtocol() {
        mockDomain();
        when(dummy4j.nextEnum(UrlProtocol.class))
                .thenReturn(UrlProtocol.FILE);
        URL actual = builder.withRandomProtocol().build();

        assertEquals("file", actual.getProtocol());
    }

    @Test
    void shouldReturnUrlWithRandomProtocolChosenFromGivenList() {
        mockDomain();
        mockNumberService();
        when(numberService.nextInt(2))
                .thenReturn(1);
        URL actual = builder.withRandomProtocol(UrlProtocol.FILE, UrlProtocol.HTTP, UrlProtocol.HTTPS).build();

        assertEquals("http", actual.getProtocol());
    }

    @Test
    void shouldReturnUrlWithPopularDomain() {
        mockExpressionResolver();
        when(expressionResolver.resolve(UrlBuilder.ROOT_DOMAIN_KEY))
                .thenReturn("test");
        when(expressionResolver.resolve(UrlBuilder.POPULAR_TOP_LEVEL_DOMAIN_KEY))
                .thenReturn("com");
        URL actual = builder.withPopularTopLevelDomain().build();

        assertEquals("https://www.test.com", actual.toString());
    }

    @Test
    void shouldReturnUrlWithCountryDomain() {
        mockExpressionResolver();
        when(expressionResolver.resolve(UrlBuilder.ROOT_DOMAIN_KEY))
                .thenReturn("test");
        when(expressionResolver.resolve(UrlBuilder.COUNTRY_TOP_LEVEL_DOMAIN_KEY))
                .thenReturn("uk");
        URL actual = builder.withCountryTopLevelDomain().build();

        assertEquals("https://www.test.uk", actual.toString());
    }

    @Test
    void shouldReturnUrlWithGenericDomain() {
        mockExpressionResolver();
        when(expressionResolver.resolve(UrlBuilder.ROOT_DOMAIN_KEY))
                .thenReturn("test");
        when(expressionResolver.resolve(UrlBuilder.GENERIC_TOP_LEVEL_DOMAIN_KEY))
                .thenReturn("dating");
        URL actual = builder.withGenericTopLevelDomain().build();

        assertEquals("https://www.test.dating", actual.toString());
    }

    @Test
    void shouldNotAddQueryParamsWhenRequiredAmountIsZero() {
        mockDomain();
        URL actual = builder.withQueryParams(0).build();

        assertAll(
                () -> assertEquals("https://www.test.dev", actual.toString()),
                () -> assertNull(actual.getQuery())
        );
    }

    @Test
    void shouldNotAddQueryParamsWhenLatestRequiredAmountIsZero() {
        mockDomain();
        URL actual = builder.withQueryParams(2)
                .withQueryParams(0)
                .build();

        assertNull(actual.getQuery());
    }

    @Test
    void shouldNotAddQueryParamsWhenRequiredAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> builder.withQueryParams(-10));
    }

    @Test
    void shouldReturnUrlWithCustomTopLevelDomain() {
        mockExpressionResolver();
        when(expressionResolver.resolve(UrlBuilder.ROOT_DOMAIN_KEY))
                .thenReturn("test");
        URL actual = builder.withTopLevelDomain("xxx").build();

        assertEquals("https://www.test.xxx", actual.toString());
    }

    @Test
    void shouldPrintBuilderWithDefaultValuesAsString() {
        String expected = "UrlBuilder{possibleProtocols=[HTTPS], howManyParams=0, withFilePath=false, " +
                "withoutWwwPrefix=false, port=-1, domainKey='#{internet.top_level_domain}', " +
                "customTopLevelDomain='null', minLength=0}";
        assertEquals(expected, builder.toString());
    }
}