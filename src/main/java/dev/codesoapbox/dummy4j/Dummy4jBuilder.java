package dev.codesoapbox.dummy4j;

import java.util.List;

/**
 * @since 0.3.0
 */
public class Dummy4jBuilder {

    private Long seed;
    private List<String> locale;
    private List<String> paths;

    Dummy4jBuilder() {
    }

    public Dummy4jBuilder seed(Long seed) {
        this.seed = seed;
        return this;
    }

    public Dummy4jBuilder locale(List<String> locale) {
        this.locale = locale;
        return this;
    }

    public Dummy4jBuilder paths(List<String> paths) {
        this.paths = paths;
        return this;
    }

    public Dummy4j build() {
        return new Dummy4j(seed, locale, paths);
    }
}
