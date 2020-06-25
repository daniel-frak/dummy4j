package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.exceptions.MissingLocaleException;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Dummy4jBuilderTest {

    @Test
    void shouldBuildWithSeed() {
        long seed = 1234L;
        Dummy4j dummy = new Dummy4jBuilder()
                .seed(seed)
                .build();
        assertEquals(seed, dummy.randomService.getSeed());
    }

    @Test
    void shouldBuildPaths() {
        // Standard path should build fine
        new Dummy4jBuilder().build();

        // Missing path should throw exception
        assertThrows(MissingLocaleException.class, () -> new Dummy4jBuilder()
                .paths(singletonList("somePath"))
                .build());
    }

    @Test
    void shouldBuildWithLocale() {
        assertThrows(MissingLocaleException.class, () -> new Dummy4jBuilder()
                .locale(singletonList("someLocale"))
                .build());
    }
}