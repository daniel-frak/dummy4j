package dev.codesoapbox.dummy4j.definitions;

import java.util.List;

public interface LocalizedDummyDefinitions {

    String getLocale();

    List<String> resolve(String key);
}
