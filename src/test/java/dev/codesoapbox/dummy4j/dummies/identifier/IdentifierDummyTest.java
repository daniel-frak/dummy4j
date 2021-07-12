package dev.codesoapbox.dummy4j.dummies.identifier;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.LoremDummy;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.InternationalStandardNumberFactory;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.Gtin13Builder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.Gtin14Builder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.Gtin8Builder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.Isbn;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.IsbnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.IsbnMother;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.Ismn;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.IsmnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.IsmnMother;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.IsniBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.OrcidBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdentifierDummyTest {

    private IdentifierDummy identifierDummy;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NumberService numberService;

    @Mock
    private InternationalStandardNumberFactory internationalStandardNumberFactory;

    @Mock
    private IsbnBuilder isbnBuilder;

    @Mock
    private IsmnBuilder ismnBuilder;

    @Mock
    private IsniBuilder isniBuilder;

    @Mock
    private OrcidBuilder orcidBuilder;

    @Mock
    private Gtin8Builder gtin8Builder;

    @Mock
    private Gtin13Builder gtin13Builder;

    @Mock
    private Gtin14Builder gtin14Builder;

    @Mock
    private LoremDummy loremDummy;

    @BeforeEach
    void setUp() {
        identifierDummy = new IdentifierDummy(dummy4j, internationalStandardNumberFactory);
    }

    @Test
    void shouldGetRandomUuid() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong())
                .thenReturn(1L);

        String expected = "c4ca4238-a0b9-3382-8dcc-509a6f75849b";

        assertEquals(expected, identifierDummy.uuid().toString());
    }

    @Test
    void shouldGetIssn() {
        when(internationalStandardNumberFactory.createIssn())
                .thenReturn("1234-567X");

        String actual = identifierDummy.issn();

        assertEquals("1234-567X", actual);
    }

    @Test
    void shouldGetIsbn() {
        when(internationalStandardNumberFactory.createIsbnBuilder())
                .thenReturn(isbnBuilder);
        Isbn expected = IsbnMother.isbn();
        when(isbnBuilder.build())
                .thenReturn(expected);

        Isbn actual = identifierDummy.isbn();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetIsbnBuilder() {
        when(internationalStandardNumberFactory.createIsbnBuilder())
                .thenReturn(isbnBuilder);

        assertNotNull(identifierDummy.isbnBuilder());
    }

    @Test
    void shouldGetIsmn() {
        when(internationalStandardNumberFactory.createIsmnBuilder())
                .thenReturn(ismnBuilder);
        Ismn expected = IsmnMother.ismn();
        when(ismnBuilder.build())
                .thenReturn(expected);

        Ismn actual = identifierDummy.ismn();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetIsmnBuilder() {
        when(internationalStandardNumberFactory.createIsmnBuilder())
                .thenReturn(ismnBuilder);

        assertNotNull(identifierDummy.ismnBuilder());
    }

    @Test
    void shouldReturnIsni() {
        when(internationalStandardNumberFactory.createIsniBuilder())
                .thenReturn(isniBuilder);
        when(isniBuilder.build())
                .thenReturn("123");

        String actual = identifierDummy.isni();

        assertEquals("123", actual);
    }

    @Test
    void shouldReturnIsniBuilder() {
        when(internationalStandardNumberFactory.createIsniBuilder())
                .thenReturn(isniBuilder);

        IsniBuilder actual = identifierDummy.isniBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnOrcid() {
        when(internationalStandardNumberFactory.createOrcidBuilder())
                .thenReturn(orcidBuilder);
        when(orcidBuilder.build())
                .thenReturn("123");

        String actual = identifierDummy.orcid();

        assertEquals("123", actual);
    }

    @Test
    void shouldReturnOrcidBuilder() {
        when(internationalStandardNumberFactory.createOrcidBuilder())
                .thenReturn(orcidBuilder);

        OrcidBuilder actual = identifierDummy.orcidBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldGetEan8() {
        when(internationalStandardNumberFactory.createGtin8Builder())
                .thenReturn(gtin8Builder);
        when(gtin8Builder.build())
                .thenReturn("111");

        String actual = identifierDummy.ean8();

        assertEquals("111", actual);
    }

    @Test
    void shouldGetGtin8() {
        when(internationalStandardNumberFactory.createGtin8Builder())
                .thenReturn(gtin8Builder);
        when(gtin8Builder.build())
                .thenReturn("111");

        String actual = identifierDummy.gtin8();

        assertEquals("111", actual);
    }

    @Test
    void shouldGetEan8Builder() {
        when(internationalStandardNumberFactory.createGtin8Builder())
                .thenReturn(gtin8Builder);

        Gtin8Builder actual = identifierDummy.ean8Builder();

        assertNotNull(actual);
    }

    @Test
    void shouldGetGtin8Builder() {
        when(internationalStandardNumberFactory.createGtin8Builder())
                .thenReturn(gtin8Builder);

        Gtin8Builder actual = identifierDummy.gtin8Builder();

        assertNotNull(actual);
    }

    @Test
    void shouldGetEan13() {
        when(internationalStandardNumberFactory.createGtin13Builder())
                .thenReturn(gtin13Builder);
        when(gtin13Builder.build())
                .thenReturn("111");

        String actual = identifierDummy.ean13();

        assertEquals("111", actual);
    }

    @Test
    void shouldGetGtin13() {
        when(internationalStandardNumberFactory.createGtin13Builder())
                .thenReturn(gtin13Builder);
        when(gtin13Builder.build())
                .thenReturn("111");

        String actual = identifierDummy.gtin13();

        assertEquals("111", actual);
    }

    @Test
    void shouldGetGtin13Builder() {
        when(internationalStandardNumberFactory.createGtin13Builder())
                .thenReturn(gtin13Builder);

        Gtin13Builder actual = identifierDummy.gtin13Builder();

        assertNotNull(actual);
    }

    @Test
    void shouldGetEan13Builder() {
        when(internationalStandardNumberFactory.createGtin13Builder())
                .thenReturn(gtin13Builder);

        Gtin13Builder actual = identifierDummy.ean13Builder();

        assertNotNull(actual);
    }

    @Test
    void shouldGetEan14() {
        when(internationalStandardNumberFactory.createGtin14Builder())
                .thenReturn(gtin14Builder);
        when(gtin14Builder.build())
                .thenReturn("111");

        String actual = identifierDummy.ean14();

        assertEquals("111", actual);
    }

    @Test
    void shouldGetGtin14() {
        when(internationalStandardNumberFactory.createGtin14Builder())
                .thenReturn(gtin14Builder);
        when(gtin14Builder.build())
                .thenReturn("111");

        String actual = identifierDummy.gtin14();

        assertEquals("111", actual);
    }

    @Test
    void shouldGetEan14Builder() {
        when(internationalStandardNumberFactory.createGtin14Builder())
                .thenReturn(gtin14Builder);

        Gtin14Builder actual = identifierDummy.ean14Builder();

        assertNotNull(actual);
    }

    @Test
    void shouldGetGtin14Builder() {
        when(internationalStandardNumberFactory.createGtin14Builder())
                .thenReturn(gtin14Builder);

        Gtin14Builder actual = identifierDummy.gtin14Builder();

        assertNotNull(actual);
    }

    @Test
    void shouldGetUpn() {
        when(internationalStandardNumberFactory.createUpc())
                .thenReturn("123");

        String actual = identifierDummy.upc();

        assertEquals("123", actual);
    }

    @Test
    void shouldGetGtin12() {
        when(internationalStandardNumberFactory.createUpc())
                .thenReturn("123");

        String actual = identifierDummy.gtin12();

        assertEquals("123", actual);
    }

    @Test
    void shouldGetGs1Dash128() {
        when(internationalStandardNumberFactory.createGs1Dash128())
                .thenReturn("123");

        String actual = identifierDummy.gs1Dash128();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateSscc() {
        when(internationalStandardNumberFactory.createSscc())
                .thenReturn("123");

        String actual = identifierDummy.sscc();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateAsin() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(10, 99_999))
                .thenReturn(10);

        when(dummy4j.lorem())
                .thenReturn(loremDummy);
        when(loremDummy.characters(5))
                .thenReturn("ąbćdę");

        String actual = identifierDummy.asin();

        assertEquals("B00ABCDE10", actual);
    }

    @Test
    void shouldCreateTac() {
        when(internationalStandardNumberFactory.createTac())
                .thenReturn("123");

        String actual = identifierDummy.tac();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateImei() {
        when(internationalStandardNumberFactory.createImei())
                .thenReturn("123");

        String actual = identifierDummy.imei();

        assertEquals("123", actual);
    }

    @Test
    void shouldCreateImeisv() {
        when(internationalStandardNumberFactory.createImeisv())
                .thenReturn("123");

        String actual = identifierDummy.imeisv();

        assertEquals("123", actual);
    }
}