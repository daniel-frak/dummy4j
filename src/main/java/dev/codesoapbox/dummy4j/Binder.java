package dev.codesoapbox.dummy4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Binder {

    private final Dummy4j dummy4j;

    public Binder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public <T> T bind(String keyPrefix, Class<T> targetClass)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T instance = targetClass.getConstructor().newInstance();
        List<Method> setters = getSetters(targetClass);

        for (String fieldName : getFieldNames(targetClass)) {
            Method setter = getSetter(setters, fieldName);
            String lowerCaseFirstLetterFieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            setter.invoke(instance, resolveField(keyPrefix, lowerCaseFirstLetterFieldName));
        }

        return instance;
    }

    private <T> List<Method> getSetters(Class<T> targetClass) {
        return Arrays.stream(targetClass.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("set"))
                .collect(Collectors.toList());
    }

    private <T> List<String> getFieldNames(Class<T> targetClass) {
        return Arrays.stream(targetClass.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("get"))
                .map(m -> m.getName().substring(3))
                .collect(Collectors.toList());
    }

    private Method getSetter(List<Method> setters, String fieldName) {
        String setterName = "set" + fieldName;

        return setters.stream()
                .filter(s -> s.getName().equals(setterName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Setter not found: " + setterName));
    }

    private String resolveField(String keyPrefix, String fieldName) {
        return dummy4j.expressionResolver.resolve("#{" + keyPrefix + "." + fieldName + "}");
    }
}
