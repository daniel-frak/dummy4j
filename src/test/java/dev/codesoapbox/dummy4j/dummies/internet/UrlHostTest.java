package dev.codesoapbox.dummy4j.dummies.internet;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UrlHostTest {

    @Test
    void shouldCreateCompleteHost() {
        UrlHost host = new UrlHost("test", "com", false);
        assertEquals("www.test.com", host.toString());
    }

    @Test
    void shouldRemovePrefix() {
        UrlHost host = new UrlHost("test", "com", true);
        assertEquals("test.com", host.toString());
    }

    @Test
    void shouldConvertToString() {
        UrlHost host = new UrlHost("test", "com", false);
        assertEquals("www.test.com", host.toString());
        UrlHost host2 = new UrlHost("test", "com", true);
        assertEquals("test.com", host2.toString());
    }

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(UrlHost.class)
                .verify();
    }
}