package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.files.yaml.LocalizedDummyDefinitionsMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;
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
    void shouldResolveExpression() {
        String result = expressionResolver.resolve("#{something.deep}-#{something.deep}");
        assertEquals("value-value", result);
    }

    @Test
    void shouldResolveExpressionWithDigits() {
        when(randomService.nextInt(10))
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