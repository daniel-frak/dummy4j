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
class ScifiDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    private ScifiDummy scifiDummy;

    @BeforeEach
    void setUp() {
        scifiDummy = new ScifiDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void name() {
        when(expressionResolver.resolve("#{scifi.spaceship.name}"))
                .thenReturn("Normandy");
        assertEquals("Normandy", scifiDummy.spaceship());
    }

}