package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.LuhnFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceIdentifierFactoryTest {

    private DeviceIdentifierFactory factory;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private LuhnFormula luhnFormula;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private NumberService numberService;

    @BeforeEach
    void setUp() {
        factory = new DeviceIdentifierFactory(dummy4j);
        mockTac();
    }

    private void mockTac() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
        when(expressionResolver.resolve(DeviceIdentifierFactory.REPORTING_BODY_ID_KEY))
                .thenReturn("01");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.digits(DeviceIdentifierFactory.TAC_ID_LENGTH))
                .thenReturn("123");
    }

    @Test
    void shouldCreateTac() {
        String actual = factory.createTac();

        assertEquals("01-123", actual);
    }

    @Test
    void shouldCreateImei() {
        when(luhnFormula.generateCheckDigit("01-123-123"))
                .thenReturn("2");

        String actual = factory.createImei(luhnFormula);

        assertEquals("01-123-123-2", actual);
    }

    @Test
    void shouldCreateImeisv() {
        when(numberService.digits(DeviceIdentifierFactory.SOFTWARE_VERSION_LENGTH))
                .thenReturn("45");

        String actual = factory.createImeisv();

        assertEquals("01-123-123-45", actual);
    }
}