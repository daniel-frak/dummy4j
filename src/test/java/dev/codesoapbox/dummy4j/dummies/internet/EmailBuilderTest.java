package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.dummies.NameDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailBuilderTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private NameDummy nameDummy;

    private EmailBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new EmailBuilder(dummy4j);
    }

    @Test
    void shouldBuildSafeEmail() {
        mockLocalPart();
        mockExpressionResolver();
        mockSafeDomain();

        String actual = builder
                .safe()
                .build();

        assertEquals("zoe.anderson@example.com", actual);
    }

    private void mockLocalPart() {
        when(dummy4j.name())
                .thenReturn(nameDummy);
        when(nameDummy.firstName())
                .thenReturn("Zoe");
        when(nameDummy.lastName())
                .thenReturn("Anderson");
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    private void mockSafeDomain() {
        when(expressionResolver.resolve(EmailBuilder.SAFE_DOMAIN_KEY))
                .thenReturn("example.com");
    }

    @Test
    void shouldBuildEmailWithProvider() {
        mockLocalPart();

        String actual = builder
                .withDomain("my-provider.com")
                .build();

        assertEquals("zoe.anderson@my-provider.com", actual);
    }

    @Test
    void shouldBuildEmailWithLocalPartAndIgnoreCustomDelimiter() {
        mockDomain();

        String actual = builder
                .withLocalPart("my.local.part")
                .withLocalPartDelimiter("-")
                .build();

        assertEquals("my.local.part@gmail.com", actual);
    }

    private void mockDomain() {
        mockExpressionResolver();
        when(expressionResolver.resolve(EmailBuilder.DOMAIN_KEY))
                .thenReturn("gmail.com");
    }

    @Test
    void shouldBuildEmailWithLocalPartDelimiter() {
        mockLocalPart();
        mockDomain();

        String actual = builder
                .withLocalPartDelimiter("-")
                .build();

        assertEquals("zoe-anderson@gmail.com", actual);
    }

    @Test
    void shouldBuildEmailWithLocalPartAndProvider() {
        String actual = builder
                .withLocalPart("my.local.part")
                .withDomain("my-provider.com")
                .build();

        assertEquals("my.local.part@my-provider.com", actual);
    }

    @Test
    void shouldBuildEmailWithLocalPartAndSafeDomain() {
        mockExpressionResolver();
        mockSafeDomain();

        String actual = builder
                .withLocalPart("my.local.part")
                .safe()
                .build();

        assertEquals("my.local.part@example.com", actual);
    }

    @Test
    void shouldBuildEmailWithRandomSubAddress() {
        mockLocalPart();
        mockDomain();
        when(expressionResolver.resolve(EmailBuilder.SUB_ADDRESS_KEY))
                .thenReturn("tag");

        String actual = builder
                .withRandomSubAddress()
                .build();

        assertEquals("zoe.anderson+tag@gmail.com", actual);
    }

    @Test
    void shouldBuildEmailWithManySubAddresses() {
        mockLocalPart();
        mockDomain();

        String actual = builder
                .withSubAddresses("tag1", "tag2", "tag3")
                .build();

        assertEquals("zoe.anderson+tag1+tag2+tag3@gmail.com", actual);
    }

    @Test
    void shouldBuildEmailWithSingleSubAddress() {
        mockLocalPart();
        mockDomain();

        String actual = builder
                .withSubAddresses("custom-tag")
                .build();

        assertEquals("zoe.anderson+custom-tag@gmail.com", actual);
    }

    @Test
    void shouldBuildEmailWithoutSubAddress() {
        mockLocalPart();
        mockDomain();

        String actual = builder
                .withSubAddresses()
                .build();

        assertEquals("zoe.anderson@gmail.com", actual);
    }

    @Test
    void shouldOverrideSubAddressesWithNewValues() {
        mockLocalPart();
        mockDomain();

        builder
                .withSubAddresses("tag1", "tag2")
                .build();
        String actual = builder
                .withSubAddresses("tag3")
                .build();

        assertEquals("zoe.anderson+tag3@gmail.com", actual);
    }

    @Test
    void shouldOverrideSubAddressesWithRandomValue() {
        mockLocalPart();
        mockDomain();
        when(expressionResolver.resolve(EmailBuilder.SUB_ADDRESS_KEY))
                .thenReturn("random");

        String actual = builder
                .withSubAddresses("tag1", "tag2")
                .withRandomSubAddress()
                .build();

        assertEquals("zoe.anderson+random@gmail.com", actual);
    }

    @Test
    void shouldOverrideRandomValueWithSubAddresses() {
        mockLocalPart();
        mockDomain();

        String actual = builder
                .withRandomSubAddress()
                .withSubAddresses("tag1", "tag2")
                .build();

        assertEquals("zoe.anderson+tag1+tag2@gmail.com", actual);
    }

    @Test
    void shouldOverrideLocalPartWithNewValue() {
        mockDomain();

        builder
                .withLocalPart("custom1")
                .build();
        String actual = builder
                .withLocalPart("custom2")
                .build();

        assertEquals("custom2@gmail.com", actual);
    }

    @Test
    void shouldOverrideDefaultLocalPartWithNewCustomValue() {
        mockLocalPart();
        mockDomain();

        builder.build();
        String actual = builder
                .withLocalPart("custom")
                .build();

        assertEquals("custom@gmail.com", actual);
    }

    @Test
    void shouldNotOverrideCustomLocalPartWithDefaultValue() {
        mockDomain();
        builder
                .withLocalPart("custom")
                .build();

        String actual = builder.build();

        assertEquals("custom@gmail.com", actual);
        verify(nameDummy, times(0)).firstName();
        verify(nameDummy, times(0)).lastName();
    }

    @Test
    void shouldNotOverrideCustomSubAddressWithDefaultValue() {
        mockLocalPart();
        mockDomain();
        builder
                .withSubAddresses("tag")
                .build();

        String actual = builder.build();

        assertEquals("zoe.anderson+tag@gmail.com", actual);
    }

    @Test
    void shouldOverrideLocalPartByAddingSubAddress() {
        mockDomain();

        builder
                .withLocalPart("custom")
                .build();
        String actual = builder
                .withSubAddresses("tag")
                .build();

        assertEquals("custom+tag@gmail.com", actual);
    }

    @Test
    void shouldOverrideLocalPartAndRemoveSubAddress() {
        mockLocalPart();
        mockDomain();

        builder
                .withSubAddresses("tag")
                .build();
        String actual = builder
                .withLocalPart("custom")
                .withSubAddresses()
                .build();

        assertEquals("custom@gmail.com", actual);
    }

    @Test
    void shouldOverrideLocalPartAndKeepSubAddress() {
        mockDomain();

        builder
                .withLocalPart("custom1")
                .withSubAddresses("tag")
                .build();
        String actual = builder
                .withLocalPart("custom2")
                .build();

        assertEquals("custom2+tag@gmail.com", actual);
    }

    @Test
    void shouldGenerateRandomDomainAndLocalPartOnEveryBuild() {
        mockLocalPart();
        mockDomain();

        builder.build();
        builder.build();
        builder.build();

        verify(expressionResolver, times(3)).resolve(EmailBuilder.DOMAIN_KEY);
        verify(nameDummy, times(3)).firstName();
        verify(nameDummy, times(3)).lastName();
    }

    @Test
    void shouldGenerateRandomSubDomainOnEveryBuild() {
        mockLocalPart();
        mockDomain();
        when(expressionResolver.resolve(EmailBuilder.SUB_ADDRESS_KEY))
                .thenReturn("tag");

        builder.withRandomSubAddress().build();
        builder.withRandomSubAddress().build();
        builder.withRandomSubAddress().build();

        verify(expressionResolver, times(3)).resolve(EmailBuilder.SUB_ADDRESS_KEY);
    }

    @Test
    void shouldSkipSanitizingLocalPart() {
        mockDomain();

        String actual = builder
                .withLocalPart("local párt")
                .withSubAddresses("sub address")
                .notSanitized()
                .build();

        assertEquals("local párt+sub address@gmail.com", actual);
    }

    @Test
    void shouldSanitize() {
        mockDomain();

        String actual = builder
                .withLocalPart("ločál pártÆ\\\"")
                .withSubAddresses("sub áddress")
                .build();

        assertEquals("localpart+subaddress@gmail.com", actual);
    }

    @Test
    void shouldConvertBuilderToString() {
        String expected = "EmailBuilder{customDomain='null', customLocalPart='null', customSubAddresses=[], " +
                "randomizeSubAddress=false, localPartDelimiter='.', sanitize=true}";
        assertEquals(expected, builder.toString());
    }
}