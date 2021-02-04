package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitionsMap;
import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultExpressionResolverTest {

    @Mock
    private DefinitionProvider definitionProvider;

    @Mock
    private RandomService randomService;

    private DefaultExpressionResolver expressionResolver;

    @BeforeEach
    void setUp() {
        String locale = "en";

        LocalizedDummyDefinitions dummyDefinitions = new LocalizedDummyDefinitionsMap(locale, buildDefinitionMap());
        when(definitionProvider.get())
                .thenReturn(singletonList(dummyDefinitions));

        expressionResolver = new DefaultExpressionResolver(singletonList(locale), randomService, definitionProvider);
    }

    private Map<String, Object> buildDefinitionMap() {
        Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("deep", "value");
        nestedMap.put("advanced", "#{something.deep}123");
        nestedMap.put("empty", emptyList());
        nestedMap.put("special", "$ $$ #{something.special_nested}");
        nestedMap.put("special_nested", "$ $$ \\abc");

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("something", nestedMap);

        rootMap.put("test_number", 1);

        return rootMap;
    }

    @Test
    void shouldResolveKey() {
        String result = expressionResolver.resolve("#{something.deep}");
        assertEquals("value", result);
    }

    @Test
    void shouldReturnEmptyStringWhenUnableToResolveKey() {
        LocalizedDummyDefinitions dummyDefinitions = mock(LocalizedDummyDefinitions.class);
        when(definitionProvider.get())
                .thenReturn(singletonList(dummyDefinitions));
        when(dummyDefinitions.resolve(any()))
                .thenReturn(null);
        when(dummyDefinitions.getLocale())
                .thenReturn("en");
        expressionResolver = new DefaultExpressionResolver(singletonList("en"), randomService, definitionProvider);
        String result = expressionResolver.resolve("#{something.notexisting}");
        assertEquals("", result);
    }

    @Test
    void shouldReturnEmptyStringWhenKeyIsEmpty() {
        String result = expressionResolver.resolve("#{something.empty}");
        assertEquals("", result);
    }

    @Test
    void shouldResolveExpression() {
        String result = expressionResolver.resolve("#{something.deep}-#{something.deep}");
        assertEquals("value-value", result);
    }

    @Test
    void shouldResolveExpressionWithDigits() {
        when(randomService.nextInt(9))
                .thenReturn(9, 1, 2, 3, 4, 5);
        String result = expressionResolver.resolve("#-##-###");
        assertEquals("9-12-345", result);
    }

    @CsvSource({
            "\\#, #",
            "\\#-\\##-\\#\\##, #-#9-##9"
    })
    @ParameterizedTest
    void shouldNotResolveEscapedHashSymbol(String input, String output) {
        lenient().when(randomService.nextInt(9))
                .thenReturn(9);
        String result = expressionResolver.resolve(input);
        assertEquals(output, result);
    }

    @Test
    void shouldResolveExpressionWithinExpression() {
        String result = expressionResolver.resolve("#{something.advanced}");
        assertEquals("value123", result);
    }

    @Test
    void shouldResolveSpecialChars() {
        String result = expressionResolver.resolve("#{something.special}");
        assertEquals("$ $$ $ $$ \\abc", result);
    }

    @Test
    void shouldResolveNumber() {
        String result = expressionResolver.resolve("#{test_number}");
        assertEquals("1", result);
    }
}