package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Since 0.2.0
 */
public class BookDummy {

    private final Dummy4j dummy;

    public BookDummy(Dummy4j dummy) {
        this.dummy = dummy;
    }

    public String title() {
        return dummy.expressionResolver().resolve("#{book.title}");
    }

    public String genre() {
        return dummy.expressionResolver().resolve("#{book.genre}");
    }

    public String genreFiction() {
        return dummy.expressionResolver().resolve("#{book.genre_fiction}");
    }

    public String genreNonFiction() {
        return dummy.expressionResolver().resolve("#{book.genre_nonfiction}");
    }

    public String publisher() {
        return dummy.expressionResolver().resolve("#{book.publisher}");
    }

    public String publisherNonFiction() {
        return dummy.expressionResolver().resolve("#{book.publisher_nonfiction}");
    }

    public String publisherFiction() {
        return dummy.expressionResolver().resolve("#{book.publisher_fiction}");
    }
}
