package dev.codesoapbox.dummy4j;

/**
 * A default builder for Dummy4j
 *
 * @since 0.3.0
 */
public class Dummy4jBuilder extends AbstractDummy4jBuilder<Dummy4jBuilder, Dummy4j> {

    @Override
    protected Dummy4jBuilder self() {
        return this;
    }

    public Dummy4j build() {
        return new Dummy4j(seed, locale, paths);
    }
}
