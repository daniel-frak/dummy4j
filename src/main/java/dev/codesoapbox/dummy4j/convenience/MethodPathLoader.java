package dev.codesoapbox.dummy4j.convenience;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

class MethodPathLoader {

    private static final String JAVA_LIB_PREFIX = "java.";
    private static final String SEPARATOR = ".";

    public String getSeparator() {
        return SEPARATOR;
    }

    public List<String> load(Class<?> rootClass) {
        Map<String, Object> methodMap = new HashMap<>();
        methodMap.put(rootClass.getSimpleName(), getMethodsForClass(rootClass, new ArrayList<>()));
        return mapToStrings(methodMap, "");
    }

    private static Map<String, Object> getMethodsForClass(Class<?> sourceClass, List<Class<?>> previousClasses) {
        previousClasses.add(sourceClass);

        Map<String, Object> methodMap = new HashMap<>();
        for (Method method : sourceClass.getDeclaredMethods()) {
            if (isPublic(method) && isNotRecursion(method, previousClasses)) {
                methodMap.put(method.getName() + "()", getSubMethods(method, previousClasses));
            }
        }

        return methodMap;
    }

    private static boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    private static boolean isNotRecursion(Method method, List<Class<?>> previousClasses) {
        return !previousClasses.contains(method.getReturnType());
    }

    private static Map<String, Object> getSubMethods(Method method, List<Class<?>> previousClasses) {
        if(isNotNativeJavaClass(method)) {
            return getMethodsForClass(method.getReturnType(), previousClasses);
        }
        return null;
    }

    private static boolean isNotNativeJavaClass(Method method) {
        return method.getReturnType().getPackage() != null
                && !method.getReturnType().getPackage().getName().startsWith(JAVA_LIB_PREFIX);
    }

    private static List<String> mapToStrings(Map<String, Object> methodMap, String prefix) {
        return methodMap.entrySet().stream()
                .map(e -> mapEntryToStrings(e, prefix))
                .flatMap(Collection::stream)
                .sorted()
                .collect(toList());
    }

    @SuppressWarnings("unchecked")
    private static List<String> mapEntryToStrings(Map.Entry<String, Object> mapEntry, String prefix) {
        if (mapEntry.getValue() instanceof Map) {
            Map<String, Object> value = (Map<String, Object>) mapEntry.getValue();
            return mapMapToStrings(value, prefix, mapEntry.getKey());
        }
        return singletonList(prefix + mapEntry.getKey() + SEPARATOR + mapEntry.getValue());
    }

    private static List<String> mapMapToStrings(Map<String, Object> map, String prefix, String key) {
        if (map.isEmpty()) {
            return singletonList(prefix + key);
        }

        List<String> strings = mapToStrings(map, prefix + key + SEPARATOR);
        if (isNotRoot(prefix)) {
            strings.add(prefix + key);
        }
        return strings;
    }

    private static boolean isNotRoot(String prefix) {
        return !prefix.isEmpty();
    }
}
