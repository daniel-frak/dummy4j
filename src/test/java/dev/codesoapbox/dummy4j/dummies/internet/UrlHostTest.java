package dev.codesoapbox.dummy4j.dummies.internet;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UrlHostTest {

    @Test
    void shouldCreateCompleteHost() {
        UrlHost host = new UrlHost("test", "com", false);
        assertEquals("www.test.com", host.toString());
    }

    @ParameterizedTest
    @CsvSource(emptyValue = "", value = {
            "test, ",
            " , com",
            "test, ''",
            "'', com"
    }
    )
    void shouldNotCreateInvalidHost(String rootDomain, String topLevelDomain) {
        assertThrows(IllegalArgumentException.class, () -> new UrlHost(rootDomain, topLevelDomain, false));
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