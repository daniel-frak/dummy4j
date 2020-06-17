package dev.codesoapbox.dummy4j.definitions.files.yaml;

import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class LocalizedDummyDefinitionsMap implements LocalizedDummyDefinitions {

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
    public List<String> resolve(String key) {
        String[] keys = key.split("\\.");

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
        if (result instanceof String) {
            return singletonList((String) result);
        }
        if (result instanceof List) {
            return (List<String>) result;
        }

        return resolve((Map<String, Object>) result, Arrays.copyOfRange(keys, 1, keys.length));
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
