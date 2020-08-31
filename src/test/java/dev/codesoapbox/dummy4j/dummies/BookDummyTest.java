package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    private BookDummy bookDummy;

    @BeforeEach
    void setUp() {
        bookDummy = new BookDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void title() {
        when(expressionResolver.resolve("#{book.title}"))
                .thenReturn("Title");
        assertEquals("Title", bookDummy.title());
    }

    @Test
    void genre() {
        when(expressionResolver.resolve("#{book.genre}"))
                .thenReturn("Genre");
        assertEquals("Genre", bookDummy.genre());
    }

    @Test
    void genreFiction() {
        when(expressionResolver.resolve("#{book.genre_fiction}"))
                .thenReturn("Fiction");
        assertEquals("Fiction", bookDummy.genreFiction());
    }

    @Test
    void genreNonFiction() {
        when(expressionResolver.resolve("#{book.genre_nonfiction}"))
                .thenReturn("Non fiction");
        assertEquals("Non fiction", bookDummy.genreNonFiction());
    }

    @Test
    void publisher() {
        when(expressionResolver.resolve("#{book.publisher}"))
                .thenReturn("Publisher");
        assertEquals("Publisher", bookDummy.publisher());
    }

    @Test
    void publisherNonFiction() {
        when(expressionResolver.resolve("#{book.publisher_nonfiction}"))
                .thenReturn("Publisher Non Fiction");
        assertEquals("Publisher Non Fiction", bookDummy.publisherNonFiction());
    }

    @Test
    void publisherFiction() {
        when(expressionResolver.resolve("#{book.publisher_fiction}"))
                .thenReturn("Publisher Fiction");
        assertEquals("Publisher Fiction", bookDummy.publisherFiction());
    }
}