package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EducationDummyTest {

    @Mock
    private Dummy4j dummy4j;
    
    @Mock
    private ExpressionResolver expressionResolver;
    
    private EducationDummy educationDummy;

    @BeforeEach
    void setUp() {
        educationDummy = new EducationDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
        
    }

    @Test
    void major() {
        when(expressionResolver.resolve("#{education.major}"))
                .thenReturn("Major");
        assertEquals("Major", educationDummy.major());
    }

    @Test
    void primaryInstitution() {
        when(expressionResolver.resolve("#{education.primary_institution}"))
                .thenReturn("Primary");
        assertEquals("Primary", educationDummy.primaryInstitution());
    }

    @Test
    void secondaryInstitution() {
        when(expressionResolver.resolve("#{education.secondary_institution}"))
                .thenReturn("Secondary");
        assertEquals("Secondary", educationDummy.secondaryInstitution());
    }

    @Test
    void tertiaryInstitution() {
        when(expressionResolver.resolve("#{education.tertiary_institution}"))
                .thenReturn("Tertiary");
        assertEquals("Tertiary", educationDummy.tertiaryInstitution());
    }

    @Test
    void institution() {
        when(expressionResolver.resolve("#{education.institution}"))
                .thenReturn("Institution");
        assertEquals("Institution", educationDummy.institution());
    }

    @Test
    void degree() {
        when(expressionResolver.resolve("#{education.degree}"))
                .thenReturn("Degree");
        assertEquals("Degree", educationDummy.degree());
    }

    @Test
    void courseNumber() {
        when(expressionResolver.resolve("#{education.course_number}"))
                .thenReturn("11");
        assertEquals("11", educationDummy.courseNumber());
    }
}