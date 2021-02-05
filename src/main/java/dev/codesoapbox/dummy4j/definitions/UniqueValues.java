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
 * Produces unique values
 *
 * @since 0.1.2
 */
@Experimental
public class UniqueValues {

    private final Map<String, Set<Object>> usedValues;
    private int maxRetries;

    public UniqueValues() {
        this.usedValues = new ConcurrentHashMap<>();
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
     * <p>
     * <b>The unique values will be referenced in memory for the lifetime of Dummy4j.</b>
     * <p></p>
     * E.g. the following code:
     * <pre>{@code
     * String color1 = dummy4j.unique().value("colors", () -> dummy4j.color().basicName());
     * String color2 = dummy4j.unique().value("colors", () -> dummy4j.color().basicName());
     * }</pre>
     * will guarantee that the values of {@code color1} and {@code color2} will always be different from each other.
     *
     * @param uniquenessGroup id of the group within which the generated value should be unique
     * @param supplier        the value supplier
     * @param <T>             the type of value to return
     * @return a unique value
     * @throws UniqueValueRetryLimitExceededException if retry limit is exceeded
     */
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

    /**
     * Guarantees supplied values to be unique within the context of the consumer's code.
     * <p>
     * <b>The unique values will be referenced in memory only until the execution of {@code within} is completed.</b>
     * <p></p>
     * E.g. the following code:
     * <pre>{@code
     * dummy.unique().within(() -> dummy.color().basicName(), color -> {
     *             String color1 = color.get();
     *             String color2 = color.get();
     *         });
     * }</pre>
     * will guarantee that the values of {@code color1} and {@code color2} will always be different from each other.
     *
     * @param supplier the value supplier
     * @param within   the code within which the supplied values will be unique
     * @param <T>      the type of value to return
     * @throws UniqueValueRetryLimitExceededException if retry limit is exceeded
     * @since 0.7.0
     */
    public <T> void within(Supplier<T> supplier, Consumer<Supplier<T>> within) {
        Set<Object> usedValuesForMethod = new HashSet<>();
        within.accept(() -> provideUnique(null, supplier, usedValuesForMethod));
    }

    /**
     * Allows for the creation of collections of unique values.
     * <p>
     * Values supplied within the collector function are guaranteed to be unique.
     * <p>
     * <b>The unique values will be referenced in memory only until the execution of {@code collector} is completed.</b>
     * <p></p>
     * E.g. the following code:
     * <pre>{@code
     * List<String> colors = dummy.unique().of(() -> dummy.color().basicName(), color -> dummy.listOf(2, color));
     * }</pre>
     * will guarantee that the values of {@code color} will always be unique within the context of their list.
     *
     * @param supplier  the value supplier
     * @param collector the collector within which the supplied values will be unique
     * @param <T>       the type of value to return
     * @throws UniqueValueRetryLimitExceededException if retry limit is exceeded
     * @since 0.7.0
     */
    public <T, E> E of(Supplier<T> supplier, Function<Supplier<T>, E> collector) {
        Set<Object> usedValuesForMethod = new HashSet<>();

        return collector.apply(() -> provideUnique(null, supplier, usedValuesForMethod));
    }
}
