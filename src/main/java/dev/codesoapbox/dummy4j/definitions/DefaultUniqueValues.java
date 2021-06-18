package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.annotations.Experimental;
import dev.codesoapbox.dummy4j.exceptions.UniqueValueRetryLimitExceededException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @since 0.8.0
 */
@Experimental
public final class DefaultUniqueValues implements UniqueValues {

    private final Map<String, Set<Object>> usedValues;
    private int maxRetries;

    public DefaultUniqueValues() {
        this.usedValues = new ConcurrentHashMap<>();
        this.maxRetries = 10_000;
    }

    @Override
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public <T> T value(String uniquenessGroup, Supplier<T> supplier) {
        Set<Object> usedValuesForMethod =
                usedValues.computeIfAbsent(uniquenessGroup, k -> ConcurrentHashMap.newKeySet());

        return provideUnique(uniquenessGroup, supplier, usedValuesForMethod);
    }

    /*
     * This method is synchronized because otherwise (in multi-threaded environments) 'contains' might return
     * false more than once for single key, leading to non-unique values being provided.
     */
    private synchronized <T> T provideUnique(String uniquenessGroup, Supplier<T> supplier,
                                             Set<Object> usedValuesForMethod) {
        for (int i = 0; i <= maxRetries; i++) {
            T result = supplier.get();
            if (!usedValuesForMethod.contains(result)) {
                usedValuesForMethod.add(result);
                return result;
            }
        }

        throw new UniqueValueRetryLimitExceededException(maxRetries, uniquenessGroup);
    }


    @Override
    public <T> void within(Supplier<T> supplier, Consumer<Supplier<T>> within) {
        Set<Object> usedValuesForMethod = new HashSet<>();
        within.accept(() -> provideUnique(null, supplier, usedValuesForMethod));
    }

    @Override
    public <T, E> E of(Supplier<T> supplier, Function<Supplier<T>, E> collector) {
        Set<Object> usedValuesForMethod = new HashSet<>();

        return collector.apply(() -> provideUnique(null, supplier, usedValuesForMethod));
    }
}
