package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
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
    private NumberService numberService;

    private LoremDummy loremDummy;

    @BeforeEach
    void setUp() {
        loremDummy = new LoremDummy(dummy4j);
    }

    @Test
    void shouldReturnCharacter() {
        mockExpressionResolver();
        when(expressionResolver.resolve("#{lorem.characters}"))
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
        when(expressionResolver.resolve("#{lorem.characters}"))
                .thenReturn("a");
        assertEquals("aaa", loremDummy.characters(3));
    }

    @Test
    void shouldReturnSentenceEndingWithDot() {
        mockNumberService();
        when(numberService.nextInt(3, 10))
                .thenReturn(4);
        when(dummy4j.number().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolve("#{lorem.word}"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem lorem lorem.", loremDummy.sentence());
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    @Test
    void shouldReturnSentenceEndingWithExclamationMark() {
        mockNumberService();
        when(numberService.nextInt(3, 10))
                .thenReturn(4);
        when(dummy4j.number().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_OTHER);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolve("#{lorem.word}"))
                .thenReturn("lorem");
        when(dummy4j.expressionResolver().resolve("#{lorem.additional_sentence_ending_punctuation}"))
                .thenReturn("!");

        assertEquals("Lorem lorem lorem lorem!", loremDummy.sentence());
    }

    @Test
    void shouldReturnSentenceWithGivenNumberOfWords() {
        mockNumberService();
        when(dummy4j.number().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolve("#{lorem.word}"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem.", loremDummy.sentence(2));
    }

    @Test
    void shouldReturnSentenceOfWordCountBetweenMinAndMax() {
        mockNumberService();
        when(numberService.nextInt(1, 10))
                .thenReturn(1);
        when(dummy4j.number().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolve("#{lorem.word}"))
                .thenReturn("lorem");
        assertEquals("Lorem.", loremDummy.sentence(1, 10));
    }

    @Test
    void shouldReturnWord() {
        mockExpressionResolver();
        when(expressionResolver.resolve("#{lorem.word}"))
                .thenReturn("lorem");

        assertEquals("lorem", loremDummy.word());
    }

    @Test
    void shouldReturnParagraph() {
        mockNumberService();
        when(numberService.nextInt(3, 10))
                .thenReturn(3);
        when(dummy4j.number().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolve("#{lorem.word}"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.", loremDummy.paragraph());
    }

    @Test
    void shouldReturnParagraphWithGivenNumberOfSentences() {
        mockNumberService();
        when(numberService.nextInt(3, 10))
                .thenReturn(3);
        when(dummy4j.number().nextInt(ENDING_CHAR_PROBABILITY))
                .thenReturn(END_WITH_DOT);

        mockExpressionResolver();
        when(dummy4j.expressionResolver().resolve("#{lorem.word}"))
                .thenReturn("lorem");

        assertEquals("Lorem lorem lorem. Lorem lorem lorem.", loremDummy.paragraph(2));
    }
}