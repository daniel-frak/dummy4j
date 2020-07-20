package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.UniqueValues;
import dev.codesoapbox.dummy4j.definitions.providers.files.yaml.YamlFileDefinitionProvider;
import dev.codesoapbox.dummy4j.dummies.*;
import dev.codesoapbox.dummy4j.dummies.color.ColorDummy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides methods for generating dummy data.
 * <p>
 * For best performance, a single instance of this class should be created and reused throughout the code.
 */
public class Dummy4j {

    protected final ExpressionResolver expressionResolver;
    protected final RandomService randomService;
    protected final Dummies dummies;
    protected final UniqueValues uniqueValues;

    /**
     * Create a Dummy4j instance with a default configuration
     */
    public Dummy4j() {
        this(null, null, null);
    }

    /**
     * This constructor is primarily used for Dummy4j's builder.
     * It is used instead of passing the entire builder instance for better extensibility.
     *
     * @param seed    used for generating random values
     * @param locales a list of locales, ordered by priority of resolution
     * @param paths   a list of paths from which to load yml files (relative to the resources directory)
     * @since 0.3.0
     */
    public Dummy4j(Long seed, List<String> locales, List<String> paths) {
        this.randomService = seed != null ? new RandomService(seed) : new RandomService();

        YamlFileDefinitionProvider definitionProvider;
        if (paths == null) {
            definitionProvider = YamlFileDefinitionProvider.standard();
        } else {
            definitionProvider = YamlFileDefinitionProvider.withPaths(paths);
        }

        if (locales != null) {
            this.expressionResolver = new ExpressionResolver(locales, this.randomService,
                    definitionProvider);
        } else {
            this.expressionResolver = new ExpressionResolver(null, this.randomService,
                    definitionProvider);
        }

        this.dummies = new Dummies(this);
        this.uniqueValues = new UniqueValues();
    }

    public Dummy4j(ExpressionResolver expressionResolver, RandomService randomService,
                   Function<? super Dummy4j, Dummies> dummiesFactory, UniqueValues uniqueValues) {
        this.randomService = randomService;
        this.expressionResolver = expressionResolver;
        this.dummies = dummiesFactory.apply(this);
        this.uniqueValues = uniqueValues;
    }

    /**
     * @return the resolver used for resolving expressions
     */
    public ExpressionResolver expressionResolver() {
        return expressionResolver;
    }

    /**
     * Provides methods for generating unique values
     *
     * @since 0.1.2
     */
    public UniqueValues unique() {
        return uniqueValues;
    }

    /**
     * Provides methods for generating random values
     */
    public RandomService random() {
        return randomService;
    }

    /**
     * Provides a list of objects supplied by a method
     *
     * @param count    the number of objects to generate
     * @param supplier the method to generate an object
     * @param <T>      the type of objects to generate
     * @return a list of objects
     * @since 0.4.0
     */
    public <T> List<T> listOf(int count, Supplier<T> supplier) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * Provides a set of objects supplied by a method
     *
     * @param count    the number of objects to generate
     * @param supplier the method to generate an object
     * @param <T>      the type of objects to generate
     * @return a list of objects
     * @since 0.4.0
     */
    public <T> Set<T> setOf(int count, Supplier<T> supplier) {
        Set<T> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            set.add(supplier.get());
        }
        return set;
    }

    /**
     * Provides methods for generating random names
     */
    public NameDummy name() {
        return dummies.name();
    }

    public NationDummy nation() {
        return dummies.nation();
    }

    /**
     * Provides methods for generating random addresses
     */
    public AddressDummy address() {
        return dummies.address();
    }

    /**
     * Provides methods for generating random text
     */
    public LoremDummy lorem() {
        return dummies.lorem();
    }

    /**
     * Provides methods for generating random dates and times
     *
     * @since 0.4.0
     */
    public DateAndTimeDummy dateAndTime() {
        return dummies.dateAndTime();
    }

    /**
     * Provides methods for generating random values related to education
     *
     * @since 0.3.0
     */
    public EducationDummy education() {
        return dummies.education();
    }

    /**
     * Provides methods for generating random book-related values
     *
     * @since 0.2.0
     */
    public BookDummy book() {
        return dummies.book();
    }

    /**
     * Provides methods for generating random values related to research papers
     *
     * @since 0.2.0
     */
    public ResearchPaperDummy researchPaper() {
        return dummies.researchPaper();
    }

    /**
     * Provides methods for generating random science fiction themed values
     */
    public ScifiDummy scifi() {
        return dummies.scifi();
    }

    /**
     * Provides methods for generating random colors
     *
     * @since 0.4.0
     */
    public ColorDummy color() {
        return dummies.color();
    }

    /**
     * Provides methods for generating random numerals
     *
     * @since 0.4.0
     */
    public NumeralsDummy numerals() {
        return dummies.numerals();
    }
}
