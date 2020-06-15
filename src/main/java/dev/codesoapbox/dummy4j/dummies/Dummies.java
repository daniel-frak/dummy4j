package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

public class Dummies {

    private final NameDummy name;
    private final SpaceshipDummy spaceship;

    public Dummies(Dummy4j dummy4j) {
        name = new NameDummy(dummy4j);
        spaceship = new SpaceshipDummy(dummy4j);
    }

    public NameDummy name() {
        return name;
    }

    public SpaceshipDummy spaceship()  {
        return spaceship;
    }
}
