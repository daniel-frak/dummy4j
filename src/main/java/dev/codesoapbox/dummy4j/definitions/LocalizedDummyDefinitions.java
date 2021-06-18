package dev.codesoapbox.dummy4j.definitions;

import java.util.List;

/**
 * A collection of resolvable dummy data definitions for a given locale.
 */
public interface LocalizedDummyDefinitions {

    /**
     * @return the locale of the definitions
     */
    String getLocale();

    /**
     * Returns a list of all possible values for a given path.
     * <p>
     * Since 0.8.0:
     * <p>
     * If the path exists but does not resolve to a value or list of values, returns a list
     * of all keys contained directly within it.
     *
     * @param path the path to resolve
     * @return a list of all values assigned to the path or a list of all keys contained within it
     */
    List<String> resolve(String path);
}
