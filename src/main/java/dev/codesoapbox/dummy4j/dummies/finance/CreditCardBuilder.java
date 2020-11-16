package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.AddressDummy;
import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;
import dev.codesoapbox.dummy4j.dummies.shared.Address;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CreditCardBuilder {

    public static final int MAX_DAYS_FOR_EXPIRY_DATE = 3650;
    public static final int MIN_SECURITY_CODE = 100;
    public static final int MAX_SECURITY_CODE = 999;

    private final Dummy4j dummy4j;
    private final CreditCardNumberBuilder numberBuilder;

    public CreditCardBuilder(Dummy4j dummy4j, CreditCardNumberBuilder numberBuilder) {
        this.dummy4j = dummy4j;
        this.numberBuilder = numberBuilder;
    }

    public CreditCardBuilder withRandomProvider() {
        numberBuilder.withRandomProvider();

        return this;
    }

    public CreditCardBuilder withProvider(CreditCardProvider provider) {
        numberBuilder.withProvider(provider);

        return this;
    }

    public CreditCardBuilder clearNumberFormatting() {
        numberBuilder.clearFormatting();

        return this;
    }

    public CreditCard build() {
        String number = numberBuilder.build();
        String ownerName = dummy4j.name().firstName() + " " + dummy4j.name().lastName();
        Address ownerAddress = generateAddress();
        String expiryDate = generateExpiryDate();
        String securityCode = String.valueOf(dummy4j.number().nextInt(MIN_SECURITY_CODE, MAX_SECURITY_CODE));

        return new CreditCard(number, numberBuilder.getProvider(), ownerName, ownerAddress, expiryDate, securityCode);
    }

    private Address generateAddress() {
        AddressDummy address = dummy4j.address();

        return new Address(address.street(), address.postCode(), address.city(), address.country());
    }

    private String generateExpiryDate() {
        LocalDate date = dummy4j.dateAndTime().future(MAX_DAYS_FOR_EXPIRY_DATE, ChronoUnit.DAYS).toLocalDate();
        int month = date.getMonthValue();
        int year = date.getYear();

        return Padding.leftPad(String.valueOf(month), 2, '0') + "/" + year;
    }
}
