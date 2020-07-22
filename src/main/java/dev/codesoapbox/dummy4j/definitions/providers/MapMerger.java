package dev.codesoapbox.dummy4j.definitions.providers;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
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
        Map<String, Object> target = new HashMap<>();

        definitionMaps.forEach(otherMap -> mergeMap(target, otherMap));
        return target;
    }

    private Map<String, Object> mergeMap(Map<String, Object> targetMap, Map<String, Object> otherMap) {
        otherMap.forEach((key, value) ->
                mergeMapKey(targetMap, otherMap, key, value));
        return targetMap;
    }

    @SuppressWarnings("unchecked")
    private void mergeMapKey(Map<String, Object> targetMap, Map<String, Object> otherMap, String key, Object value) {
        if (!targetMap.containsKey(key)) {
            targetMap.put(key, value);
            return;
        }

        Object targetValue = targetMap.get(key);
        Object otherValue = otherMap.get(key);

        if (targetValue instanceof String) {
            final List<?> result = mergeStringWithObject((String) targetValue, otherValue);
            targetMap.put(key, result);
        } else if (targetValue instanceof Map) {
            Map<String, Object> result = mergeMap(
                    new HashMap<>((Map<String, Object>) targetValue), (Map<String, Object>) otherValue);
            targetMap.put(key, result);
        } else {
            // Assuming targetValue is a Collection of Strings
            Collection<?> result = mergeCollectionWithOtherObject((Collection<String>) targetValue, otherValue);
            targetMap.put(key, result);
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<?> mergeCollectionWithOtherObject(Collection<String> targetValue, Object otherValue) {
        if (otherValue instanceof String) {
            return addToEndOfCollection( targetValue, (String) otherValue);
        } else {
            // Assuming otherValue is a Collection of Strings
            return mergeListsOfStrings(targetValue,(Collection<String>) otherValue);
        }
    }

    @SuppressWarnings("unchecked")
    private List<?> mergeStringWithObject(String targetValue, Object otherValue) {
        if (otherValue instanceof String) {
            return asList(targetValue, otherValue);
        } else {
            // Assuming otherValue is a Collection
            return addToBeginningOfCollection(targetValue, (Collection<String>) otherValue);
        }
    }

    /**
     * Adds element to the beginning of the collection
     */
    private List<String> addToBeginningOfCollection(String element, Collection<String> collection) {
        List<String> mergedList = new ArrayList<>();
        mergedList.add(element);
        mergedList.addAll(collection);
        return mergedList;
    }

    private List<String> addToEndOfCollection(Collection<String> collection, String element) {
        List<String> mergedList = new ArrayList<>(collection);
        mergedList.add(element);
        return mergedList;
    }

    private List<String> mergeListsOfStrings(Collection<String> targetValue, Collection<String> otherValue) {
        return Stream.concat(targetValue.stream(), otherValue.stream())
                .distinct()
                .collect(toList());
    }
}
