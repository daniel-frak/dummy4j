package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.annotations.Experimental;
import dev.codesoapbox.dummy4j.convenience.ConvenienceMethods;
import dev.codesoapbox.dummy4j.definitions.DefaultUniqueValues;
import dev.codesoapbox.dummy4j.definitions.UniqueValues;
import dev.codesoapbox.dummy4j.definitions.providers.files.yaml.YamlFileDefinitionProvider;
import dev.codesoapbox.dummy4j.dummies.*;
import dev.codesoapbox.dummy4j.dummies.address.AddressDummy;
import dev.codesoapbox.dummy4j.dummies.color.ColorDummy;
import dev.codesoapbox.dummy4j.dummies.business.BusinessDummy;
import dev.codesoapbox.dummy4j.dummies.finance.FinanceDummy;
import dev.codesoapbox.dummy4j.dummies.identifier.IdentifierDummy;
import dev.codesoapbox.dummy4j.dummies.internet.InternetDummy;

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
    protected final ConvenienceMethods convenienceMethods;

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
        this.randomService = seed != null ? new DefaultRandomService(seed) : new DefaultRandomService();

        YamlFileDefinitionProvider definitionProvider = YamlFileDefinitionProvider.withPaths(paths);
        this.expressionResolver = new DefaultExpressionResolver(locales, this.randomService, definitionProvider);

        this.dummies = new Dummies(this);
        this.uniqueValues = new DefaultUniqueValues();
        this.convenienceMethods = new ConvenienceMethods(randomService);
    }

    public Dummy4j(ExpressionResolver expressionResolver, RandomService randomService) {
        this(expressionResolver, randomService, Dummies::new, new DefaultUniqueValues(),
                new ConvenienceMethods(randomService));
    }

    Dummy4j(ExpressionResolver expressionResolver, RandomService randomService,
                      Function<? super Dummy4j, Dummies> dummiesFactory, UniqueValues uniqueValues,
                      ConvenienceMethods convenienceMethods) {
        this.randomService = randomService;
        this.expressionResolver = expressionResolver;
        this.dummies = dummiesFactory.apply(this);
        this.uniqueValues = uniqueValues;
        this.convenienceMethods = convenienceMethods;
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
    @Experimental
    public UniqueValues unique() {
        return uniqueValues;
    }

    /**
     * Provides methods for generating random numbers
     */
    public NumberService number() {
        return randomService;
    }

    /**
     * Returns the seed used for random number generation
     *
     * @since 0.6.0
     */
    public Long getSeed() {
        return randomService.getSeed();
    }

    /**
     * @return a random boolean value
     */
    public boolean nextBoolean() {
        return randomService.nextBoolean();
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
        return convenienceMethods.listOf(count, supplier);
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
        return convenienceMethods.setOf(count, supplier);
    }

    /**
     * Returns a random element from an array
     *
     * @param array the array to pick from
     * @param <T>   the type of object to return
     * @return a random element
     * @since 0.5.0
     */
    @SafeVarargs
    public final <T> T of(T... array) {
        return convenienceMethods.of(array);
    }

    /**
     * Returns a random element from a list
     *
     * @param list the list to pick from
     * @param <T>  the type of object to return
     * @return a random element
     * @since 0.5.0
     */
    public <T> T of(List<T> list) {
        return convenienceMethods.of(list);
    }

    /**
     * Returns a random element from a set
     *
     * @param set the list to pick from
     * @param <T> the type of object to return
     * @return a random element
     * @since 0.5.0
     */
    public <T> T of(Set<T> set) {
        return convenienceMethods.of(set);
    }

    /**
     * Returns a value from a random supplier.
     *
     * @param suppliers value suppliers to pick from
     * @param <T>       the type of value to return
     * @return a value from a random supplier
     * @since 0.5.0
     */
    @SafeVarargs
    public final <T> T of(Supplier<T>... suppliers) {
        return convenienceMethods.of(suppliers);
    }

    /**
     * Has a {@code howMany} in {@code in} chance to supply a value. Otherwise, returns null.
     * <p>
     * E.g. {@code chance(1, 2, () -> "hello")} has a 1-in-2 chance to supply "hello", that is it will be supplied
     * 50% of the time when the method is invoked.
     *
     * @return supplied {@code T} or null
     * @since 0.5.0
     */
    public <T> T chance(int howMany, int in, Supplier<T> supplier) {
        return convenienceMethods.chance(howMany, in, supplier);
    }

    /**
     * Has a {@code howMany} in {@code in} chance to return {@code true}. Otherwise, returns {@code false}.
     * <p>
     * E.g. {@code chance(1, 2)} has a 1-in-2 chance to return {@code true}, that is it will return {@code true}
     * 50% of the time when the method is invoked.
     *
     * @return {@code boolean}
     * @since 0.5.0
     */
    public boolean chance(int howMany, int in) {
        return convenienceMethods.chance(howMany, in);
    }

    /**
     * Returns a random enum value
     *
     * @since 0.5.0
     */
    public <T extends Enum<?>> T nextEnum(Class<T> clazz) {
        return convenienceMethods.nextEnum(clazz);
    }

    /**
     * Finds all methods containing a search string in their name.
     * Helpful when a specific method is needed but its placement is unknown.
     *
     * @param value a string to search for
     * @return a message containing all found methods containing the search string
     * @since 0.10.0
     */
    public String find(String value) {
        return convenienceMethods.find(value);
    }

    /**
     * Provides methods for generating names
     */
    public NameDummy name() {
        return dummies.name();
    }

    /**
     * Provides methods for generating values related to countries, languages and nationalities
     *
     * @since 0.2.0
     */
    public NationDummy nation() {
        return dummies.nation();
    }

    /**
     * Provides methods for generating addresses
     */
    public AddressDummy address() {
        return dummies.address();
    }

    /**
     * Provides methods for generating text
     */
    public LoremDummy lorem() {
        return dummies.lorem();
    }

    /**
     * Provides methods for generating dates and times
     *
     * @since 0.4.0
     */
    public DateAndTimeDummy dateAndTime() {
        return dummies.dateAndTime();
    }

    /**
     * Provides methods for generating identifiers
     *
     * @since 0.5.0
     */
    public IdentifierDummy identifier() {
        return dummies.identifier();
    }

    /**
     * Provides methods for generating values related to education
     *
     * @since 0.3.0
     */
    public EducationDummy education() {
        return dummies.education();
    }

    /**
     * Provides methods for generating book-related values
     *
     * @since 0.2.0
     */
    public BookDummy book() {
        return dummies.book();
    }

    /**
     * Provides methods for generating values related to research papers
     *
     * @since 0.2.0
     */
    public ResearchPaperDummy researchPaper() {
        return dummies.researchPaper();
    }

    /**
     * Provides methods for generating science fiction themed values
     */
    public ScifiDummy scifi() {
        return dummies.scifi();
    }

    /**
     * Provides methods for generating colors
     *
     * @since 0.4.0
     */
    public ColorDummy color() {
        return dummies.color();
    }

    /**
     * Provides methods for generating numerals
     *
     * @since 0.4.0
     */
    public NumeralsDummy numerals() {
        return dummies.numerals();
    }

    /**
     * Provides methods for generating medical data
     *
     * @since 0.4.0
     */
    public MedicalDummy medical() {
        return dummies.medical();
    }

    /**
     * Provides methods for generating text in the NATO phonetic alphabet
     *
     * @since 0.4.0
     */
    public NatoPhoneticAlphabetDummy natoPhoneticAlphabet() {
        return dummies.natoPhoneticAlphabet();
    }

    /**
     * Provides methods for generating values related to the internet
     *
     * @since 0.5.0
     */
    public InternetDummy internet() {
        return dummies.internet();
    }

    /**
     * Provides methods for generating values related to finances
     *
     * @since 0.6.0
     */
    public FinanceDummy finance() {
        return dummies.finance();
    }

    /**
     * Provides methods for generating values related to business and commerce
     *
     * @since 0.10.0
     */
    public BusinessDummy business() {
        return dummies.business();
    }
}
