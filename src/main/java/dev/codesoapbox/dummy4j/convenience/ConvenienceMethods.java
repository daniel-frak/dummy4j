package dev.codesoapbox.dummy4j.convenience;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.RandomService;

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
    private final MethodFinder methodFinder;

    public ConvenienceMethods(RandomService random) {
        this.random = random;
        this.methodFinder = new MethodFinder(Dummy4j.class, new MethodPathLoader());
    }

    ConvenienceMethods(RandomService random, MethodFinder methodFinder) {
        this.random = random;
        this.methodFinder = methodFinder;
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
     * @param <T>   the type of object to return
     * @return a random element from the array or NULL if the array is empty
     */
    @SafeVarargs
    public final <T> T of(T... array) {
        if (array.length == 0) {
            return null;
        } else if (array.length == 1) {
            return array[0];
        }
        return array[random.nextInt(array.length - 1)];
    }

    /**
     * Returns a random element from a list
     *
     * @param list the list to pick from
     * @param <T>  the type of object to return
     * @return a random element from the list or NULL if the list is NULL or empty
     */
    public <T> T of(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        }
        return list.get(random.nextInt(list.size() - 1));
    }

    /**
     * Returns a random element from a set
     *
     * @param set the list to pick from
     * @param <T> the type of object to return
     * @return a random element from the set or NULL if the set is NULL or empty
     */
    @SuppressWarnings("unchecked")
    public <T> T of(Set<T> set) {
        if (set == null || set.isEmpty()) {
            return null;
        } else if (set.size() == 1) {
            return (T) set.toArray()[0];
        }
        return (T) set.toArray()[random.nextInt(set.size() - 1)];
    }

    /**
     * Returns a value from a random supplier.
     *
     * @param suppliers value suppliers to pick from
     * @param <T>       the type of value to return
     * @return a value from a random supplier or NULL if an empty array or NULL was given as an argument
     */
    @SafeVarargs
    public final <T> T of(Supplier<T>... suppliers) {
        if (suppliers == null || suppliers.length == 0) {
            return null;
        } else if (suppliers.length == 1) {
            return suppliers[0].get();
        }
        return suppliers[random.nextInt(suppliers.length - 1)].get();
    }

    /**
     * Has a {@code howMany} in {@code in} chance to supply a value. Otherwise, returns null.
     * <p>
     * E.g. {@code chance(1, 2, () -> "hello")} has a 1-in-2 chance to supply "hello", that is it will be supplied
     * 50% of the time when the method is invoked.
     *
     * @return supplied {@code T} or null
     * @since 0.5.0
     */
    public <T> T chance(int howMany, int in, Supplier<T> supplier) {
        if (random.nextInt(1, in) > howMany) {
            return null;
        }

        return supplier.get();
    }

    /**
     * Has a {@code howMany} in {@code in} chance to return {@code true}. Otherwise, returns {@code false}.
     * <p>
     * E.g. {@code chance(1, 2)} has a 1-in-2 chance to return {@code true}, that is it will return {@code true}
     * 50% of the time when the method is invoked.
     *
     * @return {@code boolean}
     * @since 0.5.0
     */
    public boolean chance(int howMany, int in) {
        return random.nextInt(1, in) <= howMany;
    }

    /**
     * Returns a random enum value
     */
    public <T extends Enum<?>> T nextEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length - 1);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Finds all methods containing a search string in their name.
     * Helpful when a specific method is needed but its placement is unknown.
     *
     * @param value a string to search for
     * @return a message containing all found methods containing the search string
     * @since 0.10.0
     */
    public String find(String value) {
        return methodFinder.find(value);
    }
}
