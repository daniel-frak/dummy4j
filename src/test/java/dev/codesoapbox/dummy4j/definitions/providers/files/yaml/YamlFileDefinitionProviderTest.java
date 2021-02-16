package dev.codesoapbox.dummy4j.definitions.providers.files.yaml;

import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitionsMap;
import dev.codesoapbox.dummy4j.definitions.providers.MapMerger;
import dev.codesoapbox.dummy4j.definitions.providers.files.yaml.YamlFileDefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.providers.files.yaml.YamlFileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class YamlFileDefinitionProviderTest {

    private final List<String> paths = singletonList("dummy4j");

    private YamlFileDefinitionProvider yamlFileDefinitionProvider;

    @Mock
    private YamlFileLoader yamlFileLoader;

    @Mock
    private MapMerger mapMerger;

    @BeforeEach
    void setUp() {
        yamlFileDefinitionProvider = new YamlFileDefinitionProvider(yamlFileLoader, mapMerger, paths);
    }

    @Test
    void shouldGet() {
        @SuppressWarnings("unchecked")
        Map<String, Object> yamlMap = mock(Map.class);
        Map<String, Object> mergedMap = new HashMap<>();
        Map<String, Object> mergedSubMap = new HashMap<>();
        mergedSubMap.put("test", "value");
        mergedMap.put("en", mergedSubMap);

        List<LocalizedDummyDefinitions> expectedDummyDefinitions = singletonList(
                new LocalizedDummyDefinitionsMap("en", mergedSubMap)
        );

        when(yamlFileLoader.loadYamlFiles(paths))
                .thenReturn(singletonList(yamlMap));
        when(mapMerger.merge(singletonList(yamlMap)))
                .thenReturn(mergedMap);
        List<LocalizedDummyDefinitions> result = yamlFileDefinitionProvider.get();

        assertEquals(expectedDummyDefinitions, result);
    }

    @Test
    void shouldGetAsDifferentLocales() {
        @SuppressWarnings("unchecked")
        Map<String, Object> yamlMap = mock(Map.class);
        Map<String, Object> mergedMap = new HashMap<>();
        Map<String, Object> mergedSubMap = new HashMap<>();
        mergedSubMap.put("test", "value");
        mergedMap.put("en", mergedSubMap);
        mergedMap.put("pl", mergedSubMap);

        List<LocalizedDummyDefinitions> expectedDummyDefinitions = asList(
                new LocalizedDummyDefinitionsMap("en", mergedSubMap),
                new LocalizedDummyDefinitionsMap("pl", mergedSubMap)
        );

        when(yamlFileLoader.loadYamlFiles(paths))
                .thenReturn(singletonList(yamlMap));
        when(mapMerger.merge(singletonList(yamlMap)))
                .thenReturn(mergedMap);
        List<LocalizedDummyDefinitions> result = yamlFileDefinitionProvider.get();

        assertEquals(expectedDummyDefinitions, result);
    }

    @Test
    void shouldGetCached() {
        yamlFileDefinitionProvider.get();
        yamlFileDefinitionProvider.get();
        verify(yamlFileLoader).loadYamlFiles(any());
    }
}