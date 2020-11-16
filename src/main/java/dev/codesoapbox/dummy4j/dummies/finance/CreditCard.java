package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.dummies.shared.Address;
import dev.codesoapbox.dummy4j.dummies.shared.string.StringValidator;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * A value object representing a credit card
 */
public final class CreditCard {

    private final String number;
    private final CreditCardProvider provider;
    private final String ownerName;
    private final Address ownerAddress;
    private final String expiryDate;
    private final String securityCode;

    public CreditCard(String number, CreditCardProvider provider, String ownerName, Address ownerAddress,
                      String expiryDate, String securityCode) {
        this.number = number;
        this.provider = provider;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
        validateFields();
    }

    private void validateFields() {
        StringJoiner missingFields = new StringJoiner(", ");

        if (StringValidator.isNullOrEmpty(number)) {
            missingFields.add("number");
        }
        if (StringValidator.isNullOrEmpty(ownerName)) {
            missingFields.add("ownerName");
        }
        if (StringValidator.isNullOrEmpty(expiryDate)) {
            missingFields.add("expiryDate");
        }
        if (StringValidator.isNullOrEmpty(securityCode)) {
            missingFields.add("securityCode");
        }
        if (provider == null) {
            missingFields.add("provider");
        }
        if (ownerAddress == null) {
            missingFields.add("owner address");
        }
        if (!missingFields.toString().isEmpty()) {
            throw new IllegalArgumentException("Missing values for the following fields: " + missingFields.toString());
        }
    }

    public String getNumber() {
        return number;
    }

    public CreditCardProvider getProvider() {
        return provider;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Address getOwnerAddress() {
        return ownerAddress;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCard)) {
            return false;
        }
        CreditCard that = (CreditCard) o;
        return number.equals(that.number) &&
                provider == that.provider &&
                ownerName.equals(that.ownerName) &&
                ownerAddress.equals(that.ownerAddress) &&
                expiryDate.equals(that.expiryDate) &&
                securityCode.equals(that.securityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, provider, ownerName, ownerAddress, expiryDate, securityCode);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number='" + number + '\'' +
                ", provider=" + provider.getName() +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAddress='" + ownerAddress + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", securityCode='" + securityCode + '\'' +
                '}';
    }
}
