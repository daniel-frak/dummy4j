package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.color.ColorDummy;

public class Dummies {

    private final NameDummy name;
    private final NationDummy nation;
    private final AddressDummy address;
    private final LoremDummy lorem;
    private final EducationDummy education;
    private final BookDummy book;
    private final ResearchPaperDummy researchPaper;
    private final ScifiDummy spaceship;
    private final ColorDummy color;

    public Dummies(Dummy4j dummy4j) {
        name = new NameDummy(dummy4j);
        nation = new NationDummy(dummy4j);
        address = new AddressDummy(dummy4j);
        lorem = new LoremDummy(dummy4j);
        education = new EducationDummy(dummy4j);
        book = new BookDummy(dummy4j);
        researchPaper = new ResearchPaperDummy(dummy4j);
        spaceship = new ScifiDummy(dummy4j);
        color = new ColorDummy(dummy4j);
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

    /**
     * @since 0.2.0
     */
    public BookDummy book() {
        return book;
    }

    /**
     * @since 0.2.0
     */
    public ResearchPaperDummy researchPaper() {
        return researchPaper;
    }

    public ScifiDummy scifi() {
        return spaceship;
    }

    /**
     * @since 0.2.0
     */
    public NationDummy nation() {
        return nation;
    }

    public EducationDummy education() {
        return education;
    }

    /**
     * @since 0.4.0
     */
    public ColorDummy color() {
        return color;
    }
}
