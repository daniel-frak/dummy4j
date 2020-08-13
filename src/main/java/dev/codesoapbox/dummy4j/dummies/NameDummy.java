package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating names
 */
public class NameDummy {

    private final Dummy4j dummy4j;

    public NameDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Provides a random name prefix.
     * E.g. {@code Mrs.}
     */
    public String prefix() {
        return dummy4j.expressionResolver().resolve("#{name.prefix}");
    }

    /**
     * Provides a random name suffix.
     * E.g. {@code Jr.}
     */
    public String suffix() {
        return dummy4j.expressionResolver().resolve("#{name.suffix}");
    }

    /**
     * Provides a random first name
     */
    public String firstName() {
        return dummy4j.expressionResolver().resolve("#{name.first_name}");
    }

    /**
     * Provides a random last name
     */
    public String lastName() {
        return dummy4j.expressionResolver().resolve("#{name.last_name}");
    }

    /**
     * Generates a random name consisting of a first name, a last name and, occasionally, a prefix or suffix.
     * E.g. {@code Mr. Braden Freeman}
     */
    public String fullName() {
        return dummy4j.expressionResolver().resolve("#{name.full_name}");
    }

    /**
     * Generates a random name consisting of a first name, a middle name, a last name and, occasionally,
     * a prefix or suffix.
     * E.g. {@code Niki Faulkner Durham}
     */
    public String fullNameWithMiddle() {
        return dummy4j.expressionResolver().resolve("#{name.full_name_with_middle}");
    }
}
