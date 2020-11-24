package dev.codesoapbox.dummy4j.dummies.finance;

/**
 * A list of countries for which a bank account number or an IBAN can be generated
 * <p>
 * The number constraints are based on the ISO 13616 IBAN Registry published by SWIFT, Release 88 – September 2020.
 *
 * @see <a href="https://www.swift.com/standards/data-standards/iban-international-bank-account-number">
 * IBAN Registry</a>
 *
 * @since 0.6.0
 */
public enum BankAccountCountry {

    ANDORRA("AD"), UNITED_ARAB_EMIRATES("AE"), ALBANIA("AL"), AUSTRIA("AT"),
    AZERBAIJAN("AZ"), BOSNIA_AND_HERZEGOVINA("BA"), BELGIUM("BE"), BULGARIA("BG"),
    BAHRAIN("BH"), BRAZIL("BR"), REPUBLIC_OF_BELARUS("BY"), SWITZERLAND("CH"),
    COSTA_RICA("CR"), CYPRUS("CY"), CZECH_REPUBLIC("CZ"), GERMANY("DE"), DENMARK("DK"),
    DOMINICAN_REPUBLIC("DO"), ESTONIA("EE"), EGYPT("EG"), SPAIN("ES"), FINLAND("FI"),
    FAROE_ISLANDS("FO"), FRANCE("FR"), UNITED_KINGDOM("GB"), GEORGIA("GE"), GIBRALTAR("GI"),
    GREENLAND("GL"), GREECE("GR"), GUATEMALA("GT"), CROATIA("HR"), HUNGARY("HU"),
    IRELAND("IE"), ISRAEL("IL"), IRAQ("IQ"), ICELAND("IS"), ITALY("IT"), JORDAN("JO"),
    KUWAIT("KW"), KAZAKHSTAN("KZ"), LEBANON("LB"), SAINT_LUCIA("LC"), LIECHTENSTEIN("LI"),
    LITHUANIA("LT"), LUXEMBOURG("LU"), LATVIA("LV"), LIBYA("LY"), MONACO("MC"),
    MOLDOVA("MD"), MONTENEGRO("ME"), MACEDONIA("MK"), MAURITANIA("MR"), MALTA("MT"),
    MAURITIUS("MU"), NETHERLANDS("NL"), NORWAY("NO"), PAKISTAN("PK"), POLAND("PL"),
    PALESTINE("PS"), PORTUGAL("PT"), QATAR("QA"), ROMANIA("RO"), SERBIA("RS"),
    SAUDI_ARABIA("SA"), SEYCHELLES("SC"), SWEDEN("SE"), SLOVENIA("SI"), SLOVAKIA("SK"),
    SAN_MARINO("SM"), SAO_TOME_AND_PRINCIPE("ST"), EL_SALVADOR("SV"), TIMOR_LESTE("TL"),
    TUNISIA("TN"), TURKEY("TR"), UKRAINE("UA"), VATICAN_CITY_STATE("VA"),
    VIRGIN_ISLAND("VG"), KOSOVO("XK");

    private String code;

    BankAccountCountry(String code) {
        this.code = code;
    }

    /**
     * Returns a two-letter code that identifies the country.
     * E.g. {@code NO} for Norway
     */
    public String getCode() {
        return this.code;
    }
}
