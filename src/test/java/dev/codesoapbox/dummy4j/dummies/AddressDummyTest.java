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
class AddressDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    private AddressDummy addressDummy;

    @BeforeEach
    void setUp() {
        addressDummy = new AddressDummy(dummy4j);
        mockExpressionResolver();
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void city() {
        when(expressionResolver.resolve("#{address.city}"))
                .thenReturn("City");

        assertEquals("City", addressDummy.city());
    }

    @Test
    void postCode() {
        when(expressionResolver.resolve("#{address.postcode}"))
                .thenReturn("11");

        assertEquals("11", addressDummy.postCode());
    }

    @Test
    void street() {
        when(expressionResolver.resolve("#{address.street}"))
                .thenReturn("5 Street");

        assertEquals("5 Street", addressDummy.street());
    }

    @Test
    void country() {
        when(expressionResolver.resolve("#{nation.country}"))
                .thenReturn("Country");

        assertEquals("Country", addressDummy.country());
    }

    @Test
    void countryCode() {
        when(expressionResolver.resolve("#{nation.country_code}"))
                .thenReturn("es");

        assertEquals("es", addressDummy.countryCode());
    }
}