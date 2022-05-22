package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.CheckDigit10AsXFormatter;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.InternationalNumberCheckDigitFormulaProvider;
import dev.codesoapbox.dummy4j.exceptions.InvalidDefinitionException;
import dev.codesoapbox.dummy4j.exceptions.InvalidIsbnParameterException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides methods for generating a random ISBN according to customizable parameters
 *
 * @since 0.9.0
 */
public class IsbnBuilder {

    static final Integer ISBN_BASE_LENGTH = 9;
    static final String ISBN_PREFIX_KEY = "#{identifier.isbn.prefix}";
    static final String ISBN_SEPARATOR_KEY = "#{identifier.isbn.separator}";

    /**
     * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Book_Number#Registration_group_identifier">
     * Registration group identifier</a>
     */
    static final List<IsbnRegistrationGroupRange> REGISTRATION_GROUP_RANGES = Arrays.asList(
            IsbnRegistrationGroupRange.from(0, 5),
            IsbnRegistrationGroupRange.from(600, 625),
            IsbnRegistrationGroupRange.from(65, 65),
            IsbnRegistrationGroupRange.from(7, 7),
            IsbnRegistrationGroupRange.from(80, 94),
            IsbnRegistrationGroupRange.from(950, 989),
            IsbnRegistrationGroupRange.from(9917, 9989),
            IsbnRegistrationGroupRange.from(99901, 99983)
    );

    private final Dummy4j dummy4j;
    private final IsbnValidator validator;
    private final InternationalNumberCheckDigitFormulaProvider checkDigitFormulaProvider;

    private List<IsbnType> types;
    private List<String> prefixes;
    private List<String> registrationGroups;
    private List<String> registrants;
    private List<String> publications;
    private List<String> separators;

    public IsbnBuilder(Dummy4j dummy4j, IsbnValidator validator,
                       InternationalNumberCheckDigitFormulaProvider checkDigitFormulaProvider) {
        this.dummy4j = dummy4j;
        this.validator = validator;
        this.checkDigitFormulaProvider = checkDigitFormulaProvider;
    }

    /**
     * Sets a random type for the generated ISBN (10 or 13 character length).
     * <p>
     * This is the default behavior for this builder.
     *
     * @see IsbnType
     */
    public IsbnBuilder withRandomType() {
        this.types = emptyList();

        return this;
    }

    /**
     * Sets the type for the generated ISBN to one that is randomly chosen from provided arguments
     *
     * @see IsbnType
     */
    public IsbnBuilder withRandomType(IsbnType... types) {
        this.types = asList(types);

        return this;
    }

    /**
     * Sets the type of the generated ISBN
     *
     * @see IsbnType
     */
    public IsbnBuilder withType(IsbnType type) {
        this.types = singletonList(type);

        return this;
    }

    /**
     * Sets a random prefix for the generated ISBN to one that is randomly chosen from the yml definitions.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsbnBuilder withRandomPrefix() {
        this.prefixes = emptyList();

        return this;
    }

    /**
     * Sets the prefix for the generated ISBN to one that is randomly chosen from provided arguments.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided arguments fail validation
     */
    public IsbnBuilder withRandomPrefix(String... prefixes) {
        Arrays.stream(prefixes)
                .forEach(validator::testForInvalidPrefix);
        this.prefixes = asList(prefixes);

        return this;
    }

    /**
     * Sets the prefix of the generated ISBN.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided argument fails validation
     */
    public IsbnBuilder withPrefix(String prefix) {
        validator.testForInvalidPrefix(prefix);
        this.prefixes = singletonList(prefix);

        return this;
    }

    /**
     * Sets a random registration group for the generated ISBN.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsbnBuilder withRandomRegistrationGroup() {
        this.registrationGroups = emptyList();

        return this;
    }

    /**
     * Sets the registration group for the generated ISBN to one that is randomly chosen from provided arguments.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided arguments fail validation
     */
    public IsbnBuilder withRandomRegistrationGroup(String... registrationGroups) {
        Arrays.stream(registrationGroups)
                .forEach(validator::testForInvalidRegistrationGroup);
        this.registrationGroups = asList(registrationGroups);

        return this;
    }

    /**
     * Sets the registration group of the generated ISBN.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided argument fails validation
     */
    public IsbnBuilder withRegistrationGroup(String group) {
        validator.testForInvalidRegistrationGroup(group);
        this.registrationGroups = singletonList(group);

        return this;
    }

    /**
     * Sets a random registrant for the generated ISBN.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsbnBuilder withRandomRegistrant() {
        this.registrants = emptyList();

        return this;
    }

    /**
     * Sets the registrant for the generated ISBN to one that is randomly chosen from provided arguments.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided arguments fail validation*
     */
    public IsbnBuilder withRandomRegistrant(String... registrants) {
        Arrays.stream(registrants)
                .forEach(validator::testForInvalidOtherPart);
        this.registrants = asList(registrants);

        return this;
    }

    /**
     * Sets the registrant of the generated ISBN.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided argument fails validation
     */
    public IsbnBuilder withRegistrant(String registrant) {
        validator.testForInvalidOtherPart(registrant);
        this.registrants = singletonList(registrant);

        return this;
    }

    /**
     * Sets a random publication for the generated ISBN.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsbnBuilder withRandomPublication() {
        this.publications = emptyList();

        return this;
    }

    /**
     * Sets the publication for the generated ISBN to one that is randomly chosen from provided arguments.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided arguments fail validation
     */
    public IsbnBuilder withRandomPublication(String... publications) {
        Arrays.stream(publications)
                .forEach(validator::testForInvalidOtherPart);
        this.publications = asList(publications);

        return this;
    }

    /**
     * Sets the publication of the generated ISBN.
     * <p>
     * The builder tries to adjust the length of the remaining auto-generated ISBN elements
     * so that the valid ISBN length is preserved.
     * <p>
     * However, combining custom prefixes, registration groups, registrants or publications can result in invalid ISBN
     * if their combined length would not produce a valid ISBN and the builder would have no auto-generated elements
     * left to trim (or trimming them would still not produce a valid ISBN length.
     * <p>
     * Therefore, it is advised to make sure that every combination of parameters passed to this builder would produce
     * an ISBN of valid length.
     *
     * @throws InvalidIsbnParameterException when the provided argument fails validation
     */
    public IsbnBuilder withPublication(String publication) {
        validator.testForInvalidOtherPart(publication);
        this.publications = singletonList(publication);

        return this;
    }

    /**
     * Sets a separator for the generated ISBN to one that is randomly chosen from the yml definitions.
     * <p>
     * This is the default behavior for this builder.
     */
    public IsbnBuilder withRandomSeparator() {
        this.separators = emptyList();

        return this;
    }

    /**
     * Sets the separator for the generated ISBN to one that is randomly chosen from provided arguments
     */
    public IsbnBuilder withRandomSeparator(String... separators) {
        this.separators = asList(separators);

        return this;
    }

    /**
     * Sets the separator used for formatting ISBN
     */
    public IsbnBuilder withSeparator(String separator) {
        this.separators = singletonList(separator);

        return this;
    }

    /**
     * The generated ISBN will contain only digits and no separator
     */
    public IsbnBuilder withoutSeparator() {
        this.separators = singletonList("");

        return this;
    }

    /**
     * Generates a random ISBN
     */
    public Isbn build() {
        IsbnType type = resolveType();
        String prefix = resolvePrefix(type);
        String group = resolveRegistrationGroup();
        String publication = dummy4j.oneOf(publications);
        String registrant = resolveRegistrant(group.length(), publication);

        if (publication == null) {
            publication = dummy4j.number().digits(ISBN_BASE_LENGTH - (group.length() + registrant.length()));
        }

        String separator = resolveSeparator();
        String checkDigit = generateCheckDigit(type, prefix + group + registrant + publication);

        return new Isbn(type, prefix, group, registrant, publication, separator, checkDigit);
    }

    private IsbnType resolveType() {
        return Optional.ofNullable(dummy4j.oneOf(types))
                .orElse(dummy4j.nextEnum(IsbnType.class));
    }

    private String resolvePrefix(IsbnType type) {
        if (!type.requiresPrefix()) {
            return "";
        }

        return Optional.ofNullable(dummy4j.oneOf(prefixes))
                .orElseGet(this::resolvePrefixDefinition);
    }

    private String resolvePrefixDefinition() {
        String prefix = dummy4j.expressionResolver().resolve(ISBN_PREFIX_KEY);
        try {
            validator.testForInvalidPrefix(prefix);
        } catch (InvalidIsbnParameterException e) {
            throw new InvalidDefinitionException(ISBN_PREFIX_KEY, e, prefix);
        }

        return prefix;
    }

    private String resolveRegistrationGroup() {
        return Optional.ofNullable(dummy4j.oneOf(registrationGroups))
                .orElseGet(this::generateRegistrationGroup);
    }

    private String generateRegistrationGroup() {
        IsbnRegistrationGroupRange range = dummy4j.oneOf(REGISTRATION_GROUP_RANGES);
        int group = dummy4j.number().nextInt(range.getMin(), range.getMax());

        return String.valueOf(group);
    }

    private String resolveRegistrant(int registrationGroupLength, String publication) {
        return Optional.ofNullable(dummy4j.oneOf(registrants))
                .orElseGet(() -> generateRegistrant(registrationGroupLength, publication));
    }

    private String generateRegistrant(Integer registrationGroupLength, String publication) {
        if (publication != null) {
            return generateRegistrantBasedOnPublication(registrationGroupLength, publication);
        } else {
            return generateRegistrantBasedOnRemainingSpace(registrationGroupLength);
        }
    }

    private String generateRegistrantBasedOnPublication(Integer registrationGroupLength, String publication) {
        int howMany = ISBN_BASE_LENGTH - (registrationGroupLength + publication.length());
        if (howMany <= 0) {
            return "";
        }
        return dummy4j.number().digits(howMany);
    }

    private String generateRegistrantBasedOnRemainingSpace(Integer registrationGroupLength) {
        int max = ISBN_BASE_LENGTH - (registrationGroupLength + 1);
        if (max <= 0) {
            return "";
        }
        int howMany = ISBN_BASE_LENGTH - (registrationGroupLength + dummy4j.number().nextInt(1, max));
        return dummy4j.number().digits(howMany);
    }

    private String resolveSeparator() {
        return Optional.ofNullable(dummy4j.oneOf(separators))
                .orElseGet(() -> dummy4j.expressionResolver().resolve(ISBN_SEPARATOR_KEY));
    }

    private String generateCheckDigit(IsbnType type, String input) {
        if (type == IsbnType.ISBN_10) {
            Integer digit = checkDigitFormulaProvider
                    .getModElevenFormula()
                    .generateCheckDigit(input);

            return CheckDigit10AsXFormatter.formatCheckDigit(digit);
        }

        return String.valueOf(checkDigitFormulaProvider
                .getModTenFormula()
                .generateCheckDigit(input));
    }

    @Override
    public String toString() {
        return "IsbnBuilder{" +
                "isbnType=" + types +
                ", prefixes=" + prefixes +
                ", registrationGroups=" + registrationGroups +
                ", registrants=" + registrants +
                ", publications=" + publications +
                ", separators=" + separators +
                '}';
    }
}
