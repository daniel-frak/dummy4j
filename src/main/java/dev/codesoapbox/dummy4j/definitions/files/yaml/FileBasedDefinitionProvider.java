package dev.codesoapbox.dummy4j.definitions.files.yaml;

import dev.codesoapbox.dummy4j.definitions.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

/**
 * Provides definitions by loading all files as Maps, merging them into one big Map and then splitting them by locale
 * as {@code LocalizedDummyDefinitionsMap} instances
 */
public class FileBasedDefinitionProvider implements DefinitionProvider {

    private static final String DEFAULT_PATH = "dummy4j";

    private final YamlFileLoader yamlFileLoader;
    private final MapMerger mapMerger;
    private final List<String> paths;

    private List<LocalizedDummyDefinitions> definitions;

    public FileBasedDefinitionProvider(YamlFileLoader yamlFileLoader, MapMerger mapMerger, List<String> paths) {
        this.mapMerger = mapMerger;
        this.yamlFileLoader = yamlFileLoader;
        this.paths = paths;
    }

    public static FileBasedDefinitionProvider standard() {
        return withPaths(singletonList(DEFAULT_PATH));
    }

    public static FileBasedDefinitionProvider withPaths(List<String> paths) {
        return new FileBasedDefinitionProvider(YamlFileLoader.standard(), new MapMerger(), paths);
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
