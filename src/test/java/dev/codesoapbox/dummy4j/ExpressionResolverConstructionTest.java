package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import dev.codesoapbox.dummy4j.exceptions.MissingLocaleDefinitionsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpressionResolverConstructionTest {

    @Mock
    private RandomService randomService;

    @Mock
    private DefinitionProvider definitionProvider;

    @Test
    void shouldConstructWithDefaultLocaleWhenNull() {
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn("en");
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        DefaultExpressionResolver resolver = new DefaultExpressionResolver(null, randomService, definitionProvider);

        assertEquals(singletonList("en"), resolver.locales);
    }

    @Test
    void shouldConstructWithDefaultLocaleWhenEmpty() {
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn("en");
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        DefaultExpressionResolver resolver = new DefaultExpressionResolver(emptyList(), randomService, definitionProvider);

        assertEquals(singletonList("en"), resolver.locales);
    }

    @Test
    void shouldConstructWithCustomLocale() {
        String customLocale = "pl";
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn(customLocale);
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        DefaultExpressionResolver resolver = new DefaultExpressionResolver(singletonList(customLocale), randomService,
                definitionProvider);

        assertEquals(singletonList(customLocale), resolver.locales);
    }

    @Test
    void shouldThrowExceptionIfLocaleNotFoundInDefinitions() {
        String customLocale = "pl";
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn(customLocale);
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        List<String> locales = singletonList("en");
        assertThrows(MissingLocaleDefinitionsException.class,
                () -> new DefaultExpressionResolver(locales, randomService, definitionProvider));
    }

}