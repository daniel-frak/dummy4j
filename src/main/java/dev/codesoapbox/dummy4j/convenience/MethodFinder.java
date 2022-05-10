package dev.codesoapbox.dummy4j.convenience;

import dev.codesoapbox.dummy4j.dummies.shared.string.StringValidator;

import java.util.List;
import java.util.Locale;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Helps find methods in Dummy4j
 */
class MethodFinder {

    private static final String NEW_LINE = "\n";
    private static final Locale LOCALE = Locale.ENGLISH;

    private final Class<?> rootClass;
    private final MethodPathLoader methodPathLoader;
    private List<String> methodPaths;

    public MethodFinder(Class<?> rootClass, MethodPathLoader methodPathLoader) {
        this.rootClass = rootClass;
        this.methodPathLoader = methodPathLoader;
    }

    public String find(String value) {
        List<String> foundMethodPaths = findList(value);

        if (foundMethodPaths.isEmpty()) {
            return "No methods found containing '" + value + "'";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Found methods containing '")
                .append(value)
                .append("':");

        foundMethodPaths.forEach(m -> stringBuilder.append(NEW_LINE).append(m));

        return stringBuilder.toString();
    }

    private List<String> findList(String value) {
        if (StringValidator.isNullOrEmpty(value) || value.equals(methodPathLoader.getSeparator())) {
            return emptyList();
        }

        return getMethodPaths().stream()
                .filter(m -> m.toLowerCase(LOCALE).contains(value.toLowerCase(LOCALE)))
                .map(method -> trimMethodPathAfterTargetMethod(value, method))
                .distinct()
                .collect(toList());
    }

    private List<String> getMethodPaths() {
        if (methodPaths == null) {
            methodPaths = methodPathLoader.load(rootClass);
        }
        return methodPaths;
    }

    private String trimMethodPathAfterTargetMethod(String value, String methodPath) {
        String methodPathLower = methodPath.toLowerCase(LOCALE);
        int indexOfValue = methodPathLower.lastIndexOf(value.toLowerCase(LOCALE));
        int indexOfLastSeparator = getIndexOfLastSeparator(methodPathLower, indexOfValue);
        return methodPath.substring(0, indexOfLastSeparator);
    }

    private int getIndexOfLastSeparator(String methodPathLower, int indexOfValue) {
        int indexOfLastSeparator = methodPathLower.indexOf(methodPathLoader.getSeparator(), indexOfValue);
        if (indexOfValue > indexOfLastSeparator) {
            return methodPathLower.length();
        }

        return indexOfLastSeparator;
    }
}
