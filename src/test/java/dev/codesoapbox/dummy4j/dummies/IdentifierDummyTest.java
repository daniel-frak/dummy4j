package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.RandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdentifierDummyTest {

    private IdentifierDummy identifierDummy;

    @Mock
    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        identifierDummy = new IdentifierDummy(dummy4j);
    }

    @Test
    void shouldGetRandomUuid() {
        NumberService numberService = mock(RandomService.class);
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong())
                .thenReturn(1L);
        String expected = "c4ca4238-a0b9-3382-8dcc-509a6f75849b";
        assertEquals(expected, identifierDummy.uuid().toString());
    }
}