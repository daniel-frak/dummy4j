package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.namenumber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrcidBuilderTest {

    private OrcidBuilder builder;

    @Mock
    private BasicIsniProvider basicIsniProvider;

    @BeforeEach
    void setUp() {
        builder = new OrcidBuilder(basicIsniProvider);
        mockBasicIsni();
    }

    private void mockBasicIsni() {
        when(basicIsniProvider.provide())
                .thenReturn("1234567890123451");
    }

    @Test
    void shouldBuildOrcid() {
        String actual = builder.build();

        assertEquals("1234-5678-9012-3451", actual);
    }

    @Test
    void shouldBuildOrcidAsUrl() {
        String actual = builder
                .asUrl()
                .build();

        assertEquals("https://orcid.org/1234-5678-9012-3451", actual);
    }

    @Test
    void shouldBuildOrcidAsNumber() {
        String actual = builder
                .asNumber()
                .build();

        assertEquals("1234-5678-9012-3451", actual);
    }
}