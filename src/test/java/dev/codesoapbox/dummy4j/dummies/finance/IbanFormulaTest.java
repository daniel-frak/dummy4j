package dev.codesoapbox.dummy4j.dummies.finance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IbanFormulaTest {

    private IbanFormula formula;

    @BeforeEach
    void setUp() {
        formula = new IbanFormula();
    }

    /**
     * Tests for example IBAN values
     * @see <a href="https://www.swift.com/standards/data-standards/iban-international-bank-account-number">
     * IBAN Registry</a>
     */
    @ParameterizedTest
    @CsvSource({
            "1, A, 59",
            "123, FA, 07",
            "12345, A, 28",
            "WEST12345698765432, GB, 82",
            "WEST 1234 5698 765 432, GB, 82",
            "WEST-1234-5698 765 432, GB, 82",
            "00012030200359100100, AD, 12",      // Andorra
            "0331234567890123456, AE, 07",       // United Arab Emirates
            "212110090000000235698741, AL, 47",  // Albania
            "1904300234573201, AT, 61",          // Austria
            "NABZ00000000137010001944, AZ, 21",  // Azerbaijan
            "1290079401028494, BA, 39",          // Bosnia and Herzegovina
            "539007547034, BE, 68",              // Belgium
            "BNBG96611020345678, BG, 80",        // Bulgaria
            "BMAG00001299123456, BH, 67",        // Bahrain
            "00000000141455123924100C2, BR, 18", // Brazil
            "NBRB3600900000002Z00AB00, BY, 13",  // Republic of Belarus
            "00762011623852957, CH, 93",         // Switzerland
            "015202001026284066, CR, 05",        // Costa Rica
            "002001280000001200527600, CY, 17",  // Cyprus
            "08000000192000145399, CZ, 65",      // Czechoslovakia
            "370400440532013000, DE, 89",        // Germany
            "00400440116243, DK, 50",            // Denmark
            "BAGR00000001212453611324, DO, 28",  // Dominican Republic
            "2200221020145685, EE, 38",          // Estonia
            "0019000500000000263180002, EG, 38", // Egypt
            "23100001180000012345, ES, 80",      // Spain
            "12345600000785, FI, 21",            // Finland
            "64600001631634, FO, 62",            // Faroe Islands
            "20041010050500013M02606, FR, 14",   // France
            "NWBK60161331926819, GB, 29",        // UK
            "NB0000000101904917, GE, 29",        // Georgia
            "NWBK000000007099453, GI, 75",       // Gibraltar
            "64710001000206, GL, 89",            // Greenland
            "01101250000000012300695, GR, 16",   // Greece
            "TRAJ01020000001210029690, GT, 82",  // Guatemala
            "10010051863000160, HR, 12",         // Croatia
            "117730161111101800000000, HU, 42",  // Hungary
            "AIBK93115212345678, IE, 29",        // Ireland
            "0108000000099999999, IL, 62",       // Israel
            "NBIQ850123456789012, IQ, 98",       // Iraq
            "0159260076545510730339, IS, 14",    // Iceland
            "X0542811101000000123456, IT, 60",   // Italy
            "CBJO0010000000000131000302, JO, 94",// Jordan
            "CBKU0000000000001234560101, KW, 81",// Kuwait
            "125KZT5004100100, KZ, 86",          // Kazakhstan
            "099900000001001901229114, LB, 62",  // Lebanon
            "HEMM000100010012001200023015, LC, 55",//Saint Lucia
            "088100002324013AA, LI, 21",         // Liechtenstein
            "1000011101001000, LT, 12",          // Lithuania
            "0019400644750000, LU, 28",          // Luxembourg
            "BANK0000435195001, LV, 80",         // Latvia
            "002048000020100120361, LY, 83",     // Libya
            "11222000010123456789030, MC, 58",   // Monaco
            "AG000225100013104168, MD, 24",      // Moldova
            "505000012345678951, ME, 25",        // Montenegro
            "250120000058984, MK, 07",           // Macedonia
            "00020001010000123456753, MR, 13",   // Mauritania
            "MALT011000012345MTLCAST001S, MT, 84",// Malta
            "BOMM0101101030300200000MUR, MU, 17",// Mauritius
            "ABNA0417164300, NL, 91",            // Netherlands
            "86011117947, NO, 93",               // Norway
            "SCBL0000001123456702, PK, 36",      // Pakistan
            "109010140000071219812874, PL, 61",  // Poland
            "PALS000000000400123456702, PS, 92", // Palestine
            "000201231234567890154, PT, 50",     // Portugal
            "DOHB00001234567890ABCDEFG, QA, 58", // Qatar
            "AAAA1B31007593840000, RO, 49",      // Romania
            "260005601001611379, RS, 35",        // Serbia
            "80000000608010167519, SA, 03",      // Saudi Arabia
            "SSCB11010000000000001497USD, SC, 18",// Seychelles
            "50000000058398257466, SE, 45",      // Sweden
            "191000000123438, SI, 56",           // Slovenia
            "12000000198742637541, SK, 31",      // Slovakia
            "U0322509800000000270100, SM, 86",   // San Marino
            "000100010051845310146, ST, 23",     // Sao Tome and Principe
            "CENR00000000000000700025, SV, 62",  // El Salvador
            "0080012345678910157, TL, 38",       // Timor-Leste
            "10006035183598478831, TN, 59",      // Tunisia
            "0006100519786457841326, TR, 33",    // Turkey
            "3223130000026007233566001, UA, 21", // Ukraine
            "001123000012345678, VA, 59",        // Vatican City State
            "VPVG0000012345678901, VG, 96",      // Virgin Islands
            "1212012345678906, XK, 05"           // Kosovo
    })
    void shouldReturnCheckDigits(String accountNumber, String countryCode, String expected) {
        String digits = formula.getCheckDigits(accountNumber, countryCode);

        assertEquals(expected, digits);
    }
}