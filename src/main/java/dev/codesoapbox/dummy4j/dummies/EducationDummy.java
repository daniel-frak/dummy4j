package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating values related to education
 *
 * @since 0.3.0
 */
public class EducationDummy {

    private final Dummy4j dummy4j;

    public EducationDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Provides a discipline chosen at random from a set of formal, natural and social sciences
     */
    public String major() {
        return dummy4j.expressionResolver().resolve("#{education.major}");
    }

    /**
     * Generates a random institution providing primary education.
     * E.g. {@code New Cristopher Primary School}
     */
    public String primaryInstitution() {
        return dummy4j.expressionResolver().resolve("#{education.primary_institution}");
    }

    /**
     * Generates a random institution providing secondary education.
     * E.g. {@code Turnerburgh High}
     */
    public String secondaryInstitution() {
        return dummy4j.expressionResolver().resolve("#{education.secondary_institution}");
    }

    /**
     * Generates a random institution providing third-level education.
     * E.g. {@code Wilfredoburgh University}
     */
    public String tertiaryInstitution() {
        return dummy4j.expressionResolver().resolve("#{education.tertiary_institution}");
    }

    /**
     * Generates a random educational institution name
     */
    public String institution() {
        return dummy4j.expressionResolver().resolve("#{education.institution}");
    }

    /**
     * Generates a random degree in a scientific field
     */
    public String degree() {
        return dummy4j.expressionResolver().resolve("#{education.degree}");
    }

    /**
     * Generates a random course number
     */
    public String courseNumber() {
        return dummy4j.expressionResolver().resolve("#{education.course_number}");
    }
}
