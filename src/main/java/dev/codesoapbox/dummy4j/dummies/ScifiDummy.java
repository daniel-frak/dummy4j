package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating science fiction themed values
 */
public class ScifiDummy {

    private final Dummy4j dummy4j;

    public ScifiDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Provides a random space vessel name
     */
    public String spaceship() {
        return dummy4j.expressionResolver().resolve("#{scifi.spaceship.name}");
    }
}
