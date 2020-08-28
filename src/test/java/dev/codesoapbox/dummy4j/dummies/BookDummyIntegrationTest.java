package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void title() {
        assertEquals("Curse of the Devil", dummy4j.book().title());
    }

    @Test
    void genre() {
        assertEquals("Science fiction", dummy4j.book().genre());
    }

    @Test
    void genreFiction() {
        assertEquals("Science fiction", dummy4j.book().genreFiction());
    }

    @Test
    void genreNonFiction() {
        assertEquals("Biography", dummy4j.book().genreNonFiction());
    }

    @Test
    void publisher() {
        assertEquals("North Zoeshire Research Institute", dummy4j.book().publisher());
    }

    @Test
    void publisherNonFiction() {
        assertEquals("North Zoeshire Research Institute", dummy4j.book().publisherNonFiction());
    }

    @Test
    void publisherFiction() {
        assertEquals("Anderson Press", dummy4j.book().publisherFiction());
    }
}
