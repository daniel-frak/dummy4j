package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;

public class FinanceBuilderFactory {

    private final Dummy4j dummy4j;
    private final IbanFormula ibanFormula;
    private final LuhnFormula luhnFormula;

    public FinanceBuilderFactory(Dummy4j dummy4j, IbanFormula ibanFormula, LuhnFormula luhnFormula) {
        this.dummy4j = dummy4j;
        this.ibanFormula = ibanFormula;
        this.luhnFormula = luhnFormula;
    }

    IbanBuilder createIbanBuilder() {
        return new IbanBuilder(dummy4j, ibanFormula);
    }

    CreditCardNumberBuilder createCreditCardNumberBuilder() {
        return new CreditCardNumberBuilder(dummy4j, luhnFormula);
    }

    CreditCardBuilder createCreditCardBuilder() {
        return new CreditCardBuilder(dummy4j, createCreditCardNumberBuilder());
    }
}
