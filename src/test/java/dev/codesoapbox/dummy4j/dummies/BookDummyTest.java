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
        String value = dummy4j.book().title();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void genre() {
        String value = dummy4j.book().genre();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void genreFiction() {
        String value = dummy4j.book().genreFiction();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void genreNonFiction() {
        String value = dummy4j.book().genreNonFiction();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void publisher() {
        String value = dummy4j.book().publisher();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void publisherNonFiction() {
        String value = dummy4j.book().publisherNonFiction();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void publisherFiction() {
        String value = dummy4j.book().publisherFiction();
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}