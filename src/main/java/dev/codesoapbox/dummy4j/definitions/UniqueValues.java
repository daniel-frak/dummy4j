package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.annotations.Experimental;
import dev.codesoapbox.dummy4j.exceptions.RetryLimitExceeded;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Produces unique values
 *
 * @since 0.1.2
 */
@Experimental
public class UniqueValues {

    private final Map<String, Set<Object>> usedValues;
    private int maxRetries;

    public UniqueValues() {
        this.usedValues = new HashMap<>();
        this.maxRetries = 10_000;
    }

    /**
     * @param maxRetries how many times a random value may be generated during one invocation
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    /**
     * Invokes supplier until it returns a value unique within {@code uniquenessGroup} or the max retry limit is
     * reached.
     *
     * @param uniquenessGroup id of the group within which the generated value should be unique
     * @param supplier the value supplier
     * @param <T> the type of value to return
     * @return a unique value
     * @throws RetryLimitExceeded if retry limit is exceeded
     */
    public <T> T value(String uniquenessGroup, Supplier<T> supplier) {
        Set<Object> usedValuesForMethod = usedValues.computeIfAbsent(uniquenessGroup, k -> new HashSet<>());

        for (int i = 0; i <= maxRetries; i++) {
            T result = supplier.get();
            if (!usedValuesForMethod.contains(result)) {
                usedValuesForMethod.add(result);

                return result;
            }
        }

        throw new RetryLimitExceeded();
    }
}
