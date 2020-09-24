package dev.codesoapbox.dummy4j.dummies.shared.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaddingTest {

    @Test
    void shouldPad() {
        assertAll(
                () -> assertEquals("05", Padding.leftPad("5", 2, '0')),
                () -> assertEquals("10", Padding.leftPad("10", 2, '0')),
                () -> assertEquals("  a", Padding.leftPad("a", 3, ' ')),
                () -> assertEquals("__a", Padding.leftPad("a", 3, '_')),
                () -> assertNull(Padding.leftPad(null, 2, '0')),
                () -> assertEquals("123", Padding.leftPad("123", 2, '0'))
        );
    }
}