package dev.codesoapbox.dummy4j.definitions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;
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
        assertEquals(emptyList(), dummyDefinitions.resolve("deeper"));
    }

    @Test
    void shouldEqual() {
        LocalizedDummyDefinitionsMap dummyDefinitionsMap1 = buildSimpleDummyDefinitions();
        LocalizedDummyDefinitionsMap dummyDefinitionsMap2 = buildSimpleDummyDefinitions();

        assertEquals(dummyDefinitionsMap1, dummyDefinitionsMap2);
    }

    @Test
    void shouldNotEqualWithNull() {
        assertNotEquals(buildSimpleDummyDefinitions(), null);
    }

    @Test
    void shouldNotEqualWithDifferentLocales() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "val");
        LocalizedDummyDefinitionsMap dummyDefinitionsMap1 = new LocalizedDummyDefinitionsMap("en", map);
        LocalizedDummyDefinitionsMap dummyDefinitionsMap2 = new LocalizedDummyDefinitionsMap("pl", map);

        assertNotEquals(dummyDefinitionsMap1, dummyDefinitionsMap2);
    }

    @Test
    void shouldNotEqualWithDifferentMaps() {
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("key", "val1");
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("key", "val2");
        LocalizedDummyDefinitionsMap dummyDefinitionsMap1 = new LocalizedDummyDefinitionsMap("en", map1);
        LocalizedDummyDefinitionsMap dummyDefinitionsMap2 = new LocalizedDummyDefinitionsMap("en", map2);

        assertNotEquals(dummyDefinitionsMap1, dummyDefinitionsMap2);
    }

    @Test
    void shouldNotEqualWithOtherClass() {
        LocalizedDummyDefinitionsMap dummyDefinitionsMap = buildSimpleDummyDefinitions();

        assertNotEquals(dummyDefinitionsMap, new Object());
    }

    @Test
    void shouldEqualWithItself() {
        LocalizedDummyDefinitionsMap dummyDefinitionsMap = buildSimpleDummyDefinitions();

        assertEquals(dummyDefinitionsMap, dummyDefinitionsMap);
    }

    private LocalizedDummyDefinitionsMap buildSimpleDummyDefinitions() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "val");
        return new LocalizedDummyDefinitionsMap("en", map);
    }

    @Test
    void shouldHaveSameHashcode() {
        LocalizedDummyDefinitionsMap dummyDefinitionsMap1 = buildSimpleDummyDefinitions();
        LocalizedDummyDefinitionsMap dummyDefinitionsMap2 = buildSimpleDummyDefinitions();

        assertEquals(dummyDefinitionsMap1.hashCode(), dummyDefinitionsMap2.hashCode());
    }

    @Test
    void toStringShouldBeCorrect() {
        LocalizedDummyDefinitionsMap dummyDefinitionsMap = buildSimpleDummyDefinitions();

        String expectedString = "LocalizedDummyDefinitions{locale='en', map={key=val}}";
        assertEquals(expectedString, dummyDefinitionsMap.toString());
    }
}