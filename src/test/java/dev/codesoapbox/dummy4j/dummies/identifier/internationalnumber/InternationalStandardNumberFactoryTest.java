package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.Gs1Dash128Factory;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.SsccFactory;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.UpcFactory;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.IsbnValidator;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.IsmnValidator;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.BasicIsniProvider;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.serialnumber.IssnFactory;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.LuhnFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternationalStandardNumberFactoryTest {

    private InternationalStandardNumberFactory factory;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private InternationalNumberCheckDigitFormulaProvider checkDigitFormulaProvider;

    @Mock
    private IsbnValidator isbnValidator;

    @Mock
    private IsmnValidator ismnValidator;

    @Mock
    private IssnFactory issnFactory;

    @Mock
    private BasicIsniProvider basicIsniProvider;

    @Mock
    private UpcFactory upcFactory;

    @Mock
    private Gs1Dash128Factory gs1Dash128Factory;

    @Mock
    private SsccFactory ssccFactory;

    @Mock
    private DeviceIdentifierFactory deviceIdentifierFactory;

    @BeforeEach
    void setUp() {
        factory = new InternationalStandardNumberFactory(dummy4j, checkDigitFormulaProvider, isbnValidator,
                ismnValidator, issnFactory, basicIsniProvider, upcFactory, gs1Dash128Factory, ssccFactory,
                deviceIdentifierFactory);
    }

    @Test
    void shouldCreateNewInstance() {
        assertNotNull(factory);
    }

    @Test
    void shouldGetIssn() {
        when(issnFactory.createIssn())
                .thenReturn("123");

        String actual = factory.createIssn();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateIsbnBuilder() {
        assertNotNull(factory.createIsbnBuilder());
    }

    @Test
    void shouldCreateIsmnBuilder() {
        assertNotNull(factory.createIsmnBuilder());
    }

    @Test
    void shouldCreateIsniBuilder() {
        assertNotNull(factory.createIsniBuilder());
    }

    @Test
    void shouldCreateOrcidBuilder() {
        assertNotNull(factory.createOrcidBuilder());
    }

    @Test
    void shouldCreateEan8Builder() {
        assertNotNull(factory.createGtin8Builder());
    }

    @Test
    void shouldCreateEan13Builder() {
        assertNotNull(factory.createGtin13Builder());
    }

    @Test
    void shouldCreateEan14Builder() {
        assertNotNull(factory.createGtin14Builder());
    }

    @Test
    void shouldCreateUniversalProductNumber() {
        when(upcFactory.createUpc())
                .thenReturn("123");

        String actual = factory.createUpc();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateGs1Dash128() {
        when(gs1Dash128Factory.createCode())
                .thenReturn("123");

        String actual = factory.createGs1Dash128();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateSSCC() {
        when(ssccFactory.createCode())
                .thenReturn("123");

        String actual = factory.createSscc();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateTac() {
        when(deviceIdentifierFactory.createTac())
                .thenReturn("123");

        String actual = deviceIdentifierFactory.createTac();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateImei() {
        LuhnFormula luhnFormula = mock(LuhnFormula.class);
        when(deviceIdentifierFactory.createImei(luhnFormula))
                .thenReturn("123");

        String actual = deviceIdentifierFactory.createImei(luhnFormula);

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateImeisv() {
        when(deviceIdentifierFactory.createImeisv())
                .thenReturn("123");

        String actual = deviceIdentifierFactory.createImeisv();

        assertEquals("123", actual);
    }
}