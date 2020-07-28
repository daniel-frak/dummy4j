package dev.codesoapbox.dummy4j.definitions;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void equalsContract() {
        EqualsVerifier.forClass(LocalizedDummyDefinitionsMap.class)
                .usingGetClass()
                .verify();
    }

    @Test
    void toStringShouldBeCorrect() {
        LocalizedDummyDefinitionsMap dummyDefinitionsMap = buildSimpleDummyDefinitions();

        String expectedString = "LocalizedDummyDefinitions{locale='en', map={key=val}}";
        assertEquals(expectedString, dummyDefinitionsMap.toString());
    }

    private LocalizedDummyDefinitionsMap buildSimpleDummyDefinitions() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "val");
        return new LocalizedDummyDefinitionsMap("en", map);
    }
}