package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.UniqueValues;
import dev.codesoapbox.dummy4j.dummies.*;
import dev.codesoapbox.dummy4j.dummies.color.ColorDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(expressionResolver, dummy4j.expressionResolver());
    }

    @Test
    void shouldGetUnique() {
        assertEquals(uniqueValues, dummy4j.unique());
    }

    @Test
    void shouldGenerateRandomList() {
        List<Integer> expectedResult = Arrays.asList(1, 2, 3, 4, 5);

        AtomicInteger i = new AtomicInteger();
        List<Integer> result = dummy4j.listOf(5, i::incrementAndGet);

        assertEquals(expectedResult, result);
    }

    @Test
    void shouldGenerateRandomSet() {
        Set<Integer> expectedResult = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            expectedResult.add(i + 1);
        }

        AtomicInteger i = new AtomicInteger();
        Set<Integer> result = dummy4j.setOf(5, i::incrementAndGet);

        assertEquals(expectedResult, result);
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
    void shouldGetNationDummy() {
        NationDummy nationDummy = mock(NationDummy.class);
        when(dummies.nation())
                .thenReturn(nationDummy);
        assertEquals(nationDummy, dummy4j.nation());
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
    void shouldGetEducationDummy() {
        EducationDummy educationDummy = mock(EducationDummy.class);
        when(dummies.education())
                .thenReturn(educationDummy);
        assertEquals(educationDummy, dummy4j.education());
    }

    @Test
    void shouldGetScifiDummy() {
        ScifiDummy scifiDummy = mock(ScifiDummy.class);
        when(dummies.scifi())
                .thenReturn(scifiDummy);
        assertEquals(scifiDummy, dummy4j.scifi());
    }

    @Test
    void shouldGetColorDummy() {
        ColorDummy colorDummy = mock(ColorDummy.class);
        when(dummies.color())
                .thenReturn(colorDummy);
        assertEquals(colorDummy, dummy4j.color());
    }

    @Test
    void shouldGetNumeralsDummy() {
        NumeralsDummy numeralsDummy = mock(NumeralsDummy.class);
        when(dummies.numerals())
                .thenReturn(numeralsDummy);
        assertEquals(numeralsDummy, dummy4j.numerals());
    }
}