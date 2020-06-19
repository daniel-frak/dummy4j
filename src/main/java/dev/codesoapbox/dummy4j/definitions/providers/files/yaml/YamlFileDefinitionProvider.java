package dev.codesoapbox.dummy4j.definitions.providers.files.yaml;

import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitionsMap;
import dev.codesoapbox.dummy4j.definitions.providers.MapMerger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

/**
 * Provides definitions by loading all files as Maps, merging them into one big Map and then splitting them by locale
 * as {@code LocalizedDummyDefinitionsMap} instances
 */
public class YamlFileDefinitionProvider implements DefinitionProvider {

    private static final String DEFAULT_PATH = "dummy4j";

    private final YamlFileLoader yamlFileLoader;
    private final MapMerger mapMerger;
    private final List<String> paths;

    private List<LocalizedDummyDefinitions> definitions;

    public YamlFileDefinitionProvider(YamlFileLoader yamlFileLoader, MapMerger mapMerger, List<String> paths) {
        this.mapMerger = mapMerger;
        this.yamlFileLoader = yamlFileLoader;
        this.paths = paths;
    }

    public static YamlFileDefinitionProvider standard() {
        return withPaths(singletonList(DEFAULT_PATH));
    }

    public static YamlFileDefinitionProvider withPaths(List<String> paths) {
        return new YamlFileDefinitionProvider(YamlFileLoader.standard(), new MapMerger(), paths);
    }

    @Override
    public List<LocalizedDummyDefinitions> get() {
        if (definitions != null) {
            return definitions;
        }

        loadDefinitions();

        return definitions;
    }

    private void loadDefinitions() {
        List<Map<String, Object>> unmergedDefinitionMaps = yamlFileLoader.loadYamlFiles(paths);
        Map<String, Object> mergedDefinitionMap = mapMerger.merge(unmergedDefinitionMaps);
        definitions = splitByLocale(mergedDefinitionMap);
    }

    private List<LocalizedDummyDefinitions> splitByLocale(Map<String, Object> mergedDefinitionMap) {
        List<LocalizedDummyDefinitions> localizedDummyDefinitions = new ArrayList<>();
        mergedDefinitionMap.forEach((locale, subMap) ->
                localizedDummyDefinitions.add(toLocalizedDummyDefinitions(locale, subMap)));

        return localizedDummyDefinitions;
    }

    @SuppressWarnings("unchecked")
    private LocalizedDummyDefinitions toLocalizedDummyDefinitions(String locale, Object map) {
        return new LocalizedDummyDefinitionsMap(locale, (Map<String, Object>) map);
    }
}
