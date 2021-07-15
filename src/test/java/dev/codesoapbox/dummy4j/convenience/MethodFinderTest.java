package dev.codesoapbox.dummy4j.convenience;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MethodFinderTest {

    private MethodFinder methodFinder;

    private static Stream<String> invalidValues() {
        return Stream.of("", "   ", null, ".");
    }

    private static Stream<Arguments> validValues() {
        return Stream.of(
                // Standard
                Arguments.of(
                        "simpleMethod",
                        "Found methods containing 'simpleMethod':\n"
                                + "TestDummy.simpleMethod()"),

                // Case insensitive
                Arguments.of(
                        "SIMPLEmethod",
                        "Found methods containing 'SIMPLEmethod':\n"
                                + "TestDummy.simpleMethod()"),

                // Method returning class with no methods
                Arguments.of(
                        "classWithNoMethods",
                        "Found methods containing 'classWithNoMethods':\n"
                                + "TestDummy.classWithNoMethods()")
        );
    }

    @BeforeEach
    void setUp() {
        methodFinder = new MethodFinder(TestDummy.class, new MethodPathLoader());
    }

    @Test
    void findShouldReturnNotFoundMessageWhenMethodNotFound() {
        String searchString = "nonExistentMethod";
        String result = methodFinder.find(searchString);

        String expectedResult = "No methods found containing 'nonExistentMethod'";

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void findShouldReturnNotFoundMessageWhenInvalidValuePassed(String value) {
        String result = methodFinder.find(value);

        String expectedResult = "No methods found containing '" + value + "'";

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest(name = "should find ''{0}''")
    @MethodSource("validValues")
    void shouldFind(String searchString, String expectedResult) {
        String result = methodFinder.find(searchString);

        assertEquals(expectedResult, result);
    }

    @Test
    void shouldFindSortedAlphabetically() {
        String searchString = "alphabeticalMethod";
        String result = methodFinder.find(searchString);

        String expectedResult = "Found methods containing 'alphabeticalMethod':\n" +
                "TestDummy.alphabeticalMethodA()\n" +
                "TestDummy.alphabeticalMethodB()\n" +
                "TestDummy.alphabeticalMethodC()";

        assertEquals(expectedResult, result);
    }

    @Test
    void shouldFindMethodContainingMethods() {
        String searchString = "nestedMethod";
        String result = methodFinder.find(searchString) + "\n";

        assertTrue(result.contains("TestDummy.nestedMethodHolder()\n"));
        assertTrue(result.contains("TestDummy.nestedMethodHolder().nestedMethod()\n"));
        assertFalse(result.contains("simpleMethod"));
    }

    @Test
    void shouldNotFindSubMethodsOfFoundMethod() {
        String searchString = "nestedMethodHolder";
        String result = methodFinder.find(searchString);

        assertTrue(result.contains("TestDummy.nestedMethodHolder()"));
        assertFalse(result.contains("TestDummy.nestedMethodHolder().nestedMethod()"));
    }

    @Test
    void shouldOnlyLoadMethodsOnce() {
        MethodPathLoader mockLoader = mock(MethodPathLoader.class);
        methodFinder = new MethodFinder(TestDummy.class, mockLoader);

        when(mockLoader.load(any())).thenReturn(emptyList());

        methodFinder.find("aaa");
        methodFinder.find("aaa");

        verify(mockLoader, times(1)).load(any());
    }

    private static class TestDummy {

        /**
         * This is to test if MethodFinder can deal with recursion without crashing
         */
        public TestDummy recursiveMethod() {
            return new TestDummy();
        }

        public String simpleMethod() {
            return "";
        }

        public String alphabeticalMethodB() {
            return "";
        }

        public String alphabeticalMethodC() {
            return "";
        }

        public String alphabeticalMethodA() {
            return "";
        }

        public NestedMethodHolder nestedMethodHolder() {
            return new NestedMethodHolder();
        }

        public ClassWithNoMethods classWithNoMethods() {
            return new ClassWithNoMethods();
        }

        private static class NestedMethodHolder {

            public int nestedMethod() {
                return 1;
            }
        }

        public static class ClassWithNoMethods {

        }
    }
}