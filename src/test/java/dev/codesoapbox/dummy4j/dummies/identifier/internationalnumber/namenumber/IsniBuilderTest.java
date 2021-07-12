package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber;

import dev.codesoapbox.dummy4j.exceptions.IvalidIsniParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsniBuilderTest {

    private IsniBuilder builder;

    @Mock
    private BasicIsniProvider basicIsniProvider;

    @BeforeEach
    void setUp() {
        builder = new IsniBuilder(basicIsniProvider);
    }

    @Test
    void shouldBuildIsni() {
        mockBasicIsni();

        String actual = builder.build();

        assertEquals("1234567890123451", actual);
    }

    private void mockBasicIsni() {
        when(basicIsniProvider.provide())
                .thenReturn("1234567890123451");
    }

    @Test
    void shouldBuildIsniAsUrl() {
        mockBasicIsni();

        String actual = builder
                .asUrl()
                .build();

        assertEquals("https://isni.org/isni/1234567890123451", actual);
    }

    @Test
    void shouldBuildIsniAsNumber() {
        mockBasicIsni();

        String actual = builder
                .asNumber()
                .build();

        assertEquals("1234567890123451", actual);
    }

    @Test
    void shouldBuildFormattedIsni() {
        mockBasicIsni();

        String actual = builder
                .withSeparator()
                .build();

        assertEquals("1234 5678 9012 3451", actual);
    }

    @Test
    void shouldBuildIsniWithoutFormatting() {
        mockBasicIsni();

        String actual = builder
                .withoutSeparator()
                .build();

        assertEquals("1234567890123451", actual);
    }

    @Test
    void shouldThrowExceptionWhenFormattingIsniInUrl() {
        builder.asUrl();

        assertThrows(IvalidIsniParameterException.class, () -> builder.withSeparator());
    }

    @Test
    void shouldThrowExceptionWhenMakingFormattedIsniAsUrl() {
        builder.withSeparator();

        assertThrows(IvalidIsniParameterException.class, () -> builder.asUrl());
    }
}