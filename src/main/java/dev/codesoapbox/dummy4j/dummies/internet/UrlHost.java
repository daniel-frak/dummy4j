package dev.codesoapbox.dummy4j.dummies.internet;

import java.util.Objects;

/**
 * Value object used during url generation.
 * <p>
 * Represents a url host, e.g. {@code www.example.com}.
 */
public final class UrlHost {

    private static final String DELIMITER = ".";
    private static final String PREFIX_VALUE = "www";

    private final String wwwPrefix;
    private final String rootDomain;
    private final String topLevelDomain;

    public UrlHost(String rootDomain, String topLevelDomain, boolean withoutPrefix) {
        this.rootDomain = rootDomain;
        this.topLevelDomain = topLevelDomain;
        if (withoutPrefix) {
            this.wwwPrefix = "";
        } else {
            this.wwwPrefix = PREFIX_VALUE;
        }
    }

    public String getRootDomain() {
        return rootDomain;
    }

    public String getTopLevelDomain() {
        return topLevelDomain;
    }

    @Override
    public String toString() {
        if (wwwPrefix.isEmpty()) {
            return rootDomain + DELIMITER + topLevelDomain;
        } else {
            return wwwPrefix + DELIMITER + rootDomain + DELIMITER + topLevelDomain;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrlHost urlHost = (UrlHost) o;
        return Objects.equals(wwwPrefix, urlHost.wwwPrefix) &&
                Objects.equals(rootDomain, urlHost.rootDomain) &&
                Objects.equals(topLevelDomain, urlHost.topLevelDomain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wwwPrefix, rootDomain, topLevelDomain);
    }
}
