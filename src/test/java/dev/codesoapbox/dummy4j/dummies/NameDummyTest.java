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
class NameDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    private NameDummy nameDummy;

    @BeforeEach
    void setUp() {
        nameDummy = new NameDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void prefix() {
        when(expressionResolver.resolve("#{name.prefix}"))
                .thenReturn("Mrs.");
        assertEquals("Mrs.", nameDummy.prefix());
    }

    @Test
    void suffix() {
        when(expressionResolver.resolve("#{name.suffix}"))
                .thenReturn("Jr.");
        assertEquals("Jr.", nameDummy.suffix());
    }

    @Test
    void firstName() {
        when(expressionResolver.resolve("#{name.first_name}"))
                .thenReturn("Andy");
        assertEquals("Andy", nameDummy.firstName());
    }

    @Test
    void lastName() {
        when(expressionResolver.resolve("#{name.last_name}"))
                .thenReturn("Anderson");
        assertEquals("Anderson", nameDummy.lastName());
    }

    @Test
    void fullName() {
        when(expressionResolver.resolve("#{name.full_name}"))
                .thenReturn("Dr. Zoe Anderson");
        assertEquals("Dr. Zoe Anderson", nameDummy.fullName());
    }

    @Test
    void fullNameWithMiddle() {
        when(expressionResolver.resolve("#{name.full_name_with_middle}"))
                .thenReturn("Dr. Zoe Abbott Anderson");
        assertEquals("Dr. Zoe Abbott Anderson", nameDummy.fullNameWithMiddle());
    }
}