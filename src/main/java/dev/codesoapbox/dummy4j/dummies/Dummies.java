package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.color.ColorDummy;
import dev.codesoapbox.dummy4j.dummies.internet.InternetDummy;

import java.time.Clock;

public class Dummies {

    private final NameDummy name;
    private final NationDummy nation;
    private final AddressDummy address;
    private final LoremDummy lorem;
    private final DateAndTimeDummy dateAndTime;
    private final IdentifierDummy identifier;
    private final EducationDummy education;
    private final BookDummy book;
    private final ResearchPaperDummy researchPaper;
    private final ScifiDummy spaceship;
    private final ColorDummy color;
    private final NumeralsDummy numerals;
    private final MedicalDummy medical;
    private final NatoPhoneticAlphabetDummy natoPhoneticAlphabet;
    private final InternetDummy internet;

    public Dummies(Dummy4j dummy4j) {
        name = new NameDummy(dummy4j);
        nation = new NationDummy(dummy4j);
        address = new AddressDummy(dummy4j);
        lorem = new LoremDummy(dummy4j);
        dateAndTime = new DateAndTimeDummy(dummy4j, Clock.systemDefaultZone());
        identifier = new IdentifierDummy(dummy4j);
        education = new EducationDummy(dummy4j);
        book = new BookDummy(dummy4j);
        researchPaper = new ResearchPaperDummy(dummy4j);
        spaceship = new ScifiDummy(dummy4j);
        color = new ColorDummy(dummy4j);
        numerals = new NumeralsDummy(dummy4j);
        medical = new MedicalDummy(dummy4j);
        natoPhoneticAlphabet = new NatoPhoneticAlphabetDummy(dummy4j);
        internet = new InternetDummy(dummy4j);
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
     * @since 0.4.0
     */
    public DateAndTimeDummy dateAndTime() {
        return dateAndTime;
    }

    /**
     * @since 0.5.0
     */
    public IdentifierDummy identifier() {
        return identifier;
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

    /**
     * @since 0.4.0
     */
    public NumeralsDummy numerals() {
        return numerals;
    }

    /**
     * @since 0.4.0
     */
    public MedicalDummy medical() {
        return medical;
    }

    /**
     * @since 0.4.0
     */
    public NatoPhoneticAlphabetDummy natoPhoneticAlphabet() {
        return natoPhoneticAlphabet;
    }

    /**
     * @since 0.5.0
     */
    public InternetDummy internet() {
        return internet;
    }
}
