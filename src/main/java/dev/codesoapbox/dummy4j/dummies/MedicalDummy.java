package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating medical data
 * @since 0.4.0
 */
public class MedicalDummy {

    private final Dummy4j dummy4j;

    public MedicalDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Provides a random blood type
     */
    public String bloodType() {
        return dummy4j.expressionResolver().resolve("#{medical.blood_type}");
    }

    /**
     * Provides a random medical discipline
     */
    public String discipline() {
        return dummy4j.expressionResolver().resolve("#{medical.discipline}");
    }

    /**
     * Provides a random job position in medicine
     */
    public String occupation() {
        return dummy4j.expressionResolver().resolve("#{medical.occupation}");
    }
}
