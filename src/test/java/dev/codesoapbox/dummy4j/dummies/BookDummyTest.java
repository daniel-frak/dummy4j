package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDummyTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void title() {
        assertNotNull(dummy4j.book().title());
        assertFalse(dummy4j.book().title().isEmpty());
    }

    @Test
    void genre() {
        assertNotNull(dummy4j.book().genre());
        assertFalse(dummy4j.book().genre().isEmpty());
    }

    @Test
    void genreFiction() {
        assertNotNull(dummy4j.book().genreFiction());
        assertFalse(dummy4j.book().genreFiction().isEmpty());
    }

    @Test
    void genreNonFiction() {
        assertNotNull(dummy4j.book().genreNonFiction());
        assertFalse(dummy4j.book().genreNonFiction().isEmpty());
    }

    @Test
    void publisher() {
        assertNotNull(dummy4j.book().publisher());
        assertFalse(dummy4j.book().publisher().isEmpty());
    }

    @Test
    void publisherNonFiction() {
        assertNotNull(dummy4j.book().publisherNonFiction());
        assertFalse(dummy4j.book().publisherNonFiction().isEmpty());
    }

    @Test
    void publisherFiction() {
        assertNotNull(dummy4j.book().publisherFiction());
        assertFalse(dummy4j.book().publisherFiction().isEmpty());
    }
}