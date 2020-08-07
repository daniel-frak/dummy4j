package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.exceptions.UrlCouldNotBeCreatedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Generates randomized urls.
 * <p>
 * Default values:
 * <p><ul>
 * <li>port: -1 (no port)</li>
 * <li>protocol: https</li>
 * </ul>
 */
public final class UrlBuilder {

    /**
     * Lower boundary for randomly generated url port
     */
    public static final int MIN_PORT = 1000;

    /**
     * Upper boundary for randomly generated url port
     */
    public static final int MAX_PORT = 9999;

    /**
     * Default port value compatible with the URL class
     */
    public static final int DEFAULT_PORT = -1;

    /**
     * By default, the top level domain is chosen at random from a domain list
     */
    public static final String DEFAULT_TOP_LEVEL_DOMAIN_KEY = "#{internet.top_level_domain}";

    /**
     * If used, the top level domain is chosen at random from a list of popular domains
     */
    public static final String POPULAR_TOP_LEVEL_DOMAIN_KEY = "#{internet.popular_top_level_domain}";

    /**
     * If used, the top level domain is chosen at random from a list of country domains
     */
    public static final String COUNTRY_TOP_LEVEL_DOMAIN_KEY = "#{internet.country_top_level_domain}";

    /**
     * If used, the top level domain is chosen at random from a list of country domains
     */
    public static final String GENERIC_TOP_LEVEL_DOMAIN_KEY = "#{internet.generic_top_level_domain}";

    /**
     * Root domain is chosen at random from a list of words
     */
    public static final String ROOT_DOMAIN_KEY = "#{internet.root_domain}";

    private static final String FILE_EXTENSION = ".html";
    private static final String PATH_DELIMITER = "/";
    private static final int FILE_EXTENSION_LENGTH = 5;
    private static final int FILE_NAME_LENGTH = 10;

    private final Dummy4j dummy4j;

    private List<UrlProtocol> possibleProtocols = Collections.singletonList(UrlProtocol.HTTPS);
    private boolean withQueryParams;
    private int howManyParams = 1;
    private boolean withFilePath;
    private boolean withoutWwwPrefix;
    private int port = DEFAULT_PORT;
    private String domainKey = DEFAULT_TOP_LEVEL_DOMAIN_KEY;
    private String customTopLevelDomain;
    private int minLength;

    public UrlBuilder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Sets the protocol for the generated url
     */
    public UrlBuilder withProtocol(UrlProtocol protocol) {
        this.possibleProtocols = Collections.singletonList(protocol);
        return this;
    }

    /**
     * Sets the protocol for the generated url to one that is randomly chosen from provided arguments.
     * If there are no arguments, a protocol is chosen at random from the {@code UrlProtocol} enum.
     */
    public UrlBuilder withRandomProtocol(UrlProtocol... protocols) {
        this.possibleProtocols = Arrays.asList(protocols);
        return this;
    }

    /**
     * Removes the {@code www} prefix from the generated url
     */
    public UrlBuilder withoutWwwPrefix() {
        withoutWwwPrefix = true;
        return this;
    }

    /**
     * Sets the port for the generated url.
     * <p>
     * Default value is {@code -1} which means no port will be present in the generated url.
     * <p>
     * The port must be greater than {@code -1}.
     */
    public UrlBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Sets the port to a random 4-digit {@code int} value
     */
    public UrlBuilder withRandomPort() {
        withPort(dummy4j.number().nextInt(MIN_PORT, MAX_PORT));
        return this;
    }

    /**
     * Adds a file name constructed from 10 random characters with the {@code .html} extension
     */
    public UrlBuilder withFilePath() {
        withFilePath = true;
        return this;
    }

    /**
     * Adds one query param with a randomly generated value that will be either numeric or alphabetical
     */
    public UrlBuilder withQueryParam() {
        withQueryParams = true;
        return this;
    }

    /**
     * Adds the specified amount of query params with a randomly generated values that will be either
     * numeric or alphabetical.
     *
     * @throws IllegalArgumentException when a negative number is given as the argument
     */
    public UrlBuilder withQueryParams(int howManyParams) {
        if (howManyParams == 0) {
            return this;
        }
        if (howManyParams < 0) {
            String message = String.format("The specified amount of query params must be a positive number," +
                    " but \"%d\" was given", howManyParams);
            throw new IllegalArgumentException(message);
        }
        withQueryParam();
        this.howManyParams = howManyParams;
        return this;
    }

    /**
     * Sets the top level domain for the generated url
     */
    public UrlBuilder withCustomTopLevelDomain(String domain) {
        this.customTopLevelDomain = domain;
        return this;
    }

    /**
     * The top level domain is chosen at random from a list of popular domains.
     * <p>
     * E.g. {@code com, dev, io, org}.
     */
    public UrlBuilder withPopularTopLevelDomain() {
        customTopLevelDomain = null;
        domainKey = POPULAR_TOP_LEVEL_DOMAIN_KEY;
        return this;
    }

    /**
     * The top level domain is chosen at random from a list of country domains.
     * <p>
     * E.g. {@code es, uk, it}.
     */
    public UrlBuilder withCountryTopLevelDomain() {
        customTopLevelDomain = null;
        domainKey = COUNTRY_TOP_LEVEL_DOMAIN_KEY;
        return this;
    }

    /**
     * The top level domain is chosen at random from a list of generic domains.
     * <p>
     * E.g. {@code academy, photography, tech}.
     */
    public UrlBuilder withGenericTopLevelDomain() {
        customTopLevelDomain = null;
        domainKey = GENERIC_TOP_LEVEL_DOMAIN_KEY;
        return this;
    }

    /**
     * The generated url will be expanded with random characters until it is {@code minLength} characters long.
     * <p>
     * Random characters will be added to a domain, a filename or the value of the last query param
     * – depends on which elements are included in the generated url.
     * <p>
     * In case the generated url is already at least {@code minLength} characters long no characters will be added.
     */
    public UrlBuilder minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * Returns a URL instance
     *
     * @throws UrlCouldNotBeCreatedException if the url can't be created
     * @see URL
     */
    public URL build() {
        UrlProtocol protocol = getProtocol();
        UrlHost host = getUrlHost();
        String filePathAndQueryParams = getFilePath();

        if (withQueryParams) {
            filePathAndQueryParams += getParams();
        }

        return buildUrlWithProperLength(protocol, host, filePathAndQueryParams);
    }

    private UrlProtocol getProtocol() {
        if (possibleProtocols.isEmpty()) {
            return dummy4j.nextEnum(UrlProtocol.class);
        } else if (possibleProtocols.size() == 1) {
            return possibleProtocols.get(0);
        } else {
            int randomIndex = dummy4j.number().nextInt(possibleProtocols.size() - 1);
            return possibleProtocols.get(randomIndex);
        }
    }

    private UrlHost getUrlHost() {
        String rootDomain = dummy4j.expressionResolver().resolve(ROOT_DOMAIN_KEY);
        if(customTopLevelDomain != null) {
            return new UrlHost(rootDomain, customTopLevelDomain, withoutWwwPrefix);
        }
        String topLevelDomain = dummy4j.expressionResolver().resolve(domainKey);
        return new UrlHost(rootDomain, topLevelDomain, withoutWwwPrefix);
    }

    private String getFilePath() {
        if (withFilePath) {
            return PATH_DELIMITER + dummy4j.lorem().characters(FILE_NAME_LENGTH) + FILE_EXTENSION;
        }
        return "";
    }

    private String getParams() {
        StringJoiner path = new StringJoiner("&", "?", "");
        for (int i = 0; i < howManyParams; i++) {
            path.add(dummy4j.expressionResolver().resolve(ROOT_DOMAIN_KEY) + "=" + generateParamValue());
        }
        return path.toString();
    }

    private String generateParamValue() {
        if (dummy4j.number().nextInt(6) > 4) {
            return String.valueOf(dummy4j.number().nextInt());
        }
        return dummy4j.expressionResolver().resolve(ROOT_DOMAIN_KEY);
    }

    private URL buildUrlWithProperLength(UrlProtocol protocol, UrlHost host, String filePath) {
        URL url = buildUrl(protocol, host, filePath);
        int urlLength = url.toString().length();

        if (urlLength >= minLength) {
            return url;
        }

        int missingLength = minLength - urlLength;
        return buildUrlAdjustedForLength(protocol, host, filePath, missingLength);
    }

    private URL buildUrl(UrlProtocol protocol, UrlHost host, String filePath) {
        try {
            return new URL(protocol.getValue(), host.toString(), port, filePath);
        } catch (MalformedURLException e) {
            throw new UrlCouldNotBeCreatedException(e);
        }
    }

    private URL buildUrlAdjustedForLength(UrlProtocol protocol, UrlHost host, String filePath, int missingLength) {
        if (withFilePath && !withQueryParams) {
            filePath = increaseFileNameLength(filePath, missingLength);
        } else if (withQueryParams) {
            filePath = increaseLastParamValueLength(filePath, missingLength);
        } else {
            host = increaseRootDomainLength(host, missingLength);
        }

        return buildUrl(protocol, host, filePath);
    }

    private String increaseFileNameLength(String filePath, int missingLength) {
        String withoutExtension = filePath.substring(0, filePath.length() - FILE_EXTENSION_LENGTH);
        String extraCharacters = dummy4j.lorem().characters(missingLength);
        return withoutExtension + extraCharacters + FILE_EXTENSION;
    }

    private String increaseLastParamValueLength(String filePath, int missingLength) {
        return filePath + dummy4j.lorem().characters(missingLength);
    }

    private UrlHost increaseRootDomainLength(UrlHost host, int missingLength) {
        String rootDomain = host.getRootDomain() + dummy4j.lorem().characters(missingLength);
        host = new UrlHost(rootDomain, host.getTopLevelDomain(), withoutWwwPrefix);
        return host;
    }

    @Override
    public String toString() {
        return "UrlBuilder{" +
                "possibleProtocols=" + possibleProtocols +
                ", withQueryParams=" + withQueryParams +
                ", howManyParams=" + howManyParams +
                ", withFilePath=" + withFilePath +
                ", withoutWwwPrefix=" + withoutWwwPrefix +
                ", port=" + port +
                ", domainKey='" + domainKey + '\'' +
                ", minLength=" + minLength +
                '}';
    }
}
