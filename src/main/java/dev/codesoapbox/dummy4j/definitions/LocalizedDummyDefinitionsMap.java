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
            return resolveMap(keys, (Map<String, Object>) result);
        }

        if (result instanceof List) {
            return toStringList((List<?>) result);
        }

        return singletonList(String.valueOf(result));
    }

    private List<String> resolveMap(String[] keys, Map<String, Object> result) {
        if(keys.length == 1) {
            return new ArrayList<>(result.keySet());
        }
        String[] keysWithoutRoot = Arrays.copyOfRange(keys, 1, keys.length);
        return resolve(result, keysWithoutRoot);
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
