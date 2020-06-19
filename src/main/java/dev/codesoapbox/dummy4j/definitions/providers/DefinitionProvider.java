package dev.codesoapbox.dummy4j.definitions.providers;

import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;

import java.util.List;

public interface DefinitionProvider {

    /**
     * Returns a list of definitions for dummy data, grouped by locale.
     *
     * @return a list of localized dummy definitions
     */
    List<LocalizedDummyDefinitions> get();
}
