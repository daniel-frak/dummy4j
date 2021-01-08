package dev.codesoapbox.dummy4j.dummies.identifier;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.InternationalStandardNumberFactory;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.Gtin13Builder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.Gtin14Builder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.Gtin8Builder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.Isbn;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.IsbnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.Ismn;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.IsmnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.IsniBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.OrcidBuilder;
import dev.codesoapbox.dummy4j.dummies.shared.string.StringSanitizer;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.UUID;

/**
 * Provides methods for generating identifiers
 *
 * @since 0.5.0
 */
public class IdentifierDummy {

    private final Dummy4j dummy4j;
    private final InternationalStandardNumberFactory internationalStandardNumberFactory;

    public IdentifierDummy(Dummy4j dummy4j,
                           InternationalStandardNumberFactory internationalStandardNumberFactory) {
        this.dummy4j = dummy4j;
        this.internationalStandardNumberFactory = internationalStandardNumberFactory;
    }

    /**
     * Generates a random UUID.
     * E.g. {@code c4ca4238-a0b9-3382-8dcc-509a6f75849b}
     */
    public UUID uuid() {
        return UUID.nameUUIDFromBytes(String.valueOf(dummy4j.number().nextLong()).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a random International Standard Serial Number.
     * E.g. {@code 1234-567X}
     * <p>
     * ISSN is an 8-digit code used to identify newspapers, journals, magazines and periodicals of all kinds
     * and on all media–print and electronic.
     *
     * @see <a href="https://www.issn.org/understanding-the-issn/what-is-an-issn/">What is an ISSN</a>
     * @since SNAPSHOT
     */
    public String issn() {
        return internationalStandardNumberFactory.createIssn();
    }

    /**
     * Generates a random International Standard Book Number.
     * E.g. {@code 978-0-306-40615-6}
     * <p>
     * ISBN is a numeric commercial book identifier.
     *
     * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Book_Number">What is an ISBN</a>
     * @since SNAPSHOT
     */
    public Isbn isbn() {
        return internationalStandardNumberFactory.createIsbnBuilder().build();
    }

    /**
     * Provides a builder for a random International Standard Book Number.
     * E.g. {@code isbnBuilder().withType(IsbnType.ISBN_10).withoutSeparator().build()} may generate
     * {@code 9706077057}
     *
     * @since SNAPSHOT
     */
    public IsbnBuilder isbnBuilder() {
        return internationalStandardNumberFactory.createIsbnBuilder();
    }

    /**
     * Generates a random International Standard Music Number.
     * E.g. {@code 979 0 2712 7923 6}
     * <p>
     * ISMN is a thirteen-character identifier for written or printed forms of music.
     *
     * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Music_Number">What is an ISMN</a>
     * @since SNAPSHOT
     */
    public Ismn ismn() {
        return internationalStandardNumberFactory.createIsmnBuilder().build();
    }

    /**
     * Provides a builder for a random International Standard Music Number.
     * E.g. {@code ismnBuilder().withRegistrant("003").withSeparator("-").build()} may generate
     * {@code 979-0-003-76925-7}
     *
     * @since SNAPSHOT
     */
    public IsmnBuilder ismnBuilder() {
        return internationalStandardNumberFactory.createIsmnBuilder();
    }

    /**
     * Generates a random International Standard Name Identifier.
     * E.g. {@code 2975484076158599}
     * <p>
     * ISNI is an identifier system for the public identities of contributors to media content.
     *
     * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Name_Identifier">What is an ISNI</a>
     * @since SNAPSHOT
     */
    public String isni() {
        return internationalStandardNumberFactory.createIsniBuilder().build();
    }

    /**
     * Provides a builder for a random International Standard Name Identifier.
     * E.g. {@code isniBuilder().asUrl().build()} may generate {@code https://isni.org/isni/2520242885976955}
     *
     * @since SNAPSHOT
     */
    public IsniBuilder isniBuilder() {
        return internationalStandardNumberFactory.createIsniBuilder();
    }

    /**
     * Generates a random Open Researcher and Contributor ID.
     * E.g. {@code 6994-0298-2935-3670}
     * <p>
     * ORCID uniquely identifies authors and contributors of scholarly communication.
     *
     * @see <a href="https://en.wikipedia.org/wiki/ORCID">What is an ORCID</a>
     * @since SNAPSHOT
     */
    public String orcid() {
        return internationalStandardNumberFactory.createOrcidBuilder().build();
    }

    /**
     * Provides a builder for a random Open Researcher and Contributor ID.
     * E.g. {@code orcidBuilder().asUrl().build()} may generate {@code https://orcid.org/9784-7648-0678-9594}
     *
     * @since SNAPSHOT
     */
    public OrcidBuilder orcidBuilder() {
        return internationalStandardNumberFactory.createOrcidBuilder();
    }

    /**
     * Generates a random GTIN-8.
     * <p>
     * This method is functionally equivalent to IdentifierDummy::gtin8()
     *
     * @see IdentifierDummy#gtin8()
     * @since SNAPSHOT
     */
    public String ean8() {
        return gtin8();
    }

    /**
     * Generates a random GTIN-8.
     * E.g. {@code 59907824}
     * <p>
     * GTIN-8 is an identifier for trade items used usually for very small articles where fitting a larger code
     * onto the package would be difficult.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Global_Trade_Item_Number">Global Trade Item Numbers</a>
     * @since SNAPSHOT
     */
    public String gtin8() {
        return internationalStandardNumberFactory.createGtin8Builder().build();
    }

    /**
     * Provides a builder for a random GTIN-8.
     * <p>
     * This method is functionally equivalent to IdentifierDummy::gtin8Builder()
     *
     * @see IdentifierDummy#gtin8Builder()
     * @since SNAPSHOT
     */
    public Gtin8Builder ean8Builder() {
        return gtin8Builder();
    }

    /**
     * Provides a builder for a random GTIN-8.
     * E.g. {@code gtin8Builder().withGs1PrefixRange(Gs1PrefixRange.ARGENTINA).build()} may generate {@code 77867292}
     *
     * @see IdentifierDummy#gtin8()
     * @since SNAPSHOT
     */
    public Gtin8Builder gtin8Builder() {
        return internationalStandardNumberFactory.createGtin8Builder();
    }

    /**
     * Generates a random GTIN-13.
     * <p>
     * This method is functionally equivalent to IdentifierDummy::gtin13()
     *
     * @see IdentifierDummy#gtin13()
     * @since SNAPSHOT
     */
    public String ean13() {
        return gtin13();
    }

    /**
     * Generates a random GTIN-13.
     * E.g. {@code 7755838708484}
     * <p>
     * GTIN-13 is used in global trade to identify a product type, its packaging configuration and manufacturer.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Global_Trade_Item_Number">Global Trade Item Numbers</a>
     * @since SNAPSHOT
     */
    public String gtin13() {
        return internationalStandardNumberFactory.createGtin13Builder().build();
    }

    /**
     * Provides a builder for a random GTIN-13.
     * <p>
     * This method is functionally equivalent to IdentifierDummy::gtin13Builder()
     *
     * @see IdentifierDummy#gtin13Builder()
     * @since SNAPSHOT
     */
    public Gtin13Builder ean13Builder() {
        return gtin13Builder();
    }

    /**
     * Provides a builder for a random GTIN-13.
     * E.g. {@code gtin13Builder().withGs1PrefixRange(Gs1PrefixRange.DENMARK).build()} may generate
     * {@code 5716640724599}
     *
     * @see IdentifierDummy#gtin13()
     * @since SNAPSHOT
     */
    public Gtin13Builder gtin13Builder() {
        return internationalStandardNumberFactory.createGtin13Builder();
    }

    /**
     * Generates a random GTIN-14.
     * <p>
     * This method is functionally equivalent to IdentifierDummy::gtin14()
     *
     * @see IdentifierDummy#gtin14()
     * @since SNAPSHOT
     */
    public String ean14() {
        return gtin14();
    }

    /**
     * Generates a random GTIN-14.
     * E.g. {@code 37735089220257}
     * <p>
     * GTIN-14 is used to identify trade items at various packaging levels.
     *
     * @see <a href="https://www.gtin.info/">Global Trade Item Numbers</a>
     * @since SNAPSHOT
     */
    public String gtin14() {
        return internationalStandardNumberFactory.createGtin14Builder().build();
    }

    /**
     * Provides a builder for a random GTIN-14.
     * <p>
     * This method is functionally equivalent to IdentifierDummy::gtin14Builder()
     *
     * @see IdentifierDummy#gtin14Builder()
     * @since SNAPSHOT
     */
    public Gtin14Builder ean14Builder() {
        return gtin14Builder();
    }

    /**
     * Provides a builder for a random GTIN-14.
     * E.g. {@code gtin14Builder().withApplicationIdentifier().build()} may generate {@code (01)44668535249994}
     *
     * @see IdentifierDummy#gtin14()
     * @since SNAPSHOT
     */
    public Gtin14Builder gtin14Builder() {
        return internationalStandardNumberFactory.createGtin14Builder();
    }

    /**
     * Generates a random GTIN-12.
     * E.g. {@code 001543032542}
     * <p>
     * GTIN-12 is used to represent UPC barcodes. UPC is widely used in the United States, Canada, Europe, Australia,
     * New Zealand, and other countries for tracking trade items.
     *
     * @see <a href="https://www.barcode.graphics/gtin-12/">UPC (GTIN-12)</a>
     * @see <a href="https://www.gtin.info/upc/">UPC Symbols</a>
     * @since SNAPSHOT
     */
    public String gtin12() {
        return internationalStandardNumberFactory.createUpc();
    }

    /**
     * Generates a random Universal Product Code.
     * E.g. {@code 001543032542}
     * <p>
     * This method is functionally equivalent to IdentifierDummy::gtin12()
     *
     * @see IdentifierDummy#gtin12()
     * @see <a href="https://en.wikipedia.org/wiki/Universal_Product_Code">Universal Product Code</a>
     * @since SNAPSHOT
     */
    public String upc() {
        return internationalStandardNumberFactory.createUpc();
    }

    /**
     * Generates a random code compliant with the GS1-128 standard.
     * E.g. {@code (00)930815721808015600(30)548855<FNC1>(20)41}
     * <p>
     * GS1-128 is a code that includes additional data such as best before dates, batch numbers, quantities, weights.
     *
     * @see <a href="https://en.wikipedia.org/wiki/GS1-128">GS1-128</a>
     * @see <a href="https://en.wikipedia.org/wiki/GS1-128#Full_list_of_Application_Identifiers">
     * Full list of Application Identifiers</a>
     * @since SNAPSHOT
     */
    public String gs1Dash128() {
        return internationalStandardNumberFactory.createGs1Dash128();
    }

    /**
     * Generates a random Serial Shipping Container Code.
     * E.g. {@code (00)247266005821394706}
     * <p>
     * SSCC is used to identify logistics units.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Serial_shipping_container_code">Serial shipping container code</a>
     * @see <a href="https://www.gs1-128.info/sscc-18/">SSCC-18 Barcodes</a>
     * @see <a href="https://www.gs1.org/standards/id-keys/sscc/">GS1 standard for SSCC</a>
     * @since SNAPSHOT
     */
    public String sscc() {
        return internationalStandardNumberFactory.createSscc();
    }

    /**
     * Generates a random Amazon Standard Identification Number.
     * E.g. {@code B00AA74928}
     * <p>
     * ASIN is used for product identification within the Amazon organization.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Amazon_Standard_Identification_Number">
     * Amazon Standard Identification Number</a>
     * @since SNAPSHOT
     */
    public String asin() {
        return "B00" + getAsinAlphaNumericPart();
    }

    private String getAsinAlphaNumericPart() {
        int numbers = dummy4j.number().nextInt(10, 99_999);
        int letters = 7 - String.valueOf(numbers).length();
        String letterPart = dummy4j.lorem().characters(letters).toUpperCase(Locale.ENGLISH);

        return StringSanitizer.sanitizeForEmail(letterPart) + numbers;
    }

    /**
     * Generates a random Type Allocation Code.
     * E.g. {@code 52-870587}
     * <p>
     * TAC identifies a particular model (and often revision) of wireless telephones.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Type_Allocation_Code">Type Allocation Code</a>
     * @since SNAPSHOT
     */
    public String tac() {
        return internationalStandardNumberFactory.createTac();
    }

    /**
     * Generates a random International Mobile Equipment Identity number.
     * E.g. {@code 98-601561-419726-3}
     * <p>
     * IMEI identifies mobile phones, as well as some satellite phones.
     *
     * @see <a href="https://en.wikipedia.org/wiki/International_Mobile_Equipment_Identity">
     * International Mobile Equipment Identity</a>
     * @since SNAPSHOT
     */
    public String imei() {
        return internationalStandardNumberFactory.createImei();
    }

    /**
     * Generates a random International Mobile Equipment Identity Software Version number.
     * E.g. {@code 99-993671-425488-39}
     * <p>
     * IMEISV identifies mobile phones (like IMEI – but its last two digits are allocated for the Software Version
     * Number).
     *
     * @see <a href="https://en.wikipedia.org/wiki/International_Mobile_Equipment_Identity">
     * International Mobile Equipment Identity</a>
     * @since SNAPSHOT
     */
    public String imeisv() {
        return internationalStandardNumberFactory.createImeisv();
    }
}
