package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitionsMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpressionResolverTest {

    @Mock
    private DefinitionProvider definitionProvider;

    @Mock
    private RandomService randomService;

    private ExpressionResolver expressionResolver;

    @BeforeEach
    void setUp() {
        String locale = "en";

        LocalizedDummyDefinitions dummyDefinitions = new LocalizedDummyDefinitionsMap(locale, buildDefinitionMap());
        when(definitionProvider.get())
                .thenReturn(singletonList(dummyDefinitions));

        expressionResolver = new ExpressionResolver(singletonList(locale), randomService, definitionProvider);
    }

    private Map<String, Object> buildDefinitionMap() {
        Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("deep", "value");
        nestedMap.put("advanced", "#{something.deep}123");
        nestedMap.put("empty", emptyList());

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("something", nestedMap);

        return rootMap;
    }

    @Test
    void shouldResolveKey() {
        String result = expressionResolver.resolveKey("something.deep");
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
        expressionResolver = new ExpressionResolver(singletonList("en"), randomService, definitionProvider);
        String result = expressionResolver.resolveKey("something.notexisting");
        assertEquals("", result);
    }

    @Test
    void shouldReturnEmptyStringWhenKeyIsEmpty() {
        String result = expressionResolver.resolveKey("something.empty");
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

    @Test
    void shouldResolveExpressionWithinExpression() {
        String result = expressionResolver.resolve("#{something.advanced}");
        assertEquals("value123", result);
    }
}