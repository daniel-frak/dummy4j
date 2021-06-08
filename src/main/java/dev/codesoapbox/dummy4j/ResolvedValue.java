package dev.codesoapbox.dummy4j;

import java.util.Objects;

public final class ResolvedValue {

    private final String locale;
    private final String value;

    private ResolvedValue(String locale, String value) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        this.locale = locale;
        this.value = value;
    }

    public static ResolvedValue of(String locale, String value) {
        return new ResolvedValue(locale, value);
    }

    public String getLocale() {
        return locale;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResolvedValue)) return false;
        ResolvedValue that = (ResolvedValue) o;
        return locale.equals(that.locale) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, value);
    }
}
