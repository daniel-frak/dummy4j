package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

/**
 * Enum containing some of the supported GS1 prefix ranges
 *
 * @see <a href="https://en.wikipedia.org/wiki/List_of_GS1_country_codes">List of GS1 country codes</a>
 * @since 0.9.0
 */
public enum Gs1PrefixRange {

    UPCA_COMPATIBLE(0, 99), USA(100, 139), RESTRICTED_CIRCULATION(200, 299),
    FRANCE_MONACO(300, 379), BULGARIA(380, null), SLOVENIA(383, null),
    CROATIA(385, null), BOSNIA_HERZEGOVINA(387, null), MONTENEGRO(389, null),
    GERMANY(400, 440), JAPAN(450, 459), RUSSIA(460, 469), UK(500, 509),
    GREECE(520, 521), BELGIUM_LUXEMBOURG(540, 549), PORTUGAL(560, null),
    ICELAND(569, null), DENMARK(570, 579), POLAND(590, null), ROMANIA(594, null),
    HUNGARY(599, null), SOUTH_AFRICA(600, 601), GHANA(603, null),
    SENEGAL(604, null), KENYA(616, null), SYRIA(621, null), EGYPT(622, null),
    FINLAND(640, 649), CHINA(690, 699), NORWAY(700, 709), ISRAEL(729, null),
    SWEDEN(730, 739), GUATEMALA(740, null), PANAMA(745, null), MEXICO(750, null),
    SWITZERLAND(760, 769), COLOMBIA(770, 771), URUGUAY(773, null),
    PERU(775, null), ARGENTINA(778, 779), BRAZIL(789, 790), ITALY(800, 839),
    SPAIN(840, 849), CZECH(859, null), MONGOLIA(865, null), TURKEY(868, 869),
    NETHERLANDS(870, 879), AUSTRIA(900, 919), AUSTRALIA(930, 939),
    NEW_ZEALAND(940, 949);

    private final Integer min;
    private final Integer max;

    Gs1PrefixRange(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isSingleValue() {
        return max == null;
    }
}
