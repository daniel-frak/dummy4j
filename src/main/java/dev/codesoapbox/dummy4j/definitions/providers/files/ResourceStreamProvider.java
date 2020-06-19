package dev.codesoapbox.dummy4j.definitions.providers.files;

import java.io.InputStream;

/**
 * Provides resource files as InputStreams
 */
public class ResourceStreamProvider {

    public InputStream get(String resource) {
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(resource);
    }
}
