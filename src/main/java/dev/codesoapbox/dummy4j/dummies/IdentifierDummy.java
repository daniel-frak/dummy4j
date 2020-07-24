package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @since 0.5.0
 */
public class IdentifierDummy {

    private final Dummy4j dummy4j;

    public IdentifierDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Generates a random UUID
     */
    public UUID uuid() {
        return UUID.nameUUIDFromBytes(String.valueOf(dummy4j.number().nextLong()).getBytes(StandardCharsets.UTF_8));
    }
}
