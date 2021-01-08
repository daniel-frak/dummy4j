package dev.codesoapbox.dummy4j.dummies.shared.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringSanitizerTest {

    @Test
    void shouldSanitizeForEmail() {
        assertAll(
                () -> {
                    String expected = "orcpzsiayd";
                    String input = "or čp    žs\níáýÆd\\\"";
                    assertEquals(expected, StringSanitizer.sanitizeForEmail(input),
                            "Combined string not replaced");
                },
                () -> {
                    String expected = "AAAACCEEEEIINNUUOOOSZZ";
                    String input = "ĄÀÂÄÇĆÉÈÊËÎÏŃÑÛÜÔÖÓŠŻŹ";
                    assertEquals(expected, StringSanitizer.sanitizeForEmail(input), "Upper cases not replaced");
                },
                () -> {
                    String expected = "aaaaacceeeeghiiiiinoosstuuuuyyz";
                    String input = "àâäåáçčéèêëġĥĩĭîïíńôöšŝťůùûüýŷž";
                    assertEquals(expected, StringSanitizer.sanitizeForEmail(input), "Lower cases not replaced");
                },
                () -> {
                    String input = "æłøÆŁØßŒ€£§°©®";
                    assertEquals("", StringSanitizer.sanitizeForEmail(input), "Non ASCII not removed");
                },
                () -> assertEquals("test", StringSanitizer.sanitizeForEmail("t  e s\nt"),
                        "Whitespaces not removed"),
                () -> assertEquals("", StringSanitizer.sanitizeForEmail("\\\""),
                        "Backslashes and quotes not removed"),
                () -> assertEquals("", StringSanitizer.sanitizeForEmail("\""),
                        "Quotes not removed"),
                () -> assertEquals("", StringSanitizer.sanitizeForEmail("¨"),
                        "Should be replaced with a space and removed"),
                () -> assertEquals("TM", StringSanitizer.sanitizeForEmail("™"),
                        "Should be replaced with TM")
        );
    }
}