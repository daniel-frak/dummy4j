package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.LuhnFormula;

/**
 * Provides methods for instantiating builders used in the FinanceDummy class
 *
 * @since 0.6.0
 */
public class FinanceBuilderFactory {

    private final Dummy4j dummy4j;
    private final IbanFormula ibanFormula;
    private final LuhnFormula luhnFormula;

    FinanceBuilderFactory(Dummy4j dummy4j, IbanFormula ibanFormula, LuhnFormula luhnFormula) {
        this.dummy4j = dummy4j;
        this.ibanFormula = ibanFormula;
        this.luhnFormula = luhnFormula;
    }

    public static FinanceBuilderFactory newInstance(Dummy4j dummy4j) {
        return new FinanceBuilderFactory(dummy4j, new IbanFormula(), new LuhnFormula());
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
