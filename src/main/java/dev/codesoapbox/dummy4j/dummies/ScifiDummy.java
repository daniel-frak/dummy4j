package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

public class ScifiDummy {

    private final Dummy4j dummy4j;

    public ScifiDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String spaceship() {
        return dummy4j.getExpressionResolver().resolve("#{scifi.spaceship.name}");
    }
}
