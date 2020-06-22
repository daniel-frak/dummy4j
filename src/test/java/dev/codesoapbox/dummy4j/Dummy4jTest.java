package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.UniqueValues;
import dev.codesoapbox.dummy4j.dummies.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Dummy4jTest {

    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private RandomService randomService;

    @Mock
    private Dummies dummies;

    @Mock
    private UniqueValues uniqueValues;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j(expressionResolver, randomService, d -> dummies, uniqueValues);
    }

    @Test
    void shouldGetExpressionResolver() {
        assertEquals(expressionResolver, dummy4j.getExpressionResolver());
    }

    @Test
    void shouldGetUnique() {
        assertEquals(uniqueValues, dummy4j.unique());
    }

    @Test
    void shouldGetRandomService() {
        assertEquals(randomService, dummy4j.random());
    }

    @Test
    void shouldGetNameDummy() {
        NameDummy nameDummy = mock(NameDummy.class);
        when(dummies.name())
                .thenReturn(nameDummy);
        assertEquals(nameDummy, dummy4j.name());
    }

    @Test
    void shouldGetAddressDummy() {
        AddressDummy addressDummy = mock(AddressDummy.class);
        when(dummies.address())
                .thenReturn(addressDummy);
        assertEquals(addressDummy, dummy4j.address());
    }

    @Test
    void shouldGetLoremDummy() {
        LoremDummy loremDummy = mock(LoremDummy.class);
        when(dummies.lorem())
                .thenReturn(loremDummy);
        assertEquals(loremDummy, dummy4j.lorem());
    }

    @Test
    void shouldGetScifiDummy() {
        ScifiDummy scifiDummy = mock(ScifiDummy.class);
        when(dummies.scifi())
                .thenReturn(scifiDummy);
        assertEquals(scifiDummy, dummy4j.scifi());
    }
}