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
class ResearchPaperDummyTest {

    @Mock
    private Dummy4j dummy4j;
    
    @Mock
    private ExpressionResolver expressionResolver;
    
    private ResearchPaperDummy researchPaperDummy;

    @BeforeEach
    void setUp() {
        researchPaperDummy = new ResearchPaperDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void title() {
        when(expressionResolver.resolve("#{research_paper.title}"))
                .thenReturn("Title");
        assertEquals("Title", researchPaperDummy.title());
    }

    @Test
    void titleSocial() {
        when(expressionResolver.resolve("#{research_paper.title_social}"))
                .thenReturn("Title social");
        assertEquals("Title social", researchPaperDummy.titleSocial());
    }

    @Test
    void titleAnthropology() {
        when(expressionResolver.resolve("#{research_paper.title_anthropology}"))
                .thenReturn("Title anthropology");
        assertEquals("Title anthropology", researchPaperDummy.titleAnthropology());
    }

    @Test
    void titleHistory() {
        when(expressionResolver.resolve("#{research_paper.title_history}"))
                .thenReturn("Title history");
        assertEquals("Title history", researchPaperDummy.titleHistory());
    }

    @Test
    void titleNatural() {
        when(expressionResolver.resolve("#{research_paper.title_natural}"))
                .thenReturn("Title natural");
        assertEquals("Title natural", researchPaperDummy.titleNatural());
    }

    @Test
    void titlePhysics() {
        when(expressionResolver.resolve("#{research_paper.title_physics}"))
                .thenReturn("Title physics");
        assertEquals("Title physics", researchPaperDummy.titlePhysics());
    }

    @Test
    void titleBiology() {
        when(expressionResolver.resolve("#{research_paper.title_biology}"))
                .thenReturn("Title biology");
        assertEquals("Title biology", researchPaperDummy.titleBiology());
    }
}