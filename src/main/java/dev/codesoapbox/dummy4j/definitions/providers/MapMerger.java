package dev.codesoapbox.dummy4j.definitions.providers;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Merges a list of definition maps into one big map
 */
public class MapMerger {

    /**
     * Merges a list of Maps into a single Map
     *
     * @param definitionMaps the list of Maps to merge
     * @return a merged Map
     */
    public Map<String, Object> merge(List<Map<String, Object>> definitionMaps) {
        Map<String, Object> mergedMap = new HashMap<>();

        definitionMaps.forEach(otherMap -> mergeMap(mergedMap, otherMap));
        return mergedMap;
    }

    private void mergeMap(Map<String, Object> mergedMap, Map<String, Object> otherMap) {
        otherMap.forEach((key, value) ->
                mergeMapKey(mergedMap, otherMap, key, value));
    }

    private void mergeMapKey(Map<String, Object> mergedMap, Map<String, Object> otherMap, String key, Object value) {
        if (!mergedMap.containsKey(key)) {
            mergedMap.put(key, value);
            return;
        }

        if (mergedMap.get(key) instanceof String) {
            if (otherMap.get(key) instanceof String) {
                mergedMap.put(key, Arrays.asList(mergedMap.get(key), otherMap.get(key)));
            } else { // Assuming OtherMap key is a Collection
                @SuppressWarnings("unchecked")
                List<String> mergedList = mergeCollectionWithElement(
                        (String) mergedMap.get(key),
                        (Collection<String>) otherMap.get(key));
                mergedMap.put(key, mergedList);
            }
        } else if (mergedMap.get(key) instanceof Map) {
            Map<String, Object> mergedValue = mergeMapKeyAsMap(mergedMap, otherMap, key);
            mergedMap.put(key, mergedValue);
        } else { // Assuming MergedMap key is a Collection
            if (otherMap.get(key) instanceof String) {
                @SuppressWarnings("unchecked")
                List<String> mergedList = mergeElementWithCollection(
                        (String) otherMap.get(key),
                        (Collection<String>) mergedMap.get(key));
                mergedMap.put(key, mergedList);
            } else { // Assuming OtherMap key is a Collection
                Object mergedValue = mergeMapKeyAsList(mergedMap, otherMap, key);
                mergedMap.put(key, mergedValue);
            }
        }
    }

    private List<String> mergeCollectionWithElement(String string, Collection<String> collection) {
        List<String> mergedList = new ArrayList<>();
        mergedList.add(string);
        mergedList.addAll(collection);
        return mergedList;
    }

    private List<String> mergeElementWithCollection(String string, Collection<String> collection) {
        List<String> mergedList = new ArrayList<>(collection);
        mergedList.add(string);
        return mergedList;
    }

    @SuppressWarnings("unchecked")
    private Object mergeMapKeyAsList(Map<String, Object> mergedMap, Map<String, Object> otherMap, String key) {
        final Collection<String> mergedMapValue = (Collection<String>) mergedMap.get(key);
        final Collection<String> otherMapValue = (Collection<String>) otherMap.get(key);
        return Stream.concat(mergedMapValue.stream(), otherMapValue.stream())
                .distinct()
                .collect(toList());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> mergeMapKeyAsMap(Map<String, Object> mergedMap, Map<String, Object> otherMap,
                                                 String key) {
        final Map<String, Object> mergedMapValue = (Map<String, Object>) mergedMap.get(key);
        final Map<String, Object> otherMapValue = (Map<String, Object>) otherMap.get(key);
        return merge(Arrays.asList(mergedMapValue, otherMapValue));
    }
}
