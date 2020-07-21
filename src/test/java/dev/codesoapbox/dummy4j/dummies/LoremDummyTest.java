package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.RandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoremDummyTest {

    private static final int ENDING_CHAR_PROBABILITY = 15;
    private static final int END_WITH_DOT = 10;
    private static final int END_WITH_OTHER = 1;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private RandomService randomService;

    private LoremDummy loremDummy;

    @BeforeEach
    void setUp() {
        loremDummy = new LoremDummy(dummy4j);
    }

    @Test
    void shouldReturnCharacter() {
        mockExpressionResolver();
        when(expressionResolver.resolveKey("lorem.characters"))
                .thenReturn("a");
        assertEquals("a", loremDummy.character());
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void shouldReturnStringOfCharacters() {
        mockExpressionResolver();
        when(expressionResolver.resolveKey("lorem.characters"))
                .thenReturn("a");
        assertEquals("aaa", loremDummy.characters(3));
    }

    @Test
    void shouldReturnSentenceEndingWithDot() {
        mockRandomService();
        when(randomService.nextInt(3, 10))
                .thenReturn(4);
        when(dummy4j.random().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolveKey("lorem.word"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem lorem lorem.", loremDummy.sentence());
    }

    private void mockRandomService() {
        when(dummy4j.random())
                .thenReturn(randomService);
    }

    @Test
    void shouldReturnSentenceEndingWithExclamationMark() {
        mockRandomService();
        when(randomService.nextInt(3, 10))
                .thenReturn(4);
        when(dummy4j.random().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_OTHER);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolveKey("lorem.word"))
                .thenReturn("lorem");
        when(dummy4j.expressionResolver().resolveKey("lorem.additional_sentence_ending_punctuation"))
                .thenReturn("!");

        assertEquals("Lorem lorem lorem lorem!", loremDummy.sentence());
    }

    @Test
    void shouldReturnSentenceWithGivenNumberOfWords() {
        mockRandomService();
        when(dummy4j.random().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolveKey("lorem.word"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem.", loremDummy.sentence(2));
    }

    @Test
    void shouldReturnSentenceOfWordCountBetweenMinAndMax() {
        mockRandomService();
        when(randomService.nextInt(1, 10))
                .thenReturn(1);
        when(dummy4j.random().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolveKey("lorem.word"))
                .thenReturn("lorem");
        assertEquals("Lorem.", loremDummy.sentence(1, 10));
    }

    @Test
    void shouldReturnWord() {
        mockExpressionResolver();
        when(expressionResolver.resolveKey("lorem.word"))
                .thenReturn("lorem");

        assertEquals("lorem", loremDummy.word());
    }

    @Test
    void shouldReturnParagraph() {
        mockRandomService();
        when(randomService.nextInt(3, 10))
                .thenReturn(3);
        when(dummy4j.random().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolveKey("lorem.word"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.", loremDummy.paragraph());
    }

    @Test
    void shouldReturnParagraphWithGivenNumberOfSentences() {
        mockRandomService();
        when(randomService.nextInt(3, 10))
                .thenReturn(3);
        when(dummy4j.random().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolveKey("lorem.word"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem lorem. Lorem lorem lorem.", loremDummy.paragraph(2));
    }
}