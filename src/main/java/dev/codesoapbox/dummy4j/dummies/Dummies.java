package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

public class Dummies {

    private final NameDummy name;
    private final AddressDummy address;
    private final LoremDummy lorem;
    private final ScifiDummy spaceship;

    public Dummies(Dummy4j dummy4j) {
        name = new NameDummy(dummy4j);
        address = new AddressDummy(dummy4j);
        lorem = new LoremDummy(dummy4j);
        spaceship = new ScifiDummy(dummy4j);
    }

    public NameDummy name() {
        return name;
    }

    public AddressDummy address() {
        return address;
    }

    public LoremDummy lorem() {
        return lorem;
    }

    public ScifiDummy scifi()  {
        return spaceship;
    }
}
