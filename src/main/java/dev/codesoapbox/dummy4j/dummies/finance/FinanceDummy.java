package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.regex.Pattern;

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
    static final String BANK_ACCOUNT_TYPE_KEY = "#{finance.bank_account.type}";
    static final String BANK_ACCOUNT_LETTER_KEY = "#{finance.bank_account.letter}";
    static final Pattern BANK_ACCOUNT_LETTER_PLACEHOLDER_PATTERN = Pattern.compile("_");
    static final String PARTIAL_ACCOUNT_NUMBER_KEY = "#{finance.bank_account.number_structure.";
    static final String PAYMENT_OPTION_KEY = "#{finance.bank_account.payment_option}";
    static final String FINANCIAL_OPERATION_KEY = "#{finance.bank_account.financial_operation}";
    static final String BITCOIN_ADDRESS_FORMAT_KEY = "#{finance.bank_account.bitcoin_address.format}";
    static final String BITCOIN_ADDRESS_CHARACTERS_KEY = "#{finance.bank_account.bitcoin_address.characters}";
    static final int BITCOIN_ADDRESS_MIN_LENGTH = 26;
    static final int BITCOIN_ADDRESS_MAX_LENGTH = 35;

    private final Dummy4j dummy4j;
    private final FinanceBuilderFactory financeBuilderFactory;

    public FinanceDummy(Dummy4j dummy4j, FinanceBuilderFactory financeBuilderFactory) {
        this.dummy4j = dummy4j;
        this.financeBuilderFactory = financeBuilderFactory;
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
     * Provides a random currency code compliant with the ISO 4217 standard.
     * E.g. {@code CHF}
     */
    public String currencyCode() {
        return dummy4j.expressionResolver().resolve(CURRENCY_CODE_KEY);
    }

    /**
     * Provides a random currency numeric code compliant with the ISO 4217 standard.
     * E.g. {@code 971}
     */
    public String currencyNumericCode() {
        return dummy4j.expressionResolver().resolve(CURRENCY_NUMERIC_CODE_KEY);
    }

    /**
     * Provides a random currency name compliant with the ISO 4217 standard.
     * E.g. {@code Armenian Dram}
     */
    public String currencyName() {
        return dummy4j.expressionResolver().resolve(CURRENCY_NAME_KEY);
    }

    /**
     * Provides a random currency symbol.
     * E.g. {@code £}
     *
     * @see <a href="https://en.wikipedia.org/wiki/Currency_symbol#List_of_currency_symbols_currently_in_use">
     * Symbols chosen from this list (November 2020)</a>
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
     * Provides a credit card provider chosen at random from the {@code CreditCardProvider} enum values.
     * E.g. {@code creditCardProvider().getName()} may return {@code Visa}
     *
     * @see CreditCardProvider
     */
    public CreditCardProvider creditCardProvider() {
        return dummy4j.nextEnum(CreditCardProvider.class);
    }

    /**
     * Provides a random credit card number compliant with the ISO/IEC 7812 standard.
     * E.g. {@code 4150 2591 8277 4861}
     * <p>
     * The default format patterns and IIN ranges are based on data available on November 2020.
     *
     * @see <a href="https://baymard.com/checkout-usability/credit-card-patterns">
     * Credit Card IIN Ranges and Spacing Patterns</a>
     */
    public String creditCardNumber() {
        return financeBuilderFactory.createCreditCardNumberBuilder().build();
    }

    /**
     * Provides a builder for random credit card numbers compliant with the ISO/IEC 7812 standard that can be created
     * according to customisable parameters.
     * E.g. {@code creditCardNumberBuilder().withoutFormatting().build()} may generate {@code 4150259182774861}
     * <p>
     * The default format patterns and IIN ranges are based on data available on November 2020.
     *
     * @see <a href="https://baymard.com/checkout-usability/credit-card-patterns">
     * Credit Card IIN Ranges and Spacing Patterns</a>
     */
    public CreditCardNumberBuilder creditCardNumberBuilder() {
        return financeBuilderFactory.createCreditCardNumberBuilder();
    }

    /**
     * Returns a credit card with random data.
     * E.g. {@code CreditCard{number='4150 2591 8277 4861', provider=Visa, ownerName='Zoe Anderson',
     * ownerAddress='10 Amos Alley, 1234-55 North Austinshire, Canada', "expiryDate='05/2030', securityCode='111'}}
     * <p>
     * The default format patterns and IIN ranges are based on data available on November 2020.
     *
     * @see <a href="https://baymard.com/checkout-usability/credit-card-patterns">
     * Credit Card IIN Ranges and Spacing Patterns</a>
     * @see CreditCard
     */
    public CreditCard creditCard() {
        return financeBuilderFactory.createCreditCardBuilder().build();
    }

    /**
     * Provides a builder for a random credit cards created according to customisable parameters.
     * E.g. {@code creditCardBuilder().withProvider(CreditCardProvider.VISA).withoutNumberFormatting().build()}
     * may generate {@code CreditCard{number='4150259182774861', provider=Visa, ownerName='Zoe Anderson',
     * ownerAddress='10 Amos Alley, 1234-55 North Austinshire, Canada', "expiryDate='05/2030', securityCode='111'}}
     * <p>
     * The default format patterns and IIN ranges are based on data available on November 2020.
     *
     * @see <a href="https://baymard.com/checkout-usability/credit-card-patterns">
     * Credit Card IIN Ranges and Spacing Patterns</a>
     * @see CreditCard
     */
    public CreditCardBuilder creditCardBuilder() {
        return financeBuilderFactory.createCreditCardBuilder();
    }

    /**
     * Provides a random, 11 characters long string, that can be used as a Business Identifier Code,
     * also called a SWIFT code.
     * E.g. {@code RHBHPLPW123}, where {@code RHBH} is a fake bank code, {@code PL} is a random country code, {@code PW}
     * is a fake location code and {@code 123} is a fake branch code.
     */
    public String bic() {
        String bankCode = getBankAccountLetters(4);
        String countryCode = dummy4j.nation().countryCode();
        String locationCode = getBankAccountLetters(2);
        String branchCode = String.valueOf(dummy4j.number().nextInt(100, 999));

        return bankCode + countryCode + locationCode + branchCode;
    }

    private String getBankAccountLetters(int howMany) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < howMany; i++) {
            code.append(dummy4j.expressionResolver().resolve(BANK_ACCOUNT_LETTER_KEY));
        }

        return code.toString();
    }

    /**
     * Provides a random bank account number for a given country.
     * E.g. {@code bankAccountNumber(BankAccountCountry.GREENLAND)} may generate {@code 30157608510356}
     *
     * @see BankAccountCountry
     */
    public String bankAccountNumber(BankAccountCountry country) {
        String key = PARTIAL_ACCOUNT_NUMBER_KEY + country.getCode() + "}";
        String numbers = dummy4j.expressionResolver().resolve(key);

        return BANK_ACCOUNT_LETTER_PLACEHOLDER_PATTERN.matcher(numbers)
                .replaceAll(dummy4j.expressionResolver().resolve(BANK_ACCOUNT_LETTER_KEY));
    }

    /**
     * Provides a random IBAN number for a randomly chosen country.
     * <p>
     * The country code and check digits are correct while the bank ID, branch ID and account ID are random characters.
     * E.g. {@code GL2530157608510356}.
     *
     * @see BankAccountCountry
     */
    public String iban() {
        return financeBuilderFactory.createIbanBuilder().build();
    }

    /**
     * Provides a builder for random IBAN numbers generated according to customisable parameters.
     * <p>
     * The country code and check digits are correct while the bank ID, branch ID and account ID are random characters.
     * E.g. {@code ibanBuilder().withCountry(BankAccountCountry.GREENLAND).formatted().build()} may generate
     * {@code GL25 3015 7608 5103 56}.
     *
     * @see BankAccountCountry
     */
    public IbanBuilder ibanBuilder() {
        return financeBuilderFactory.createIbanBuilder();
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
     * Returns a random Bitcoin address that is 26-35 characters long.
     * E.g. {@code bc1qarsrrr7xfkvy5643ydnw9re59gtzzwf5mdq}
     * <p>
     * While the generated address doesn't contain forbidden characters (uppercase letter "O", uppercase letter "I",
     * lowercase letter "l", and the number "0") it won't pass any real-life validation.
     *
     * @see <a href="https://en.bitcoin.it/wiki/Address">Bitcoin address</a>
     */
    public String bitcoinAddress() {
        String format = dummy4j.expressionResolver().resolve(BITCOIN_ADDRESS_FORMAT_KEY);
        String characters = getBitcoinAddressChars(format.length());

        return format + characters;
    }

    private String getBitcoinAddressChars(int formatLength) {
        int length = dummy4j.number().nextInt(BITCOIN_ADDRESS_MIN_LENGTH - formatLength,
                BITCOIN_ADDRESS_MAX_LENGTH - formatLength);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(dummy4j.expressionResolver().resolve(BITCOIN_ADDRESS_CHARACTERS_KEY).toCharArray()[0]);
        }
        return builder.toString();
    }
}
