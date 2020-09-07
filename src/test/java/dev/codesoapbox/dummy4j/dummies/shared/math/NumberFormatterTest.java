package dev.codesoapbox.dummy4j.dummies.shared.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberFormatterTest {

    @Test
    void shouldFormatToString() {
        assertAll(
                () -> assertEquals("2.2", NumberFormatter.toTwoDecimals(2.2011F)),
                () -> assertEquals("2.21", NumberFormatter.toTwoDecimals(2.211F)),
                () -> assertEquals("2.2", NumberFormatter.toTwoDecimals(2.205F)),
                () -> assertEquals("-200", NumberFormatter.toTwoDecimals(-200.00F)),
                () -> assertEquals("2.9", NumberFormatter.toTwoDecimals(2.899F))
        );
    }

    @Test
    void shouldFormatToPercent() {
        assertAll(
                () -> assertEquals("25%", NumberFormatter.toPercent(0.25F)),
                () -> assertEquals("20%", NumberFormatter.toPercent(0.20005F)),
                () -> assertEquals("25.9%", NumberFormatter.toPercent(0.259F)),
                () -> assertEquals("25.91%", NumberFormatter.toPercent(0.25912345F))

        );
    }
}