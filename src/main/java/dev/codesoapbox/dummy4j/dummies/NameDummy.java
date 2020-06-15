package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

public class NameDummy {

    private final Dummy4j dummy4j;

    public NameDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String prefix() {
        return dummy4j.getExpressionResolver().resolve("#{name.prefix}");
    }

    public String suffix() {
        return dummy4j.getExpressionResolver().resolve("#{name.suffix}");
    }

    public String firstName() {
        return dummy4j.getExpressionResolver().resolve("#{name.first_name}");
    }

    public String lastName() {
        return dummy4j.getExpressionResolver().resolve("#{name.last_name}");
    }

    public String fullName() {
        return dummy4j.getExpressionResolver().resolve("#{name.full_name}");
    }

    public String fullNameWithMiddle() {
        return dummy4j.getExpressionResolver().resolve("name.full_name_with_middle");
    }
}
