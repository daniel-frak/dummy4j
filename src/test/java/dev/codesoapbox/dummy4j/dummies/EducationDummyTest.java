package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EducationDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void major() {
        String value = dummy4j.education().major();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void primaryInstitution() {
        String value = dummy4j.education().primaryInstitution();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void secondaryInstitution() {
        String value = dummy4j.education().secondaryInstitution();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void tertiaryInstitution() {
        String value = dummy4j.education().tertiaryInstitution();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void institution() {
        String value = dummy4j.education().institution();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void degree() {
        String value = dummy4j.education().degree();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void courseNumber() {
        String value = dummy4j.education().courseNumber();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}