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
     * Return a list of all possible values for a given key.
     *
     * @param key the key to resolve
     * @return a list of all values assigned to the key
     */
    List<String> resolve(String key);
}
