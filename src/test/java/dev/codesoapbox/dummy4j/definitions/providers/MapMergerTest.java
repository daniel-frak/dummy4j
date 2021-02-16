package dev.codesoapbox.dummy4j.definitions.providers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapMergerTest {

    private MapMerger mapMerger;

    @BeforeEach
    void setUp() {
        mapMerger = new MapMerger();
    }

    @Test
    void shouldNotMutateOriginalMaps() {
        List<Map<String, Object>> maps = advancedMapList();

        mapMerger.merge(maps);

        assertEquals(advancedMapList(), maps);
    }

    private List<Map<String, Object>> advancedMapList() {
        return asList(
                simpleMap("pl", "root1", "val1"),
                simpleMap("en", "root1", "val1"),
                simpleMap("en", "root1", "val2"),
                simpleMap("en", "root2", asList("val1", "val2")),
                simpleMap("en", "root2", "val3")
        );
    }

    /**
     * {
     * val1: {
     * val2: val3
     * }
     * }
     */
    private Map<String, Object> simpleMap(String key1, String key2, Object val2) {
        Map<String, Object> mapRoot = new HashMap<>();
        Map<String, Object> mapSub = new HashMap<>();
        mapRoot.put(key1, mapSub);
        mapSub.put(key2, val2);
        return mapRoot;
    }

    @Test
    void shouldMerge() {
        Map<String, Object> yamlMap1 = simpleMap("en", "root1", "val1");
        Map<String, Object> yamlMap2 = simpleMap("en", "root2", "val2");

        Map<String, Object> expectedMap = new HashMap<>();
        Map<String, Object> expectedSubMap = new HashMap<>();
        expectedSubMap.put("root1", "val1");
        expectedSubMap.put("root2", "val2");
        expectedMap.put("en", expectedSubMap);

        Map<String, Object> result = mapMerger.merge(asList(yamlMap1, yamlMap2));
        assertEquals(expectedMap, result);
    }

    @Test
    void shouldMergeDeep() {
        Map<String, Object> yamlMap1 = new HashMap<>();
        Map<String, Object> yamlMapRoot1 = new HashMap<>();
        HashMap<Object, Object> yamlSubMap1 = new HashMap<>();
        yamlSubMap1.put("sub1", "val1");
        yamlMapRoot1.put("root1", yamlSubMap1);
        yamlMap1.put("en", yamlMapRoot1);

        Map<String, Object> yamlMap2 = new HashMap<>();
        Map<String, Object> yamlMapRoot2 = new HashMap<>();
        HashMap<Object, Object> yamlSubMap2 = new HashMap<>();
        yamlSubMap2.put("sub2", "val2");
        yamlMapRoot2.put("root1", yamlSubMap2);
        yamlMapRoot2.put("root2", "val3");
        yamlMap2.put("en", yamlMapRoot2);

        Map<String, Object> expectedMap = new HashMap<>();
        Map<String, Object> expectedRootMap = new HashMap<>();
        Map<String, Object> expectedSubMap1 = new HashMap<>();
        expectedSubMap1.put("sub1", "val1");
        expectedSubMap1.put("sub2", "val2");
        expectedRootMap.put("root1", expectedSubMap1);
        expectedRootMap.put("root2", "val3");
        expectedMap.put("en", expectedRootMap);

        Map<String, Object> result = mapMerger.merge(asList(yamlMap1, yamlMap2));
        assertEquals(expectedMap, result);
    }

    @Test
    void shouldNotMergeDifferentLocales() {
        Map<String, Object> yamlMap1 = simpleMap("en", "root1", "val1");
        Map<String, Object> yamlMap2 = simpleMap("pl", "root1", "val1");

        Map<String, Object> expectedMap = new HashMap<>();
        Map<String, Object> expectedRootMap = new HashMap<>();
        expectedRootMap.put("root1", "val1");
        expectedMap.put("en", expectedRootMap);
        expectedMap.put("pl", expectedRootMap);

        Map<String, Object> result = mapMerger.merge(asList(yamlMap1, yamlMap2));
        assertEquals(expectedMap, result);
    }

    @Test
    void shouldMergeTwoLists() {
        Map<String, Object> yamlMap1 = simpleMap("en", "root1", asList("val1", "val2"));
        Map<String, Object> yamlMap2 = simpleMap("en", "root1", asList("val3", "val4"));
        Map<String, Object> expectedMap = simpleMap("en", "root1", asList("val1", "val2", "val3", "val4"));

        Map<String, Object> result = mapMerger.merge(asList(yamlMap1, yamlMap2));

        assertEquals(expectedMap, result);
    }

    @Test
    void shouldMergeTwoSingleElements() {
        Map<String, Object> yamlMap1 = simpleMap("en", "root1", "val1");
        Map<String, Object> yamlMap2 = simpleMap("en", "root1", "val2");
        Map<String, Object> expectedMap = simpleMap("en", "root1", asList("val1", "val2"));

        Map<String, Object> result = mapMerger.merge(asList(yamlMap1, yamlMap2));

        assertEquals(expectedMap, result);
    }

    @Test
    void shouldMergeListAndSingleElement() {
        Map<String, Object> yamlMap1 = simpleMap("en", "root1", asList("val1", "val2"));
        Map<String, Object> yamlMap2 = simpleMap("en", "root1", "val3");
        Map<String, Object> expectedMap = simpleMap("en", "root1", asList("val1", "val2", "val3"));

        Map<String, Object> result = mapMerger.merge(asList(yamlMap1, yamlMap2));

        assertEquals(expectedMap, result);
    }

    @Test
    void shouldMergeSingleElementAndList() {
        Map<String, Object> yamlMap1 = simpleMap("en", "root1", "val1");
        Map<String, Object> yamlMap2 = simpleMap("en", "root1", asList("val2", "val3"));
        Map<String, Object> expectedMap = simpleMap("en", "root1", asList("val1", "val2", "val3"));

        Map<String, Object> result = mapMerger.merge(asList(yamlMap1, yamlMap2));

        assertEquals(expectedMap, result);
    }
}