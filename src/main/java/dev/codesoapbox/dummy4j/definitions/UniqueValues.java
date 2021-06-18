package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.exceptions.UniqueValueRetryLimitExceededException;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Produces unique values
 *
 * @since 0.8.0
 */
public interface UniqueValues {

    /**
     * @param maxRetries how many times a random value may be generated during one invocation
     */
    void setMaxRetries(int maxRetries);

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
    <T> T value(String uniquenessGroup, Supplier<T> supplier);

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
     */
    <T> void within(Supplier<T> supplier, Consumer<Supplier<T>> within);

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
     */
    <T, E> E of(Supplier<T> supplier, Function<Supplier<T>, E> collector);
}
