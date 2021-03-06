package dev.codesoapbox.dummy4j.dummies.finance;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Enum containing the supported card-issuing entities
 *
 * @since 0.6.0
 */
public enum CreditCardProvider {

    VISA("Visa", singletonList(IINRange.from(4, 4))),

    VISA_ELECTRON("Visa Electron", asList(IINRange.from(4026, 4026), IINRange.from(417_500, 417_500),
            IINRange.from(4405, 4405), IINRange.from(4508, 4508), IINRange.from(4844, 4844), IINRange.from(4913, 4913),
            IINRange.from(4917, 4917))),

    MASTER_CARD("MasterCard",
            asList(IINRange.from(51, 55), IINRange.from(222_100, 272_099))),

    MAESTRO("Maestro",
            asList(IINRange.from(500_000, 509_999), IINRange.from(560_000, 589_999), IINRange.from(600_000, 699_999))),

    AMERICAN_EXPRESS("American Express",
            asList(IINRange.from(34, 34), IINRange.from(37, 37))),

    DISCOVER("Discover",
            asList(IINRange.from(6011, 6011), IINRange.from(622_126, 622_925), IINRange.from(644, 649),
                    IINRange.from(65, 65))),

    JCB("JCB", singletonList(IINRange.from(3528, 3589)));

    private final String name;
    private final List<IINRange> iinRanges;

    CreditCardProvider(String name, List<IINRange> iinRanges) {
        this.name = name;
        this.iinRanges = iinRanges;
    }

    /**
     * Returns a name for the provider.
     * E.g. {@code American Express}
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the list of available IIN ranges for the provider.
     * E.g. {@code [IINRange{min=51, max=55}, IINRange{min=222100, max=272099}]} for MasterCard.
     * <p>
     * The available IIN ranges are based on the data available on November 2020.
     *
     * @see <a href="https://baymard.com/checkout-usability/credit-card-patterns">
     * Credit Card IIN Ranges and Spacing Patterns</a>
     */
    public List<IINRange> getIinRanges() {
        return iinRanges;
    }
}
