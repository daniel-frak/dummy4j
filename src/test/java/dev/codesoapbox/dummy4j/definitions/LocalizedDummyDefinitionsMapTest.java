package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.definitions.files.yaml.LocalizedDummyDefinitionsMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LocalizedDummyDefinitionsMapTest {

    private LocalizedDummyDefinitionsMap dummyDefinitions;

    @BeforeEach
    void setUp() {
        final Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("deeper", "actualValue");

        final Map<String, Object> map = new HashMap<>();
        map.put("something", nestedMap);

        dummyDefinitions = new LocalizedDummyDefinitionsMap("en", map);
    }

    @Test
    void shouldResolveNestedKey() {
        assertEquals(Collections.singletonList("actualValue"), dummyDefinitions.resolve("something.deeper"));
    }

    @Test
    void shouldReturnNullWhenKeyNotFound() {
        assertNull(dummyDefinitions.resolve("deeper"));
    }
}