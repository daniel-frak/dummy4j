package dev.codesoapbox.dummy4j.definitions;

import java.util.*;

import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;

/**
 * Stores localized dummy data definitions as a Java Map
 */
public final class LocalizedDummyDefinitionsMap implements LocalizedDummyDefinitions {

    private final String locale;
    private final Map<String, Object> map;

    public LocalizedDummyDefinitionsMap(String locale, Map<String, Object> map) {
        this.locale = locale;
        this.map = map;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public List<String> resolve(String path) {
        String[] keys = path.split("\\.");

        return resolve(map, keys);
    }

    private List<String> resolve(Map<String, Object> subMap, String[] keys) {
        if (!subMap.containsKey(keys[0])) {
            return emptyList();
        }

        return resolveResult(keys, subMap.get(keys[0]));
    }

    @SuppressWarnings("unchecked")
    private List<String> resolveResult(String[] keys, Object result) {
        if (result instanceof Map) {
            String[] keysWithoutRoot = Arrays.copyOfRange(keys, 1, keys.length);
            return resolve((Map<String, Object>) result, keysWithoutRoot);
        }

        if (result instanceof List) {
            return toStringList((List<?>) result);
        }

        return singletonList(String.valueOf(result));
    }

    @SuppressWarnings("unchecked")
    private List<String> toStringList(List<?> result) {
        if (result.isEmpty()) {
            return (List<String>) result;
        }

        return result.stream()
                .map(String::valueOf)
                .collect(toList());
    }

    @Override
    public Set<String> getKeysFor(String path) {
        String[] keys = path.split("\\.");

        return getKeysFor(map, keys);
    }

    private Set<String> getKeysFor(Map<String, Object> subMap, String[] keys) {
        if (!subMap.containsKey(keys[0])) {
            return emptySet();
        }

        return getKeysForResult(keys, subMap.get(keys[0]));
    }

    @SuppressWarnings("unchecked")
    private Set<String> getKeysForResult(String[] keys, Object result) {
        if (!(result instanceof Map)) {
            return emptySet();
        }

        if(keys.length == 1) {
            return ((Map<String, Object>) result).keySet();
        }

        String[] keysWithoutRoot = Arrays.copyOfRange(keys, 1, keys.length);
        return getKeysFor((Map<String, Object>) result, keysWithoutRoot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocalizedDummyDefinitionsMap that = (LocalizedDummyDefinitionsMap) o;

        return Objects.equals(locale, that.locale) &&
                Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, map);
    }

    @Override
    public String toString() {
        return "LocalizedDummyDefinitions{" +
                "locale='" + locale + '\'' +
                ", map=" + map +
                '}';
    }
}
