package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.exceptions.MissingLocaleDefinitionsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

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
    void shouldBuildWithDefaultSeedLocaleAndPaths() {
        Dummy4j dummy4j = new Dummy4jBuilder().build();

        assertNotNull(dummy4j);
    }

    @Test
    void shouldBuildWithGivenAndValidLocaleAndPaths() {
        Dummy4j fromArrays = new Dummy4jBuilder()
                .paths(singletonList("dummy4j"))
                .locale(singletonList("en"))
                .build();

        Dummy4j fromStrings = new Dummy4jBuilder()
                .paths("dummy4j")
                .locale("en")
                .build();

        assertAll(
                () -> assertNotNull(fromArrays),
                () -> assertNotNull(fromStrings)
        );
    }

    @ParameterizedTest
    @CsvSource({
            ",en",
            "dummy4j,",
            ","
    })
    void shouldFallBackToDefaultLocaleAndPathsWhenNullIsGiven(String path, String locale) {
        Dummy4j dummy4j = new Dummy4jBuilder()
                .paths(path)
                .locale(locale)
                .build();

        assertNotNull(dummy4j);
    }

    @Test
    void shouldFallBackToDefaultLocaleAndPathsWhenEmptyArrayIsGiven() {
        Dummy4j bothEmpty = new Dummy4jBuilder()
                .paths(new ArrayList<>())
                .locale(new ArrayList<>())
                .build();

        Dummy4j localeEmpty = new Dummy4jBuilder()
                .paths(singletonList("dummy4j"))
                .locale(new ArrayList<>())
                .build();

        Dummy4j pathEmpty = new Dummy4jBuilder()
                .paths(new ArrayList<>())
                .locale(singletonList("en"))
                .build();

        assertAll(
                () -> assertNotNull(bothEmpty, "Both empty"),
                () -> assertNotNull(localeEmpty, "Locale empty"),
                () -> assertNotNull(pathEmpty, "Paths empty")
        );
    }

    @ParameterizedTest
    @CsvSource({
            "nonExistingPath, en",
            "dummy4j, nonExistingLocale",
            "nonExistingPath, nonExistingLocale"
    })
    void shouldThrowExceptionWhenPathOrLocaleDontExist(String path, String locale) {
        Dummy4jBuilder fromArrays = new Dummy4jBuilder()
                .paths(singletonList(path))
                .locale(singletonList(locale));

        Dummy4jBuilder fromStrings = new Dummy4jBuilder()
                .paths(path)
                .locale(locale);

        assertAll(
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromArrays::build),
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromStrings::build)
        );
    }

    @Test
    void shouldBuildWhenSomeOfTheProvidedPathsAreNull() {
        Dummy4j fromArrays = new Dummy4jBuilder()
                .paths(Arrays.asList("dummy4j", null))
                .locale(singletonList("en"))
                .build();

        Dummy4j fromStrings = new Dummy4jBuilder()
                .paths("dummy4j", null)
                .locale("en")
                .build();

        assertAll(
                () -> assertNotNull(fromArrays),
                () -> assertNotNull(fromStrings)
        );
    }

    @Test
    void shouldThrowExceptionWhenSomeOfTheProvidedLocaleAreNull() {
        Dummy4jBuilder fromArrays = new Dummy4jBuilder()
                .paths(singletonList("dummy4j"))
                .locale(Arrays.asList("en", null));

        Dummy4jBuilder fromStrings = new Dummy4jBuilder()
                .paths("dummy4j")
                .locale("en", null);

        assertAll(
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromArrays::build),
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromStrings::build)
        );
    }
}