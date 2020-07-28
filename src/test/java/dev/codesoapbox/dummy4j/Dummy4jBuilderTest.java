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

        Dummy4jBuilder builder = new Dummy4jBuilder()
                .paths(singletonList("somePath"));

        // Missing path should throw exception
        assertThrows(MissingLocaleException.class, builder::build);
    }

    @Test
    void shouldBuildWithLocale() {
        // Standard locale should build fine
        new Dummy4jBuilder().build();

        Dummy4jBuilder builder = new Dummy4jBuilder()
                .locale(singletonList("someLocale"));

        // Missing locale should throw exception
        assertThrows(MissingLocaleException.class, builder::build);
    }
}