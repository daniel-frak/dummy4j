package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * International Standard Music Number is a thirteen-character identifier for written or printed forms of music
 *
 * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Music_Number">What is an ISMN</a>
 * @since 0.9.0
 */
public final class Ismn {

    static final String TENTH_CAPACITY_PREFIX = "0";
    static final String GS1_MUSICLAND = "979";

    private final String registrant;
    private final String item;
    private final String checkDigit;
    private final String separator;

    Ismn(String registrant, String item, String checkDigit, String separator) {
        this.registrant = registrant;
        this.item = item;
        this.checkDigit = checkDigit;
        this.separator = separator;
    }

    public String getRegistrant() {
        return registrant;
    }

    public String getItem() {
        return item;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public String getSeparator() {
        return separator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ismn ismn = (Ismn) o;
        return registrant.equals(ismn.registrant) && item.equals(ismn.item) && checkDigit.equals(ismn.checkDigit)
                && Objects.equals(separator, ismn.separator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrant, item, checkDigit, separator);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(separator == null ? "" : separator);

        return joiner
                .add(GS1_MUSICLAND)
                .add(TENTH_CAPACITY_PREFIX)
                .add(registrant)
                .add(item)
                .add(checkDigit)
                .toString();
    }
}
