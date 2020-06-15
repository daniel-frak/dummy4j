package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.exceptions.DefinitionNotFoundException;

import java.util.*;

import static java.util.Collections.*;

public class LocalizedDummyDefinitions {

    private final String locale;
    private final Map<String, Object> map;

    public LocalizedDummyDefinitions(String locale, Map<String, Object> map) {
        this.locale = locale;
        this.map = map;
    }

    public String getLocale() {
        return locale;
    }

    public List<String> resolve(String key) {
        String[] keys = key.split("\\.");

        return resolve(map, keys);
    }

    @SuppressWarnings("unchecked")
    private List<String> resolve(Map<String, Object> subMap, String[] keys) {
        if (!subMap.containsKey(keys[0])) {
            return null;
        }

        final Object result = subMap.get(keys[0]);
        if (result instanceof String) {
            return singletonList((String) result);
        }
        if(result instanceof List) {
            return (List<String>) result;
        }

        return resolve((Map<String, Object>) result, Arrays.copyOfRange(keys, 1, keys.length));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizedDummyDefinitions that = (LocalizedDummyDefinitions) o;
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
