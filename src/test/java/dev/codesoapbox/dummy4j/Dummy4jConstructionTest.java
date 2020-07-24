package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.UniqueValues;
import dev.codesoapbox.dummy4j.dummies.Dummies;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class Dummy4jConstructionTest {

    @Test
    void shouldConstructWithDefaultValues() {
        Dummy4j dummy = new Dummy4j();
        assertNotNull(dummy.dummies);
        assertNotNull(dummy.expressionResolver);
        assertNotNull(dummy.numberService);
        assertEquals(dummy.numberService, dummy.expressionResolver.numberService);
    }

    @Test
    void shouldConstructWithCustomSeedAndLocales() {
        List<String> locales = singletonList("en");
        long seed = 1234L;
        Dummy4j dummy = new Dummy4j(seed, locales, null);
        assertEquals(locales, dummy.expressionResolver.locales);
        assertEquals(seed, dummy.numberService.getSeed());
    }

    @Test
    void shouldConstructWithSeedAndDefaultLocales() {
        long seed = 1234L;
        Dummy4j dummy = new Dummy4j(seed, null, null);
        assertEquals(singletonList("en"), dummy.expressionResolver.locales);
        assertEquals(seed, dummy.numberService.getSeed());
    }

    @Test
    void shouldConstructWithDefaultSeedAndCustomLocales() {
        List<String> locales = singletonList("en");
        Dummy4j dummy = new Dummy4j(null, locales, null);
        assertEquals(locales, dummy.expressionResolver.locales);
    }

    @Test
    void shouldConstructWithBasicCustomDependencies() {
        ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
        NumberService numberService = mock(NumberService.class);

        Dummy4j dummy = new Dummy4j(expressionResolver, numberService);

        assertEquals(expressionResolver, dummy.expressionResolver);
        assertEquals(numberService, dummy.numberService);
        assertNotNull(dummy.dummies);
        assertNotNull(dummy.expressionResolver);
        assertNotNull(dummy.numberService);
    }

    @Test
    void shouldConstructWithCustomDependencies() {
        ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
        NumberService numberService = mock(NumberService.class);
        Dummies dummies = mock(Dummies.class);
        UniqueValues uniqueValues = mock(UniqueValues.class);
        ConvenienceMethods convenienceMethods = mock(ConvenienceMethods.class);

        Dummy4j dummy = new Dummy4j(expressionResolver, numberService, d -> dummies, uniqueValues, convenienceMethods);

        assertEquals(expressionResolver, dummy.expressionResolver);
        assertEquals(numberService, dummy.numberService);
        assertEquals(dummies, dummy.dummies);
    }

}