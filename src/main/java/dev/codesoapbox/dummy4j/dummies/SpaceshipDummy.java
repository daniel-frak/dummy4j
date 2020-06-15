package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

public class SpaceshipDummy {

    private final Dummy4j dummy4j;

    public SpaceshipDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String name() {
        return dummy4j.getExpressionResolver().resolve("#{spaceship.name}");
    }
}
