package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import dev.codesoapbox.dummy4j.dummies.shared.string.StringValidator;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * International Standard Book Number is a numeric commercial book identifier
 *
 * @see <a href="https://en.wikipedia.org/wiki/International_Standard_Book_Number">
 * International Standard Book Number</a>
 * @since 0.9.0
 */
public final class Isbn {

    private final IsbnType type;
    private final String prefix;
    private final String registrationGroup;
    private final String registrant;
    private final String publication;
    private final String checkDigit;
    private final String separator;

    Isbn(IsbnType type, String prefix, String registrationGroup, String registrant, String publication,
         String separator, String checkDigit) {
        this.type = type;
        this.prefix = prefix;
        this.registrationGroup = registrationGroup;
        this.registrant = registrant;
        this.publication = publication;
        this.checkDigit = checkDigit;
        this.separator = separator;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRegistrationGroup() {
        return registrationGroup;
    }

    public String getRegistrant() {
        return registrant;
    }

    public String getPublication() {
        return publication;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public IsbnType getType() {
        return type;
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
        Isbn isbn = (Isbn) o;
        return type == isbn.type && prefix.equals(isbn.prefix) && registrationGroup.equals(isbn.registrationGroup)
                && registrant.equals(isbn.registrant) && publication.equals(isbn.publication)
                && checkDigit.equals(isbn.checkDigit) && separator.equals(isbn.separator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, prefix, registrationGroup, registrant, publication, checkDigit, separator);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(separator == null ? "" : separator);
        if (!StringValidator.isNullOrEmpty(prefix)) {
            joiner.add(prefix);
        }
        return joiner
                .add(registrationGroup)
                .add(registrant)
                .add(publication)
                .add(checkDigit)
                .toString();
    }
}
