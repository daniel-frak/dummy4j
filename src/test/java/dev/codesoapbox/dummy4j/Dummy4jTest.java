package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.UniqueValues;
import dev.codesoapbox.dummy4j.dummies.*;
import dev.codesoapbox.dummy4j.dummies.color.ColorDummy;
import dev.codesoapbox.dummy4j.dummies.internet.InternetDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Dummy4jTest {

    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private RandomService randomService;

    @Mock
    private Dummies dummies;

    @Mock
    private UniqueValues uniqueValues;

    @Mock
    private ConvenienceMethods convenienceMethods;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j(expressionResolver, randomService, d -> dummies, uniqueValues, convenienceMethods);
    }

    @Test
    void shouldGetExpressionResolver() {
        assertEquals(expressionResolver, dummy4j.expressionResolver());
    }

    @Test
    void shouldGetUnique() {
        assertEquals(uniqueValues, dummy4j.unique());
    }

    @Test
    void shouldGetNumberService() {
        assertEquals(randomService, dummy4j.number());
    }

    @Test
    void shouldReturnRandomBoolean() {
        when(randomService.nextBoolean())
                .thenReturn(true)
                .thenReturn(false);

        assertAll(
                () -> assertTrue(dummy4j.nextBoolean()),
                () -> assertFalse(dummy4j.nextBoolean())
        );
    }

    @Test
    void shouldGenerateRandomList() {
        List<String> expected = Arrays.asList("1", "2");
        Supplier<String> supplier = () -> "test";
        when(convenienceMethods.listOf(4, supplier))
                .thenReturn(expected);

        assertEquals(expected, dummy4j.listOf(4, supplier));
    }

    @Test
    void shouldGetNameDummy() {
        NameDummy nameDummy = mock(NameDummy.class);
        when(dummies.name())
                .thenReturn(nameDummy);
        assertEquals(nameDummy, dummy4j.name());
    }

    @Test
    void shouldGenerateRandomSet() {
        Set<String> expected = new HashSet<>();
        expected.add("1");
        expected.add("2");
        Supplier<String> supplier = () -> "test";
        when(convenienceMethods.setOf(4, supplier))
                .thenReturn(expected);

        assertEquals(expected, dummy4j.setOf(4, supplier));
    }

    @Test
    void shouldReturnRandomElementFromArray() {
        assertEquals("1", new Dummy4j().of("1"));
    }

    @Test
    void shouldReturnRandomElementFromList() {
        List<String> list = Arrays.asList("1", "2");
        String expected = "test";
        when(convenienceMethods.of(list))
                .thenReturn(expected);

        assertEquals(expected, dummy4j.of(list));
    }

    @Test
    void shouldReturnRandomElementFromSet() {
        Set<String> set = new HashSet<>(Arrays.asList("1", "2"));
        String expected = "test";
        when(convenienceMethods.of(set))
                .thenReturn(expected);

        assertEquals(expected, dummy4j.of(set));
    }

    @Test
    void shouldSupplyFromRandomSupplier() {
        assertEquals("1", new Dummy4j().of(() -> "1"));
    }

    @Test
    void shouldSupplyRandomlyByChance() {
        Supplier<String> supplier = () -> "1";
        when(convenienceMethods.chance(1, 2, supplier))
                .thenReturn("test");
        assertEquals("test", dummy4j.chance(1, 2, supplier));
    }

    @Test
    void shouldGetRandomEnumValue() {
        ConvenienceMethodsTest.TestEnum expectedEnum = ConvenienceMethodsTest.TestEnum.THREE;
        when(convenienceMethods.nextEnum(ConvenienceMethodsTest.TestEnum.class))
                .thenReturn(expectedEnum);

        assertEquals(expectedEnum, dummy4j.nextEnum(ConvenienceMethodsTest.TestEnum.class));
    }

    @Test
    void shouldGetNationDummy() {
        NationDummy nationDummy = mock(NationDummy.class);
        when(dummies.nation())
                .thenReturn(nationDummy);
        assertEquals(nationDummy, dummy4j.nation());
    }

    @Test
    void shouldGetAddressDummy() {
        AddressDummy addressDummy = mock(AddressDummy.class);
        when(dummies.address())
                .thenReturn(addressDummy);
        assertEquals(addressDummy, dummy4j.address());
    }

    @Test
    void shouldGetLoremDummy() {
        LoremDummy loremDummy = mock(LoremDummy.class);
        when(dummies.lorem())
                .thenReturn(loremDummy);
        assertEquals(loremDummy, dummy4j.lorem());
    }

    @Test
    void shouldGetDateAndTimeDummy() {
        DateAndTimeDummy dateAndTimeDummy = mock(DateAndTimeDummy.class);
        when(dummies.dateAndTime())
                .thenReturn(dateAndTimeDummy);
        assertEquals(dateAndTimeDummy, dummy4j.dateAndTime());
    }

    @Test
    void shouldGetIdentifierDummy() {
        IdentifierDummy identifierDummy = mock(IdentifierDummy.class);
        when(dummies.identifier())
                .thenReturn(identifierDummy);
        assertEquals(identifierDummy, dummy4j.identifier());
    }

    @Test
    void shouldGetEducationDummy() {
        EducationDummy educationDummy = mock(EducationDummy.class);
        when(dummies.education())
                .thenReturn(educationDummy);
        assertEquals(educationDummy, dummy4j.education());
    }

    @Test
    void shouldGetScifiDummy() {
        ScifiDummy scifiDummy = mock(ScifiDummy.class);
        when(dummies.scifi())
                .thenReturn(scifiDummy);
        assertEquals(scifiDummy, dummy4j.scifi());
    }

    @Test
    void shouldGetColorDummy() {
        ColorDummy colorDummy = mock(ColorDummy.class);
        when(dummies.color())
                .thenReturn(colorDummy);
        assertEquals(colorDummy, dummy4j.color());
    }

    @Test
    void shouldGetNumeralsDummy() {
        NumeralsDummy numeralsDummy = mock(NumeralsDummy.class);
        when(dummies.numerals())
                .thenReturn(numeralsDummy);
        assertEquals(numeralsDummy, dummy4j.numerals());
    }

    @Test
    void shouldGetMedicalDummy() {
        MedicalDummy medicalDummy = mock(MedicalDummy.class);
        when(dummies.medical())
                .thenReturn(medicalDummy);
        assertEquals(medicalDummy, dummy4j.medical());
    }

    @Test
    void shouldGetNatoPhoneticAlphabetDummy() {
        NatoPhoneticAlphabetDummy natoPhoneticAlphabetDummy = mock(NatoPhoneticAlphabetDummy.class);
        when(dummies.natoPhoneticAlphabet())
                .thenReturn(natoPhoneticAlphabetDummy);
        assertEquals(natoPhoneticAlphabetDummy, dummy4j.natoPhoneticAlphabet());
    }
    
    @Test
    void shouldGetInternetDummy() {
        InternetDummy internetDummy = mock(InternetDummy.class);
        when(dummies.internet())
                .thenReturn(internetDummy);
        assertEquals(internetDummy, dummy4j.internet());
    }
}