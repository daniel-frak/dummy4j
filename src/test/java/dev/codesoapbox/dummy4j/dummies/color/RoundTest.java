package dev.codesoapbox.dummy4j.dummies.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundTest {

    @Test
    void shouldRound() {
        assertAll(
                () -> assertEquals(200.13F, Round.toTwoDecimals(200.129F)),
                () -> assertEquals(0.56F, Round.toTwoDecimals(0.559F)),
                () -> assertEquals(-0.56F, Round.toTwoDecimals(-0.5555F)),
                () -> assertEquals(0.02F, Round.toTwoDecimals(0.019F)),
                () -> assertEquals(0.21F, Round.toTwoDecimals(0.209F)),
                () -> assertEquals(0.21F, Round.toTwoDecimals(0.205F))
        );
    }
}