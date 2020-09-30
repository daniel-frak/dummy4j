package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.AddressDummy;
import dev.codesoapbox.dummy4j.dummies.shared.math.NumberValidator;
import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;
import dev.codesoapbox.dummy4j.dummies.shared.valueobject.Address;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Provides methods for generating values related to finances
 *
 * @since SNAPSHOT
 */
public class FinanceDummy {

    static final String CURRENCY_CODE_KEY = "#{finance.currency.code}";
    static final String CURRENCY_NUMERIC_CODE_KEY = "#{finance.currency.numeric_code}";
    static final String CURRENCY_NAME_KEY = "#{finance.currency.name}";
    static final String CURRENCY_SYMBOL_KEY = "#{finance.currency.symbol}";
    static final String CRYPTO_CURRENCY_CODE_KEY = "#{finance.cryptocurrency.code}";
    static final String CRYPTO_CURRENCY_NAME_KEY = "#{finance.cryptocurrency.name}";
    static final String CRYPTO_CURRENCY_SYMBOL_KEY = "#{finance.cryptocurrency.symbol}";
    static final String PARTIAL_CREDIT_CARD_KEY = "#{finance.credit_card_without_check_digit.";
    static final String BANK_ACCOUNT_TYPE_KEY = "#{finance.bank_account.type}";
    static final String PARTIAL_ACCOUNT_NUMBER_KEY = "#{finance.bank_account.number_structure.";
    static final String PAYMENT_OPTION_KEY = "#{finance.bank_account.payment_option}";
    static final String FINANCIAL_OPERATION_KEY = "#{finance.bank_account.financial_operation}";
    static final String BITCOIN_ADDRESS_FORMAT_KEY = "#{finance.bank_account.bitcoin_address.format}";
    static final String BITCOIN_ADDRESS_CHARACTERS_KEY = "#{finance.bank_account.bitcoin_address.characters}";
    static final int BITCOIN_ADDRESS_MIN_LENGTH = 26;
    static final int BITCOIN_ADDRESS_MAX_LENGTH = 35;
    static final int MAX_DAYS_FOR_EXPIRY_DATE = 3650;
    static final int MIN_SECURITY_CODE = 100;
    static final int MAX_SECURITY_CODE = 999;

    /**
     * There is no standardized minimum Basic Bank Account Number length, currently Norway has the shortest - 11
     */
    private static final int MIN_ACCOUNT_LENGTH = 11;
    private static final int MAX_ACCOUNT_LENGTH = 30;

    private final Dummy4j dummy4j;
    private final LuhnFormula luhnFormula;
    private final IbanFormula ibanFormula;

    public FinanceDummy(Dummy4j dummy4j, LuhnFormula luhnFormula, IbanFormula ibanFormula) {
        this.dummy4j = dummy4j;
        this.luhnFormula = luhnFormula;
        this.ibanFormula = ibanFormula;
    }

    /**
     * Provides a random price.
     * E.g. {@code 12.34}
     */
    public String price() {
        return new PriceBuilder(dummy4j).build();
    }

    /**
     * Provides a builder for random prices.
     * E.g. {@code priceBuilder.withCurrency("USD").build()} may generate a price similar to {@code USD 12.34}
     */
    public PriceBuilder priceBuilder() {
        return new PriceBuilder(dummy4j);
    }

    /**
     * Provides a random currency code compliant with the ISO_4217 standard.
     * E.g. {@code CHF}
     */
    public String currencyCode() {
        return dummy4j.expressionResolver().resolve(CURRENCY_CODE_KEY);
    }

    /**
     * Provides a random currency numeric code compliant with the ISO_4217 standard.
     * E.g. {@code 971}
     */
    public String currencyNumericCode() {
        return dummy4j.expressionResolver().resolve(CURRENCY_NUMERIC_CODE_KEY);
    }

    /**
     * Provides a random currency name compliant with the ISO_4217 standard.
     * E.g. {@code Armenian Dram}
     */
    public String currencyName() {
        return dummy4j.expressionResolver().resolve(CURRENCY_NAME_KEY);
    }

    /**
     * Provides a random currency symbol.
     * E.g. {@code £}
     */
    public String currencySymbol() {
        return dummy4j.expressionResolver().resolve(CURRENCY_SYMBOL_KEY);
    }

    /**
     * Provides a random cryptocurrency code.
     * E.g. {@code ETH}
     */
    public String cryptoCurrencyCode() {
        return dummy4j.expressionResolver().resolve(CRYPTO_CURRENCY_CODE_KEY);
    }

    /**
     * Provides a random cryptocurrency name.
     * E.g. {@code Monero}
     */
    public String cryptoCurrencyName() {
        return dummy4j.expressionResolver().resolve(CRYPTO_CURRENCY_NAME_KEY);
    }

    /**
     * Provides a random cryptocurrency symbol.
     * E.g. {@code ɱ}
     */
    public String cryptoCurrencySymbol() {
        return dummy4j.expressionResolver().resolve(CRYPTO_CURRENCY_SYMBOL_KEY);
    }

    /**
     * Provides a random credit card provider.
     * E.g. {@code Visa}
     */
    public String creditCardProvider() {
        return dummy4j.nextEnum(CreditCardProvider.class).getValue();
    }

    /**
     * Provides a random credit card number compliant with the ISO/IEC 7812 standard.
     * E.g. {@code 4150259182774861}
     */
    public String creditCardNumber() {
        CreditCardProvider randomProvider = dummy4j.nextEnum(CreditCardProvider.class);
        return creditCardNumber(randomProvider);
    }

    /**
     * Provides a random credit card number compliant with the ISO/IEC 7812 standard and containing a correct prefix
     * for the given provider.
     * E.g. {@code 4150259182774861}
     */
    public String creditCardNumber(CreditCardProvider provider) {
        String key = getProviderKey(provider);
        String incompleteNumber = dummy4j.expressionResolver().resolve(key);
        return incompleteNumber + luhnFormula.getCheckDigit(incompleteNumber);
    }

    private String getProviderKey(CreditCardProvider provider) {
        String providerKey = provider.getValue().toLowerCase(Locale.ENGLISH).replaceAll("\\s", "_");
        return PARTIAL_CREDIT_CARD_KEY + providerKey + "}";
    }

    /**
     * Returns a credit card with random data.
     * E.g. {@code CreditCard{number='4150259182774861', provider=Visa, ownerName='Zoe Anderson',
     * ownerAddress='10 Amos Alley, 1234-55 North Austinshire, Canada', "expiryDate='05/2030', securityCode='111'}}
     *
     * @see CreditCard
     */
    public CreditCard creditCard() {
        CreditCardProvider randomProvider = dummy4j.nextEnum(CreditCardProvider.class);

        return creditCard(randomProvider);
    }

    /**
     * Returns a credit card for the given provider.
     * E.g. {@code CreditCard{number='4150259182774861', provider=Visa, ownerName='Zoe Anderson',
     * ownerAddress='10 Amos Alley, 1234-55 North Austinshire, Canada', "expiryDate='05/2030', securityCode='111'}}
     *
     * @see CreditCard
     */
    public CreditCard creditCard(CreditCardProvider provider) {
        String number = creditCardNumber(provider);
        String ownerName = dummy4j.name().firstName() + " " + dummy4j.name().lastName();
        Address ownerAddress = generateAddress();
        String expiryDate = generateExpiryDate();
        String securityCode = String.valueOf(dummy4j.number().nextInt(MIN_SECURITY_CODE, MAX_SECURITY_CODE));

        return new CreditCard(number, provider, ownerName, ownerAddress, expiryDate, securityCode);
    }

    private Address generateAddress() {
        AddressDummy dummy = dummy4j.address();
        return new Address(dummy.street(), dummy.postCode(), dummy.city(), dummy.country());
    }

    private String generateExpiryDate() {
        LocalDate date = dummy4j.dateAndTime().future(MAX_DAYS_FOR_EXPIRY_DATE, ChronoUnit.DAYS).toLocalDate();
        int month = date.getMonthValue();
        int year = date.getYear();

        return Padding.leftPad(String.valueOf(month), 2, '0') + "/" + year;
    }

    /**
     * Provides a random, 11 characters long string, that can be used as a Business Identifier Code,
     * also called a SWIFT code.
     * E.g. {@code RHBHPLPW123}
     */
    public String bic() {
        String bankCode = dummy4j.lorem().characters(4).toUpperCase(Locale.ENGLISH);
        String countryCode = dummy4j.nation().countryCode();
        String locationCode = dummy4j.lorem().characters(2).toUpperCase(Locale.ENGLISH);
        String branchCode = String.valueOf(dummy4j.number().nextInt(100, 999));

        return bankCode + countryCode + locationCode + branchCode;
    }

    /**
     * Provides a random bank account number for a given country.
     * <p>
     * The number constraints are based on the ISO 13616 IBAN Registry published by SWIFT, Release 88 – September 2020.
     * E.g. {@code bankAccountNumber(CountrySupportingBankAccount.GREENLAND)} may generate {@code 30157608510356}
     *
     * @see CountrySupportingBankAccount A list of countries for which a number can be generated
     * @see <a href="https://www.swift.com/standards/data-standards/iban-international-bank-account-number">
     * IBAN Registry</a>
     */
    public String bankAccountNumber(CountrySupportingBankAccount country) {
        String key = PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}";
        String numbers = dummy4j.expressionResolver().resolve(key);
        NumberValidator.inRange(numbers.length(), MIN_ACCOUNT_LENGTH, MAX_ACCOUNT_LENGTH);

        return numbers.replace("_", dummy4j.lorem().character().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Provides a random IBAN number for a randomly chosen country.
     * <p>
     * The number constraints are based on the ISO 13616 IBAN Registry published by SWIFT, Release 88 – September 2020.
     * The country code and check digits are correct while the bank ID, branch ID and account ID are random characters.
     * E.g. {@code GL2530157608510356}.
     *
     * @see CountrySupportingBankAccount A list of countries for which a number can be generated
     * @see <a href="https://www.swift.com/standards/data-standards/iban-international-bank-account-number">
     * IBAN Registry</a>
     */
    public String iban() {
        return new IbanBuilder(dummy4j, ibanFormula).build();
    }

    /**
     * Provides a builder for random IBAN numbers generated according to customisable parameters.
     * <p>
     * The number constraints are based on the ISO 13616 IBAN Registry published by SWIFT, Release 88 – September 2020.
     * The country code and check digits are correct while the bank ID, branch ID and account ID are random characters.
     * E.g. {@code ibanBuilder().withCountry(CountrySupportingBankAccount.GREENLAND).format().build()} may generate
     * {@code GL25 3015 7608 5103 56}.
     *
     * @see CountrySupportingBankAccount A list of countries for which a number can be generated
     * @see <a href="https://www.swift.com/standards/data-standards/iban-international-bank-account-number">
     * IBAN Registry</a>
     */
    public IbanBuilder ibanBuilder() {
        return new IbanBuilder(dummy4j, ibanFormula);
    }

    /**
     * Returns a random bank account type.
     * E.g. {@code Savings}
     */
    public String bankAccountType() {
        return dummy4j.expressionResolver().resolve(BANK_ACCOUNT_TYPE_KEY);
    }

    /**
     * Returns a random payment option.
     * E.g. {@code Debit card}
     */
    public String paymentOption() {
        return dummy4j.expressionResolver().resolve(PAYMENT_OPTION_KEY);
    }

    /**
     * Returns a random financial operation.
     * E.g. {@code Deposit}
     */
    public String financialOperation() {
        return dummy4j.expressionResolver().resolve(FINANCIAL_OPERATION_KEY);
    }

    /**
     * Returns a random Bitcoin address that is 26-35 characters long and doesn't contain the uppercase letter "O",
     * uppercase letter "I", lowercase letter "l", and the number "0".
     * E.g. {@code bc1qarsrrr7xfkvy5643ydnw9re59gtzzwf5mdq}
     * <p>
     * The generated address does not pass any real-life validation.
     *
     * @see <a href="https://en.bitcoin.it/wiki/Address">Bitcoin address</a>
     */
    public String bitcoinAddress() {
        String format = dummy4j.expressionResolver().resolve(BITCOIN_ADDRESS_FORMAT_KEY);
        String characters = getBitcoinAddressChars(format.length());

        return format + characters;
    }

    private String getBitcoinAddressChars(int formatLength) {
        int amount = dummy4j.number().nextInt(BITCOIN_ADDRESS_MIN_LENGTH,
                BITCOIN_ADDRESS_MAX_LENGTH - formatLength);
        char[] characters = new char[amount];

        for (int i = 0; i < amount; i++) {
            characters[i] = dummy4j.expressionResolver().resolve(BITCOIN_ADDRESS_CHARACTERS_KEY).toCharArray()[0];
        }

        return String.valueOf(characters);
    }
}
