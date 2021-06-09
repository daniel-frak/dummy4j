package dev.codesoapbox.dummy4j.definitions;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalizedDummyDefinitionsMapTest {

    private LocalizedDummyDefinitionsMap dummyDefinitions;

    @BeforeEach
    void setUp() {
        final Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("deeper", "actualValue");

        final Map<String, Object> doublyNestedMap = new HashMap<>();
        doublyNestedMap.put("thanThat", "actualValue");
        nestedMap.put("evenDeeper", doublyNestedMap);

        final Map<String, Object> map = new HashMap<>();
        map.put("something", nestedMap);
        map.put("test_number", 1);
        map.put("test_float_number", 1.4);
        map.put("test_number_list", Arrays.asList(1, 2));
        map.put("test_mixed_list", Arrays.asList("1", 2));

        dummyDefinitions = new LocalizedDummyDefinitionsMap("en", map);
    }

    @Test
    void shouldResolveNestedKey() {
        assertEquals(singletonList("actualValue"), dummyDefinitions.resolve("something.deeper"));
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

    @Test
    void shouldResolveNumber() {
        List<String> result = dummyDefinitions.resolve("test_number");
        assertEquals(singletonList("1"), result);
    }

    @Test
    void shouldResolveFloatingPointNumber() {
        List<String> result = dummyDefinitions.resolve("test_float_number");
        assertEquals(singletonList("1.4"), result);
    }

    @Test
    void shouldResolveNumberList() {
        List<String> result = dummyDefinitions.resolve("test_number_list");
        assertEquals(Arrays.asList("1", "2"), result);
    }

    @Test
    void shouldResolveMixedList() {
        List<String> result = dummyDefinitions.resolve("test_mixed_list");
        assertEquals(Arrays.asList("1", "2"), result);
    }

    @Test
    void shouldGetKeysFor() {
        assertEquals(Arrays.asList("evenDeeper", "deeper"), dummyDefinitions.resolve("something"));
    }

    @Test
    void shouldGetKeysForNested() {
        assertEquals(singletonList("thanThat"), dummyDefinitions.resolve("something.evenDeeper"));
    }

    @Test
    void getKeysOfShouldReturnEmptySetWhenKeyIsNotFound() {
        assertEquals(emptyList(), dummyDefinitions.resolve("thisDoesntExist"));
    }

    @Test
    void shouldReturnNestedKeysWhenHigherKeyEndsWithDot() {
        List<String> result = dummyDefinitions.resolve("something.");
        assertEquals(Arrays.asList("evenDeeper", "deeper"), result);
    }

    @Test
    void shouldReturnValueKeysWhenLastKeyEndsWithDot() {
        List<String> result = dummyDefinitions.resolve("something.evenDeeper.thanThat.");
        assertEquals(singletonList("actualValue"), result);
    }
}