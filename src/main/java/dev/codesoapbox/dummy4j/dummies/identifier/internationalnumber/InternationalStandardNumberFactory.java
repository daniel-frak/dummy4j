package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber.*;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.IsbnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber.IsbnValidator;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.IsmnBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.musicnumber.IsmnValidator;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.BasicIsniProvider;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.IsniBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber.OrcidBuilder;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.serialnumber.IssnFactory;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;

/**
 * This class serves as a provider of international standard identifiers for IdentifierDummy
 *
 * @since 0.9.0
 */
public class InternationalStandardNumberFactory {

    private final Dummy4j dummy4j;
    private final InternationalNumberCheckDigitFormulaProvider checkDigitFormulaProvider;
    private final IsbnValidator isbnValidator;
    private final IsmnValidator ismnValidator;
    private final IssnFactory issnFactory;
    private final BasicIsniProvider basicIsniProvider;
    private final UpcFactory upcFactory;
    private final Gs1Dash128Factory gs1Dash128Factory;
    private final SsccFactory ssccFactory;
    private final DeviceIdentifierFactory deviceIdentifierFactory;

    InternationalStandardNumberFactory(Dummy4j dummy4j,
                                       InternationalNumberCheckDigitFormulaProvider checkDigitFormulaProvider,
                                       IsbnValidator isbnValidator,
                                       IsmnValidator ismnValidator,
                                       IssnFactory issnFactory,
                                       BasicIsniProvider basicIsniProvider,
                                       UpcFactory upcFactory,
                                       Gs1Dash128Factory gs1Dash128Factory,
                                       SsccFactory ssccFactory,
                                       DeviceIdentifierFactory deviceIdentifierFactory) {
        this.dummy4j = dummy4j;
        this.checkDigitFormulaProvider = checkDigitFormulaProvider;
        this.isbnValidator = isbnValidator;
        this.ismnValidator = ismnValidator;
        this.issnFactory = issnFactory;
        this.basicIsniProvider = basicIsniProvider;
        this.upcFactory = upcFactory;
        this.gs1Dash128Factory = gs1Dash128Factory;
        this.ssccFactory = ssccFactory;
        this.deviceIdentifierFactory = deviceIdentifierFactory;
    }

    public static InternationalStandardNumberFactory newInstance(Dummy4j dummy4j) {
        InternationalNumberCheckDigitFormulaProvider checkDigitFormulaProvider =
                new InternationalNumberCheckDigitFormulaProvider();
        IssnFactory issnFactory = IssnFactory.newInstance(dummy4j, checkDigitFormulaProvider.getModElevenFormula());
        BasicIsniProvider basicIsniProvider = new BasicIsniProvider(dummy4j,
                checkDigitFormulaProvider.getModElevenDashTwoFormula());
        ModTenFormula modTenFormula = checkDigitFormulaProvider.getModTenFormula();
        UpcFactory upcFactory = UpcFactory.newInstance(dummy4j, modTenFormula);
        SsccFactory ssccFactory = new SsccFactory(dummy4j, modTenFormula);
        Gtin14Builder gtin14Builder = new Gtin14Builder(dummy4j, modTenFormula);
        Gs1Dash128Factory gs1Dash128Factory = new Gs1Dash128Factory(dummy4j, gtin14Builder, ssccFactory);
        DeviceIdentifierFactory deviceIdentifierFactory = new DeviceIdentifierFactory(dummy4j);

        return new InternationalStandardNumberFactory(dummy4j, checkDigitFormulaProvider, new IsbnValidator(),
                new IsmnValidator(), issnFactory, basicIsniProvider, upcFactory, gs1Dash128Factory, ssccFactory,
                deviceIdentifierFactory);
    }

    public String createIssn() {
        return issnFactory.createIssn();
    }

    public IsbnBuilder createIsbnBuilder() {
        return new IsbnBuilder(dummy4j, isbnValidator, checkDigitFormulaProvider);
    }

    public IsmnBuilder createIsmnBuilder() {
        return new IsmnBuilder(dummy4j, checkDigitFormulaProvider.getModTenFormula(), ismnValidator);
    }

    public IsniBuilder createIsniBuilder() {
        return new IsniBuilder(basicIsniProvider);
    }

    public OrcidBuilder createOrcidBuilder() {
        return new OrcidBuilder(basicIsniProvider);
    }

    public Gtin8Builder createGtin8Builder() {
        return new Gtin8Builder(dummy4j, checkDigitFormulaProvider.getModTenFormula());
    }

    public Gtin13Builder createGtin13Builder() {
        return new Gtin13Builder(dummy4j, checkDigitFormulaProvider.getModTenFormula());
    }

    public Gtin14Builder createGtin14Builder() {
        return new Gtin14Builder(dummy4j, checkDigitFormulaProvider.getModTenFormula());
    }

    public String createUpc() {
        return upcFactory.createUpc();
    }

    public String createGs1Dash128() {
        return gs1Dash128Factory.createCode();
    }

    public String createSscc() {
        return ssccFactory.createCode();
    }

    public String createTac() {
        return deviceIdentifierFactory.createTac();
    }

    public String createImei() {
        return deviceIdentifierFactory.createImei(checkDigitFormulaProvider.getLuhnFormula());
    }

    public String createImeisv() {
        return deviceIdentifierFactory.createImeisv();
    }
}
