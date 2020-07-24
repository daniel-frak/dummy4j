package dev.codesoapbox.dummy4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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
     * @since 0.4.0
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
     * @since 0.4.0
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
     *
     * @since 0.5.0
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
     *
     * @since 0.5.0
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
     *
     * @since 0.5.0
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
     *
     * @since 0.4.0
     */
    @SafeVarargs
    public final <T> T of(Supplier<T>... suppliers) {
        return suppliers[random.nextInt(0, suppliers.length - 1)].get();
    }
}
