package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating book-related values
 *
 * Since 0.2.0
 */
public class BookDummy {

    private final Dummy4j dummy;

    public BookDummy(Dummy4j dummy) {
        this.dummy = dummy;
    }

    /**
     * Generates a random book title.
     * E.g. {@code "The Daughters of the Past"}
     */
    public String title() {
        return dummy.expressionResolver().resolve("#{book.title}");
    }

    /**
     * Provides a random genre
     */
    public String genre() {
        return dummy.expressionResolver().resolve("#{book.genre}");
    }

    /**
     * Provides a random genre from the fiction category
     */
    public String genreFiction() {
        return dummy.expressionResolver().resolve("#{book.genre_fiction}");
    }

    /**
     * Provides a random genre from the non-fiction category
     */
    public String genreNonFiction() {
        return dummy.expressionResolver().resolve("#{book.genre_nonfiction}");
    }

    /**
     * Generates a random publisher
     */
    public String publisher() {
        return dummy.expressionResolver().resolve("#{book.publisher}");
    }

    /**
     * Generates a random publisher name suitable for non-fiction genres.
     * E.g. {@code North Willismouth University Press}
     */
    public String publisherNonFiction() {
        return dummy.expressionResolver().resolve("#{book.publisher_nonfiction}");
    }

    /**
     * Generates a random publisher name suitable for fiction genres.
     * E.g. {@code Richardson Publishing}
     */
    public String publisherFiction() {
        return dummy.expressionResolver().resolve("#{book.publisher_fiction}");
    }
}
