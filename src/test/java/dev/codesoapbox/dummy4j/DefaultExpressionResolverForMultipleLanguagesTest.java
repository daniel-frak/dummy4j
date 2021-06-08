package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitionsMap;
import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultExpressionResolverForMultipleLanguagesTest {

    private static final String EN = "en";
    private static final String FR = "fr";
    private static final String FLAVOUR = "flavour";
    private static final String FRUIT = "fruit";
    private static final String SPICY = "spicy";
    private static final String MIXED = "mixed";
    private static final String MIXED_MULTI = "mixed_multi";
    private static final String MIXED_FLAVOURS = String.format("#{%s.%s} - #{%s.%s}", FLAVOUR, FRUIT, FLAVOUR, SPICY);
    private static final String MIXED_FLAVOURS_MULTI = String.format("#{{%s.%s}} - #{{%s.%s}}",
            FLAVOUR, FRUIT, FLAVOUR, SPICY);

    @Mock
    private DefinitionProvider definitionProvider;

    @Mock
    private RandomService randomService;

    private DefaultExpressionResolver expressionResolver;

    @BeforeEach
    void setUp() {
        LocalizedDummyDefinitions english = new LocalizedDummyDefinitionsMap(EN, createEnglishDefinitions());
        LocalizedDummyDefinitions french = new LocalizedDummyDefinitionsMap(FR, createFrenchDefinitions());

        when(definitionProvider.get())
                .thenReturn(asList(english, french));

        expressionResolver = new DefaultExpressionResolver(asList(EN, FR), randomService, definitionProvider);
    }

    private Map<String, Object> createEnglishDefinitions() {
        Map<String, Object> flavours = new HashMap<>();
        flavours.put(FRUIT, Arrays.asList("lemon", "raspberry"));
        flavours.put(SPICY, Arrays.asList("pumpkin spice", "cinnamon"));
        flavours.put(MIXED, EN + ": " + MIXED_FLAVOURS);
        flavours.put(MIXED_MULTI, EN + ": " + MIXED_FLAVOURS_MULTI);

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put(FLAVOUR, flavours);

        return rootMap;
    }

    private Map<String, Object> createFrenchDefinitions() {
        Map<String, Object> flavours = new HashMap<>();
        flavours.put(FRUIT, Arrays.asList("citron", "framboise"));
        flavours.put(SPICY, Arrays.asList("epices de potiron", "cannelle"));
        flavours.put(MIXED, FR + ": " + MIXED_FLAVOURS);
        flavours.put(MIXED_MULTI, FR + ": " + MIXED_FLAVOURS_MULTI);

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put(FLAVOUR, flavours);

        return rootMap;
    }

    @Test
    void shouldResolveNestedSingleLocalePlaceholdersInMultiLocaleExpressionFromAllLocales() {
        Set<String> expected = new HashSet<>();
        expected.add("en: raspberry - cinnamon");
        expected.add("fr: framboise - cannelle");

        Set<String> result = new HashSet<>();

        mockRandomServiceForFullRange();

        for (int i = 0; i < 10; i++) {
            result.add(expressionResolver.resolve("#{{" + FLAVOUR + "." + MIXED + "}}"));
        }
        assertEquals(expected, result);
    }

    @Test
    void shouldResolveNestedSingleLocalePlaceholdersInSingleLocaleExpression() {
        Set<String> expected = new HashSet<>();
        expected.add("en: raspberry - cinnamon");

        Set<String> result = new HashSet<>();

        mockRandomServiceForFullRange();

        for (int i = 0; i < 10; i++) {
            result.add(expressionResolver.resolve("#{" + FLAVOUR + "." + MIXED + "}"));
        }
        assertEquals(expected, result);
    }

    @Test
    void shouldResolveNestedMultiLocalePlaceholdersInSingleLocaleExpression() {
        Set<String> notExpected = new HashSet<>();
        notExpected.add("en: raspberry - cinnamon");

        Set<String> result = new HashSet<>();

        mockRandomServiceForFullRange();

        for (int i = 0; i < 10; i++) {
            result.add(expressionResolver.resolve("#{" + FLAVOUR + "." + MIXED_MULTI + "}"));
        }
        assertNotEquals(notExpected, result);
    }

    private void mockRandomServiceForFullRange() {
        AtomicInteger counter = new AtomicInteger();
        doAnswer(inv -> {
            int value = counter.getAndIncrement();
            if (value < (int) inv.getArgument(0)) {
                return value;
            }
            return inv.getArgument(0);
        }).when(randomService).nextInt(anyInt());
    }

    @Test
    void shouldResolveNestedMultiLocalePlaceholdersInMultiLocaleExpressionFromAllLocales() {
        Set<String> notExpected = new HashSet<>();
        notExpected.add("en: raspberry - cinnamon");
        notExpected.add("fr: framboise - cannelle");

        Set<String> result = new HashSet<>();

        mockRandomServiceForFullRange();

        for (int i = 0; i < 10; i++) {
            result.add(expressionResolver.resolve("#{{" + FLAVOUR + "." + MIXED_MULTI + "}}"));
        }
        assertNotEquals(notExpected, result);
    }
}