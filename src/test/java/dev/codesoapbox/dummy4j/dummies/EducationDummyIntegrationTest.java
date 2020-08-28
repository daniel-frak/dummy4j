package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EducationDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void major() {
        assertEquals("Arts", dummy4j.education().major());
    }

    @Test
    void primaryInstitution() {
        assertEquals("North Zoeshire Academy", dummy4j.education().primaryInstitution());
    }

    @Test
    void secondaryInstitution() {
        assertEquals("North Zoeshire Secondary College", dummy4j.education().secondaryInstitution());
    }

    @Test
    void tertiaryInstitution() {
        assertEquals("North Zoeshire University", dummy4j.education().tertiaryInstitution());
    }

    @Test
    void institution() {
        assertEquals("North Zoeshire University", dummy4j.education().institution());
    }

    @Test
    void degree() {
        assertEquals("Master of Arts", dummy4j.education().degree());
    }

    @Test
    void courseNumber() {
        assertEquals("104", dummy4j.education().courseNumber());
    }
}