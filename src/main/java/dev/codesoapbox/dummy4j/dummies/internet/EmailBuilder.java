package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static java.util.Collections.singletonList;

/**
 * Generates a randomized email.
 * <p>
 * Default values:
 * <p>
 * <ul>
 *  <li>sanitize: {@code true}</li>
 *  <li>local part delimiter: {@code .}</li>
 * </ul>
 */
public class EmailBuilder {

    /**
     * Points to a list of possible email providers
     */
    public static final String DOMAIN_KEY = "#{internet.email_domain}";

    /**
     * Points to a list of possible top and second level domains that can be safely used for testing and documentation
     *
     * @see <a href="https://tools.ietf.org/html/rfc2606">https://tools.ietf.org/html/rfc2606</a>
     */
    public static final String SAFE_DOMAIN_KEY = "#{internet.email_safe_domains}";

    /**
     * Points to a list of possible sub-addresses
     */
    public static final String SUB_ADDRESS_KEY = "#{internet.email_sub_address}";

    private final Dummy4j dummy4j;

    private String customDomain;
    private String customLocalPart;
    private List<String> customSubAddresses = new ArrayList<>();
    private boolean randomizeSubAddress;
    private String localPartDelimiter = ".";
    private boolean sanitize = true;

    public EmailBuilder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Sets the domain to one that is randomly selected from a list of domain names reserved for testing
     * and documentation
     */
    public EmailBuilder safe() {
        customDomain = dummy4j.expressionResolver().resolve(SAFE_DOMAIN_KEY);
        return this;
    }

    /**
     * Sets the domain
     */
    public EmailBuilder withDomain(String customDomain) {
        this.customDomain = customDomain;
        return this;
    }

    /**
     * Sets the local part
     */
    public EmailBuilder withLocalPart(String localPart) {
        this.customLocalPart = localPart;
        return this;
    }

    /**
     * Sets the delimiter used in a randomly generated local part.
     * <p>
     * Ignored when the {@code withLocalPart()} option is also used.
     */
    public EmailBuilder withLocalPartDelimiter(String delimiter) {
        this.localPartDelimiter = delimiter;
        return this;
    }

    /**
     * Sets the sub-address to a value chosen at random.
     * <p>
     * E.g. {@code zoe.anderson+random-sub-address@gmail.com}
     */
    public EmailBuilder withRandomSubAddress() {
        randomizeSubAddress = true;
        customSubAddresses = new ArrayList<>();
        return this;
    }

    /**
     * Adds every provided value to the local part as a sub-address.
     * <p>
     * E.g. using {@code withSubAddresses("tag1", "tag2")} will produce an email similar to
     * {@code zoe.anderson+tag1+tag2@gmail.com}
     */
    public EmailBuilder withSubAddresses(String... customSubAddresses) {
        randomizeSubAddress = false;
        this.customSubAddresses = Arrays.asList(customSubAddresses);
        return this;
    }

    /**
     * Skips email sanitization.
     * <p>
     * By default, whitespaces, quotes, backslashes and non-ASCII characters will be removed from the local part.
     * <p>
     * Use this option if you don't want the local part to be sanitized.
     */
    public EmailBuilder notSanitized() {
        sanitize = false;
        return this;
    }

    /**
     * Returns a randomly generated email
     */
    public String build() {
        return getFullLocalPart() + "@" + getDomain();
    }

    private String getFullLocalPart() {
        String localPart = getLocalPart();
        localPart += processSubAddresses(getSubAddresses());

        if (sanitize) {
            localPart = StringSanitizer.sanitizeForEmail(localPart);
        }

        return localPart;
    }

    private String getLocalPart() {
        if (this.customLocalPart != null) {
            return customLocalPart;
        } else {
            return getRandomLocalPart();
        }
    }

    private String getRandomLocalPart() {
        String firstName = dummy4j.name().firstName().toLowerCase(Locale.ENGLISH);
        String lastName = dummy4j.name().lastName().toLowerCase(Locale.ENGLISH);

        return firstName + localPartDelimiter + lastName;
    }

    private String processSubAddresses(List<String> subAddresses) {
        if (subAddresses.isEmpty()) {
            return "";
        }

        return "+" + String.join("+", subAddresses);
    }

    private List<String> getSubAddresses() {
        if (randomizeSubAddress) {
            return singletonList(dummy4j.expressionResolver().resolve(SUB_ADDRESS_KEY));
        }

        return customSubAddresses;
    }

    private String getDomain() {
        if (customDomain != null) {
            return customDomain;
        } else {
            return dummy4j.expressionResolver().resolve(DOMAIN_KEY);
        }
    }

    @Override
    public String toString() {
        return "EmailBuilder{" +
                "customDomain='" + customDomain + '\'' +
                ", customLocalPart='" + customLocalPart + '\'' +
                ", customSubAddresses=" + customSubAddresses +
                ", randomizeSubAddress=" + randomizeSubAddress +
                ", localPartDelimiter='" + localPartDelimiter + '\'' +
                ", sanitize=" + sanitize +
                '}';
    }
}
