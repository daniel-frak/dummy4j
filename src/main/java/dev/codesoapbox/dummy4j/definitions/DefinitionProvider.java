package dev.codesoapbox.dummy4j.definitions;

import java.util.List;

public interface DefinitionProvider {

    /**
     * Returns a list of definitions for dummy data, grouped by locale.
     *
     * @return a list of localized dummy definitions
     */
    List<LocalizedDummyDefinitions> get();
}
