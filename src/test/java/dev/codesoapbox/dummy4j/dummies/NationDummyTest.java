package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NationDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    private NationDummy nationDummy;

    @BeforeEach
    void setUp() {
        nationDummy = new NationDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void country() {
        when(expressionResolver.resolve("#{nation.country}"))
                .thenReturn("Spain");
        assertEquals("Spain", nationDummy.country());
    }

    @Test
    void countryCode() {
        when(expressionResolver.resolve("#{nation.country_code}"))
                .thenReturn("AD");
        assertEquals("AD", nationDummy.countryCode());
    }

    @Test
    void nationality() {
        when(expressionResolver.resolve("#{nation.nationality}"))
                .thenReturn("Belarusian");
        assertEquals("Belarusian", nationDummy.nationality());
    }

    @Test
    void languageCodeTwoLetter() {
        when(expressionResolver.resolve("#{nation.language_code_iso_639_1}"))
                .thenReturn("ch");
        assertEquals("ch", nationDummy.languageCodeTwoLetter());
    }

    @Test
    void languageCodeThreeLetter() {
        when(expressionResolver.resolve("#{nation.language_code_iso_639_2}"))
                .thenReturn("alb");
        assertEquals("alb", nationDummy.languageCodeThreeLetter());
    }

    @Test
    void language() {
        when(expressionResolver.resolve("#{nation.language}"))
                .thenReturn("Haitian");
        assertEquals("Haitian", nationDummy.language());
    }

    @Test
    void languageCodeTwoLetterCommon() {
        when(expressionResolver.resolve("#{nation.common_language_code_iso_639_1}"))
                .thenReturn("ja");
        assertEquals("ja", nationDummy.languageCodeTwoLetterCommon());
    }

    @Test
    void languageCodeThreeLetterCommon() {
        when(expressionResolver.resolve("#{nation.common_language_code_iso_639_2}"))
                .thenReturn("spa");
        assertEquals("spa", nationDummy.languageCodeThreeLetterCommon());
    }

    @Test
    void languageCommon() {
        when(expressionResolver.resolve("#{nation.common_language}"))
                .thenReturn("Portuguese");
        assertEquals("Portuguese", nationDummy.languageCommon());
    }
}