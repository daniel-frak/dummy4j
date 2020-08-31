package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    private MedicalDummy medicalDummy;

    @BeforeEach
    void setUp() {
        medicalDummy = new MedicalDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void shouldReturnValidBloodType() {
        when(expressionResolver.resolve("#{medical.blood_type}"))
                .thenReturn("0+");
        assertEquals("0+", medicalDummy.bloodType());
    }

    @Test
    void shouldReturnDiscipline() {
        when(expressionResolver.resolve("#{medical.discipline}"))
                .thenReturn("Urology");
        assertEquals("Urology", medicalDummy.discipline());
    }

    @Test
    void shouldReturnOccupation() {
        when(expressionResolver.resolve("#{medical.occupation}"))
                .thenReturn("Neurosurgeon");
        assertEquals("Neurosurgeon", medicalDummy.occupation());
    }
}