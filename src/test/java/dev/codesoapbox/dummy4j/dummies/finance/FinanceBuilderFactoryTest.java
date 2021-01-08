package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.LuhnFormula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FinanceBuilderFactoryTest {

    private FinanceBuilderFactory financeBuilderFactory;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private IbanFormula ibanFormula;

    @Mock
    private LuhnFormula luhnFormula;

    @BeforeEach
    void setUp() {
        financeBuilderFactory = new FinanceBuilderFactory(dummy4j, ibanFormula, luhnFormula);
    }

    @Test
    void shouldCreateNewInstance() {
        FinanceBuilderFactory result = FinanceBuilderFactory.newInstance(dummy4j);
        assertNotNull(result);
    }

    @Test
    void shouldCreateIbanBuilder() {
        IbanBuilder result = financeBuilderFactory.createIbanBuilder();
        assertNotNull(result);
    }

    @Test
    void shouldCreateCreditCardNumberBuilder() {
        CreditCardNumberBuilder result = financeBuilderFactory.createCreditCardNumberBuilder();
        assertNotNull(result);

    }

    @Test
    void shouldCreateCreditCardBuilder() {
        CreditCardBuilder result = financeBuilderFactory.createCreditCardBuilder();
        assertNotNull(result);
    }
}