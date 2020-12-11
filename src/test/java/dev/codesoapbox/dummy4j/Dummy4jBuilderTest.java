package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.exceptions.MissingLocaleDefinitionsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
    void shouldBuildWithGivenAndExistingLocaleAndPaths() {
        Dummy4j fromList = new Dummy4jBuilder()
                .paths(singletonList("dummy4j"))
                .locale(singletonList("en"))
                .build();

        Dummy4j fromVarArgs = new Dummy4jBuilder()
                .paths("dummy4j")
                .locale("en")
                .build();

        assertAll(
                () -> assertNotNull(fromList),
                () -> assertNotNull(fromVarArgs)
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
    void shouldFallBackToDefaultLocaleAndPathsWhenEmptyListsAreGiven() {
        Dummy4j bothEmpty = new Dummy4jBuilder()
                .paths(emptyList())
                .locale(emptyList())
                .build();

        Dummy4j localeEmpty = new Dummy4jBuilder()
                .paths(singletonList("dummy4j"))
                .locale(emptyList())
                .build();

        Dummy4j pathEmpty = new Dummy4jBuilder()
                .paths(emptyList())
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
    void shouldThrowExceptionWhenPathOrLocaleDoesNotExist(String path, String locale) {
        Dummy4jBuilder fromList = new Dummy4jBuilder()
                .paths(singletonList(path))
                .locale(singletonList(locale));

        Dummy4jBuilder fromVarArgs = new Dummy4jBuilder()
                .paths(path)
                .locale(locale);

        assertAll(
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromList::build),
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromVarArgs::build)
        );
    }

    @Test
    void shouldBuildWhenSomeOfTheProvidedPathsAreNull() {
        Dummy4j fromList = new Dummy4jBuilder()
                .paths(asList("dummy4j", null))
                .locale(singletonList("en"))
                .build();

        Dummy4j fromVarArgs = new Dummy4jBuilder()
                .paths("dummy4j", null)
                .locale("en")
                .build();

        assertAll(
                () -> assertNotNull(fromList),
                () -> assertNotNull(fromVarArgs)
        );
    }

    @Test
    void shouldBuildWhenSomeOfTheProvidedPathsAreNonExisting() {
        Dummy4j fromList = new Dummy4jBuilder()
                .paths(asList("dummy4j", "nonExistingPath"))
                .locale(singletonList("en"))
                .build();

        Dummy4j fromVarArgs = new Dummy4jBuilder()
                .paths("dummy4j", "nonExistingPath")
                .locale("en")
                .build();

        assertAll(
                () -> assertNotNull(fromList),
                () -> assertNotNull(fromVarArgs)
        );
    }

    @Test
    void shouldThrowExceptionWhenSomeOfTheProvidedLocaleAreNull() {
        Dummy4jBuilder fromList = new Dummy4jBuilder()
                .paths(singletonList("dummy4j"))
                .locale(asList("en", null));

        Dummy4jBuilder fromVarArgs = new Dummy4jBuilder()
                .paths("dummy4j")
                .locale("en", null);

        assertAll(
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromList::build),
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromVarArgs::build)
        );
    }

    @Test
    void shouldThrowExceptionWhenSomeOfTheProvidedLocaleAreNonExisting() {
        Dummy4jBuilder fromList = new Dummy4jBuilder()
                .paths(singletonList("dummy4j"))
                .locale(asList("en", "nonExistingLocale"));

        Dummy4jBuilder fromVarArgs = new Dummy4jBuilder()
                .paths("dummy4j")
                .locale("en", "nonExistingLocale");

        assertAll(
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromList::build),
                () -> assertThrows(MissingLocaleDefinitionsException.class, fromVarArgs::build)
        );
    }
}