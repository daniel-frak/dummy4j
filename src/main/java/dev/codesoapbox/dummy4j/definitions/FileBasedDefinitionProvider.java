package dev.codesoapbox.dummy4j.definitions;

import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileBasedDefinitionProvider implements DefinitionProvider {

    private final Logger log = Logger.getLogger(FileBasedDefinitionProvider.class.getSimpleName());

    /**
     * Note that Yaml isn't thread safe!
     */
    private final Yaml yaml;

    private final Reflections reflections;

    private final List<String> paths;

    public FileBasedDefinitionProvider(Yaml yaml, Reflections reflections, List<String> paths) {
        this.yaml = yaml;
        this.reflections = reflections;
        this.paths = paths;
    }

    @Override
    public List<LocalizedDummyDefinitions> get() {
        final Set<String> resources = reflections.getResources(Pattern.compile(".*\\.yml"));
        log.log(Level.FINE, "Loading definitions from: {0}", resources);

        List<Map<String, Object>> definitionMaps = new ArrayList<>();
        for (String resource : resources) {
            if (!isInAllowedPath(resource, paths)) {
                continue;
            }
            InputStream inputStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(resource);
            definitionMaps.add(yaml.load(inputStream));
        }

        Map<String, Object> mergedDefinitionMap = merge(definitionMaps);

        List<LocalizedDummyDefinitions> localizedDummyDefinitions = new ArrayList<>();
        mergedDefinitionMap.forEach((key, value) ->
                localizedDummyDefinitions.add(toLocalizedDummyDefinitions(key, value)));

        return localizedDummyDefinitions;
    }

    private boolean isInAllowedPath(String resource, List<String> paths) {
        boolean isInAllowedPath = false;
        for (String path : paths) {
            if (resource.startsWith(path)) {
                isInAllowedPath = true;
                break;
            }
        }
        return isInAllowedPath;
    }

    @SuppressWarnings("unchecked")
    private LocalizedDummyDefinitions toLocalizedDummyDefinitions(String key, Object value) {
        return new LocalizedDummyDefinitions(key, (Map<String, Object>) value);
    }

    private Map<String, Object> merge(List<Map<String, Object>> definitionMaps) {
        Map<String, Object> mergedMap = new HashMap<>();

        definitionMaps.forEach(otherMap -> mergeMap(mergedMap, otherMap));
        return mergedMap;
    }

    private void mergeMap(Map<String, Object> mergedMap, Map<String, Object> otherMap) {
        otherMap.forEach((key, value) ->
                mergeMapKey(mergedMap, otherMap, key, value));
    }

    private void mergeMapKey(Map<String, Object> mergedMap, Map<String, Object> otherMap, String key, Object value) {
        if (!mergedMap.containsKey(key)) {
            mergedMap.put(key, value);
            return;
        }
        if (mergedMap.get(key) instanceof Map) {
            Map<String, Object> mergedValue = mergeMapKeyAsMap(mergedMap, otherMap, key);
            mergedMap.put(key, mergedValue);
        }
        if (mergedMap.get(key) instanceof Collection) {
            Object mergedValue = mergeMapKeyAsList(mergedMap, otherMap, key);
            mergedMap.put(key, mergedValue);
        }
    }

    @SuppressWarnings("unchecked")
    private Object mergeMapKeyAsList(Map<String, Object> mergedMap, Map<String, Object> otherMap, String key) {
        final Collection<String> mergedMapValue = (Collection<String>) mergedMap.get(key);
        final Collection<String> otherMapValue = (Collection<String>) otherMap.get(key);
        return Stream.concat(mergedMapValue.stream(), otherMapValue.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> mergeMapKeyAsMap(Map<String, Object> mergedMap, Map<String, Object> otherMap,
                                                 String key) {
        final Map<String, Object> mergedMapValue = (Map<String, Object>) mergedMap.get(key);
        final Map<String, Object> otherMapValue = (Map<String, Object>) otherMap.get(key);
        return merge(Arrays.asList(mergedMapValue, otherMapValue));
    }
}
