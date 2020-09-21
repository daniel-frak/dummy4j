package dev.codesoapbox.dummy4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An abstract dummy builder which can optionally be extended to build classes extending Dummy4j.
 *
 * @see Dummy4jBuilder
 * @since 0.3.1
 */
public abstract class AbstractDummy4jBuilder<T extends AbstractDummy4jBuilder<T, E>, E> {

    protected Long seed;
    protected List<String> locale;
    protected List<String> paths;

    public AbstractDummy4jBuilder() {
    }

    public T seed(Long seed) {
        self().seed = seed;
        return self();
    }

    protected abstract T self();

    public T locale(List<String> locale) {
        self().locale = locale;
        return self();
    }

    public T paths(List<String> paths) {
        self().paths = paths;
        return self();
    }

    /**
     * @since SNAPSHOT
     */
    public T locale(String... locale) {
        if (theOnlyElementIsNull(locale)) {
            self().locale = new ArrayList<>();
        } else {
            self().locale = Arrays.asList(locale);
        }
        return self();
    }

    private boolean theOnlyElementIsNull(String[] elements) {
        return elements.length == 1 && elements[0] == null;
    }

    /**
     * @since SNAPSHOT
     */
    public T paths(String... paths) {
        if (theOnlyElementIsNull(paths)) {
            self().paths = new ArrayList<>();
        } else {
            self().paths = Arrays.asList(paths);
        }
        return self();
    }

    public abstract E build();
}
