package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.exceptions.DefinitionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LocalizedDummyDefinitionsTest {

    private LocalizedDummyDefinitions dummyDefinitions;

    @BeforeEach
    void setUp() {
        final Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("deeper", "actualValue");

        final Map<String, Object> map = new HashMap<>();
        map.put("something", nestedMap);

        dummyDefinitions = new LocalizedDummyDefinitions("en", map);
    }

    @Test
    void shouldResolveNestedKey() {
        assertEquals("actualValue", dummyDefinitions.resolve("something.deeper"));
    }

    @Test
    void shouldReturnNullWhenKeyNotFound() {
        assertNull(dummyDefinitions.resolve("deeper"));
    }
}