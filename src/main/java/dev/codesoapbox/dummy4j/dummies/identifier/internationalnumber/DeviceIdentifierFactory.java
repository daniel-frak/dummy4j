package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.LuhnFormula;

/**
 * This class serves as a provider of device identifiers for IdentifierDummy
 *
 * @since SNAPSHOT
 */
class DeviceIdentifierFactory {

    static final String REPORTING_BODY_ID_KEY = "#{identifier.device.tac.reporting_body}";
    static final int TAC_ID_LENGTH = 6;
    static final int SERIAL_NUMBER_LENGTH = 6;
    static final int SOFTWARE_VERSION_LENGTH = 2;

    private static final String SEPARATOR = "-";

    private final Dummy4j dummy4j;

    DeviceIdentifierFactory(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    String createTac() {
        String reportingBodyId = dummy4j.expressionResolver().resolve(REPORTING_BODY_ID_KEY);

        return reportingBodyId + SEPARATOR + dummy4j.number().digits(TAC_ID_LENGTH);
    }

    String createImei(LuhnFormula luhnFormula) {
        String firstPart = createTac() + SEPARATOR + getSerialNumber();

        return firstPart + SEPARATOR + luhnFormula.generateCheckDigit(firstPart);
    }

    private String getSerialNumber() {
        return dummy4j.number().digits(SERIAL_NUMBER_LENGTH);
    }

    String createImeisv() {
        return createTac() + SEPARATOR
                + getSerialNumber() + SEPARATOR
                + dummy4j.number().digits(SOFTWARE_VERSION_LENGTH);
    }
}
