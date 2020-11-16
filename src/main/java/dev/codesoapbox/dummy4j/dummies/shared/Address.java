package dev.codesoapbox.dummy4j.dummies.shared;

import dev.codesoapbox.dummy4j.dummies.shared.string.StringValidator;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * A value object representing a complete address
 *
 * @since SNAPSHOT
 */
public final class Address {

    private final String street;
    private final String postCode;
    private final String city;
    private final String country;

    public Address(String street, String postCode, String city, String country) {
        this.street = street;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
        validateFields();
    }

    private void validateFields() {
        StringJoiner missingFields = new StringJoiner(", ");

        if (StringValidator.isNullOrEmpty(street)) {
            missingFields.add("street");
        }
        if (StringValidator.isNullOrEmpty(postCode)) {
            missingFields.add("post code");
        }
        if (StringValidator.isNullOrEmpty(city)) {
            missingFields.add("city");
        }
        if (StringValidator.isNullOrEmpty(country)) {
            missingFields.add("country");
        }
        if (!missingFields.toString().isEmpty()) {
            throw new IllegalArgumentException("Missing values for the following fields: " + missingFields.toString());
        }
    }

    public String getStreet() {
        return street;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return street.equals(address.street) &&
                postCode.equals(address.postCode) &&
                city.equals(address.city) &&
                country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, postCode, city, country);
    }

    @Override
    public String toString() {
        return street + ", " + postCode + " " + city + ", " + country;
    }
}
