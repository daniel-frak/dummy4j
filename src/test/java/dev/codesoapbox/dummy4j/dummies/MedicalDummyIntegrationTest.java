package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicalDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void shouldReturnValidBloodType() {
        assertEquals("A+", dummy4j.medical().bloodType());
    }

    @Test
    void shouldReturnDiscipline() {
        assertEquals("Anesthesiology", dummy4j.medical().discipline());
    }

    @Test
    void shouldReturnOccupation() {
        assertEquals("Certified Emergency Registered Nurse", dummy4j.medical().occupation());
    }
}