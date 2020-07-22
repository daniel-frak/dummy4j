package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * @since 0.4.0
 */
public class MedicalDummy {

    private final Dummy4j dummy4j;

    public MedicalDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String bloodType() {
        return dummy4j.expressionResolver().resolveKey("medical.blood_type");
    }

    public String discipline() {
        return dummy4j.expressionResolver().resolveKey("medical.discipline");
    }

    public String occupation() {
        return dummy4j.expressionResolver().resolveKey("medical.occupation");
    }
}
