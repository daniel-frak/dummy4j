package dev.codesoapbox.dummy4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @since 0.5.0
 */
public class ConvenienceMethods {

    private final RandomService random;

    public ConvenienceMethods(RandomService random) {
        this.random = random;
    }

    /**
     * Provides a list of objects supplied by a method
     *
     * @param count    the number of objects to generate
     * @param supplier the method to generate an object
     * @param <T>      the type of objects to generate
     * @return a list of objects
     */
    public <T> List<T> listOf(int count, Supplier<T> supplier) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * Provides a set of objects supplied by a method
     *
     * @param count    the number of objects to generate
     * @param supplier the method to generate an object
     * @param <T>      the type of objects to generate
     * @return a list of objects
     */
    public <T> Set<T> setOf(int count, Supplier<T> supplier) {
        Set<T> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            set.add(supplier.get());
        }
        return set;
    }

    /**
     * Returns a random element from an array
     *
     * @param array the array to pick from
     * @param <T> the type of object to return
     * @return a random element
     */
    @SafeVarargs
    public final <T> T of(T... array) {
        return array[random.nextInt(array.length - 1)];
    }

    /**
     * Returns a random element from a list
     *
     * @param list the list to pick from
     * @param <T> the type of object to return
     * @return a random element
     */
    public <T> T of(List<T> list) {
        return list.get(random.nextInt(list.size() - 1));
    }

    /**
     * Returns a random element from a set
     *
     * @param set the list to pick from
     * @param <T> the type of object to return
     * @return a random element
     */
    @SuppressWarnings("unchecked")
    public <T> T of(Set<T> set) {
        return (T) set.toArray()[random.nextInt(set.size() - 1)];
    }

    /**
     * Returns a value from a random supplier.
     *
     * @param suppliers value suppliers to pick from
     * @param <T> the type of value to return
     * @return a value from a random supplier
     */
    @SafeVarargs
    public final <T> T of(Supplier<T>... suppliers) {
        return suppliers[random.nextInt(0, suppliers.length - 1)].get();
    }

    /**
     * Has a {@code howMany} in {@code in} chance to supply a value. Otherwise, returns null.
     * <p>
     * E.g. {@code chance(1, 2, () -> "hello")} has a 1-in-2 chance to supply "hello", that is it will be supplied
     * 50% of the time when the method is invoked.
     *
     * @return supplied {@code T} or null
     */
    public <T> T chance(int howMany, int in, Supplier<T> supplier) {
        if (random.nextInt(1, in) > howMany) {
            return null;
        }

        return supplier.get();
    }

    /**
     * Returns a random enum value
     */
    public <T extends Enum<?>> T nextEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length - 1);
        return clazz.getEnumConstants()[x];
    }
}
