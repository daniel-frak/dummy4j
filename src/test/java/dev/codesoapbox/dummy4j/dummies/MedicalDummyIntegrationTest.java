package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class MedicalDummyIntegrationTest {

    private static final Pattern BLOOD_TYPE_PATTERN = Pattern.compile("(A|B|AB|O)[+-]");

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void shouldReturnValidBloodType() {
        assertTrue(BLOOD_TYPE_PATTERN.matcher(dummy4j.medical().bloodType()).matches());
    }

    @Test
    void shouldReturnDiscipline() {
        String value = dummy4j.medical().discipline();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void shouldReturnOccupation() {
        String value = dummy4j.medical().occupation();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}