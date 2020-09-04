package dev.codesoapbox.dummy4j.dummies.finance;

public enum CreditCardProvider {

    VISA("Visa"),
    MASTER_CARD("MasterCard"),
    AMERICAN_EXPRESS("American Express"),
    DISCOVER("Discover"),
    JCB("JCB");

    private String value;

    CreditCardProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
