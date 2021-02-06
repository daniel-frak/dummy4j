package dev.codesoapbox.dummy4j.definitions;

import java.util.List;
import java.util.Set;

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
     *
     * @param path the path to resolve
     * @return a list of all values assigned to the path
     */
    List<String> resolve(String path);

    /**
     * Returns a list of all keys contained directly within a given path.
     *
     * @param path the path whose sub-keys should be returned
     * @return a list of keys
     * @since SNAPSHOT
     */
    Set<String> getKeysFor(String path);
}
