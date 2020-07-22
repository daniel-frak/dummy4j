package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.RandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NatoPhoneticAlphabetDummyTest {

    private NatoPhoneticAlphabetDummy natoPhoneticAlphabetDummy;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private RandomService randomService;

    @BeforeEach
    void setUp() {
        natoPhoneticAlphabetDummy = new NatoPhoneticAlphabetDummy(dummy4j);
    }

    @Test
    void shouldReturnRandomWord() {
        when(dummy4j.random())
                .thenReturn(randomService);
        when(randomService.nextInt(35))
                .thenReturn(4);

        assertEquals("Echo", natoPhoneticAlphabetDummy.word());
    }

    @Test
    void shouldConvertText() {
        assertAll(
                () -> assertEquals("Oscar Kilo", natoPhoneticAlphabetDummy.of("ok")),
                () -> assertEquals("Oscar Kilo One Three", natoPhoneticAlphabetDummy.of("Ok13")),
                () -> assertEquals("Bravo Romeo Alpha Victor Oscar", natoPhoneticAlphabetDummy.of("Bravo"))
        );
    }
}