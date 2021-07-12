package dev.codesoapbox.dummy4j.dummies.identifier;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.Isbn;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.IsbnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.Ismn;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.IsmnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.IsniBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.OrcidBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierDummyIntegrationTest {

    private static final Pattern ORCID_PATTERN = Pattern.compile("(\\d{4}-\\d{4}-\\d{4}-\\d{3}(?:\\d|X))");
    private static final Pattern ASIN_PATTERN = Pattern.compile("B00[A-Z,0-9]{7}");
    private static final Pattern TAC_PATTERN = Pattern.compile("\\d{2}[-]\\d{6}");
    private static final Pattern IMEI_PATTERN = Pattern.compile("\\d{2}[-]\\d{6}[-]\\d{6}[-]\\d");
    private static final Pattern IMEISV_PATTERN = Pattern.compile("\\d{2}[-]\\d{6}[-]\\d{6}[-]\\d{2}");

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void shouldReturnUUID() {
        UUID actual = dummy4j.identifier().uuid();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnIssn() {
        String actual = dummy4j.identifier().issn();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnIsbn() {
        Isbn actual = dummy4j.identifier().isbn();

        assertNotNull(actual);
        assertAll(
                () -> assertNotNull(actual.getType()),
                () -> assertNotNull(actual.getPrefix()),
                () -> assertNotNull(actual.getRegistrationGroup()),
                () -> assertFalse(actual.getRegistrationGroup().isEmpty()),
                () -> assertNotNull(actual.getRegistrant()),
                () -> assertFalse(actual.getRegistrant().isEmpty()),
                () -> assertNotNull(actual.getPublication()),
                () -> assertFalse(actual.getPublication().isEmpty()),
                () -> assertNotNull(actual.getCheckDigit()),
                () -> assertFalse(actual.getCheckDigit().isEmpty()),
                () -> assertNotNull(actual.getSeparator())
        );
    }

    @Test
    void shouldReturnIsbnBuilder() {
        IsbnBuilder actual = dummy4j.identifier().isbnBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnIsmn() {
        Ismn actual = dummy4j.identifier().ismn();

        assertNotNull(actual);
        assertAll(
                () -> assertNotNull(actual.getRegistrant()),
                () -> assertFalse(actual.getRegistrant().isEmpty()),
                () -> assertNotNull(actual.getItem()),
                () -> assertFalse(actual.getItem().isEmpty()),
                () -> assertNotNull(actual.getCheckDigit()),
                () -> assertFalse(actual.getCheckDigit().isEmpty()),
                () -> assertNotNull(actual.getSeparator())
        );
    }

    @Test
    void shouldReturnIsmnBuilder() {
        IsmnBuilder actual = dummy4j.identifier().ismnBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnIsni() {
        String actual = dummy4j.identifier().isni();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnIsniBuilder() {
        IsniBuilder actual = dummy4j.identifier().isniBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnOrcid() {
        String actual = dummy4j.identifier().orcid();

        assertNotNull(actual);
        assertTrue(ORCID_PATTERN.matcher(actual).find());
    }

    @Test
    void shouldReturnOrcidBuilder() {
        OrcidBuilder actual = dummy4j.identifier().orcidBuilder();

        assertNotNull(actual);
    }

    @Test
    void shouldReturnEan8() {
        String actual = dummy4j.identifier().ean8();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnEan8Builder() {
        String actual = dummy4j.identifier().ean8Builder().build();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnGtin8() {
        String actual = dummy4j.identifier().gtin8();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnGtin8Builder() {
        String actual = dummy4j.identifier().gtin8Builder().build();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnEan13() {
        String actual = dummy4j.identifier().ean13();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnEan13Builder() {
        String actual = dummy4j.identifier().ean13Builder().build();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnGtin13() {
        String actual = dummy4j.identifier().gtin13();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnGtin13Builder() {
        String actual = dummy4j.identifier().gtin13Builder().build();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnEan14() {
        String actual = dummy4j.identifier().ean14();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnEan14Builder() {
        String actual = dummy4j.identifier().ean14Builder().build();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnGtin14() {
        String actual = dummy4j.identifier().gtin14();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnGtin14Builder() {
        String actual = dummy4j.identifier().gtin14Builder().build();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnUniversalProductNumber() {
        String actual = dummy4j.identifier().upc();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals(12, actual.length());
    }

    @Test
    void shouldReturnGtin12() {
        String actual = dummy4j.identifier().gtin12();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals(12, actual.length());
    }

    @Test
    void shouldReturnGs1Dash128() {
        String actual = dummy4j.identifier().gs1Dash128();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnSerialShippingContainerCode() {
        String actual = dummy4j.identifier().sscc();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnAsin() {
        String actual = dummy4j.identifier().asin();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(ASIN_PATTERN.matcher(actual).find());
    }

    @Test
    void shouldReturnTac() {
        String actual = dummy4j.identifier().tac();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(TAC_PATTERN.matcher(actual).find());
    }

    @Test
    void shouldReturnImei() {
        String actual = dummy4j.identifier().imei();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(IMEI_PATTERN.matcher(actual).find());
    }

    @Test
    void shouldReturnImeisv() {
        String actual = dummy4j.identifier().imeisv();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(IMEISV_PATTERN.matcher(actual).find());
    }
}