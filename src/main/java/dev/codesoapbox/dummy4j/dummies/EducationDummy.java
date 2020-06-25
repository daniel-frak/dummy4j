package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * @since 0.3.0
 */
public class EducationDummy {

    private final Dummy4j dummy4j;

    public EducationDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String major() {
        return dummy4j.expressionResolver().resolveKey("education.major");
    }

    public String primaryInstitution() {
        return dummy4j.expressionResolver().resolve("#{education.primary_institution}");
    }

    public String secondaryInstitution() {
        return dummy4j.expressionResolver().resolve("#{education.secondary_institution}");
    }

    public String tertiaryInstitution() {
        return dummy4j.expressionResolver().resolve("#{education.tertiary_institution}");
    }

    public String institution() {
        return dummy4j.expressionResolver().resolve("#{education.institution}");
    }

    public String degree() {
        return dummy4j.expressionResolver().resolve("#{education.degree}");
    }

    public String courseNumber() {
        return dummy4j.expressionResolver().resolve("#{education.course_number}");
    }
}
