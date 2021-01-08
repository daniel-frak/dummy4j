package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.InternationalNumberCheckDigitFormulaProvider;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModElevenFormula;
import dev.codesoapbox.dummy4j.dummies.shared.checkdigitformulas.ModTenFormula;
import dev.codesoapbox.dummy4j.exceptions.InvalidDefinitionException;
import dev.codesoapbox.dummy4j.exceptions.InvalidIsbnParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsbnBuilderTest {

    private static final String PREFIX = "978";
    private static final Integer GROUP = 3;

    private IsbnBuilder isbnBuilder;

    @Mock
    private IsbnValidator isbnValidator;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private InternationalNumberCheckDigitFormulaProvider checkDigitFormulaProvider;

    @Mock
    private ModTenFormula modTenFormula;

    @Mock
    private ModElevenFormula modElevenFormula;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private NumberService numberService;

    @BeforeEach
    void setUp() {
        isbnBuilder = new IsbnBuilder(dummy4j, isbnValidator, checkDigitFormulaProvider);
    }

    @Test
    void shouldBuildIsbnWithGivenPartsAndAddCheckDigit() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModTenFormula("111222333444");

        String actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("111")
                .withRegistrationGroup("222")
                .withRegistrant("333")
                .withPublication("444")
                .withSeparator("-")
                .build()
                .toString();

        assertEquals("111-222-333-444-8", actual);
    }

    private void mockFirstElementFromAnyList() {
        when(dummy4j.of(anyList()))
                .thenAnswer(inv -> ((List<?>) inv.getArgument(0)).get(0));
    }

    private void mockCheckDigitForModTenFormula(String input) {
        when(checkDigitFormulaProvider.getModTenFormula())
                .thenReturn(modTenFormula);
        when(modTenFormula.generateCheckDigit(input))
                .thenReturn(8);
    }

    @Test
    void shouldBuildIsbnWithDrawnPartsAndAddCheckDigit() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModElevenFormula();

        String actual = isbnBuilder
                .withRandomType(IsbnType.ISBN_10)
                .withRandomPrefix("111")
                .withRandomRegistrationGroup("222")
                .withRandomRegistrant("333")
                .withRandomPublication("444")
                .withRandomSeparator("-")
                .build()
                .toString();

        assertEquals("222-333-444-8", actual);
    }

    private void mockCheckDigitForModElevenFormula() {
        when(checkDigitFormulaProvider.getModElevenFormula())
                .thenReturn(modElevenFormula);
        when(modElevenFormula.generateCheckDigit("222333444"))
                .thenReturn(8);
    }

    @Test
    void shouldIgnorePrefixWhenNotRequired() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModElevenFormula();

        String actual = isbnBuilder
                .withType(IsbnType.ISBN_10)
                .withPrefix("111")
                .withRegistrationGroup("222")
                .withRegistrant("333")
                .withPublication("444")
                .withSeparator("-")
                .build()
                .toString();

        assertEquals("222-333-444-8", actual);
    }

    @Test
    void shouldBuildIsbnWithRandomPartsAndAddCheckDigit() {
        mockRandomType();
        mockExpressionResolver();
        mockResolvingPrefix();
        mockSeparator();
        mockNumberService();
        mockCheckDigitForModTenFormula(PREFIX + GROUP + "11");
        when(dummy4j.of(emptyList()))
                .thenReturn(null);
        mockRegistrationGroupRange();
        when(numberService.digits(anyInt()))
                .thenReturn("1");

        Isbn actual = isbnBuilder
                .withRandomType()
                .withRandomPrefix()
                .withRandomRegistrationGroup()
                .withRandomRegistrant()
                .withRandomPublication()
                .withRandomSeparator()
                .build();

        assertNotNull(actual);
        assertAll(
                () -> assertNotNull(actual.getType()),
                () -> assertNotNull(actual.getPrefix()),
                () -> assertNotNull(actual.getRegistrationGroup()),
                () -> assertFalse(actual.getRegistrationGroup().isEmpty()),
                () -> assertNotNull(actual.getRegistrant()),
                () -> assertFalse(actual.getRegistrant().isEmpty()),
                () -> assertNotNull(actual.getPublication()),
                () -> assertFalse(actual.getPublication().isEmpty()),
                () -> assertNotNull(actual.getCheckDigit()),
                () -> assertFalse(actual.getCheckDigit().isEmpty()),
                () -> assertNotNull(actual.getSeparator()),
                () -> assertFalse(actual.getSeparator().isEmpty())
        );
    }

    private void mockRegistrationGroupRange() {
        IsbnRegistrationGroupRange groupRange = IsbnBuilder.REGISTRATION_GROUP_RANGES.get(0);
        when(dummy4j.of(IsbnBuilder.REGISTRATION_GROUP_RANGES))
                .thenReturn(groupRange);
        when(numberService.nextInt(groupRange.getMin(), groupRange.getMax()))
                .thenReturn(GROUP);
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    private void mockSeparator() {
        when(expressionResolver.resolve(IsbnBuilder.ISBN_SEPARATOR_KEY))
                .thenReturn("-");
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    private void mockResolvingPrefix() {
        when(expressionResolver.resolve(IsbnBuilder.ISBN_PREFIX_KEY))
                .thenReturn(PREFIX);
    }

    private void mockRandomType() {
        when(dummy4j.nextEnum(IsbnType.class))
                .thenReturn(IsbnType.ISBN_13);
    }

    @Test
    void shouldBuildIsbnForAllRegistrationGroupMinimumValues() {
        mockFirstElementFromAnyList();
        mockRandomType();
        mockExpressionResolver();
        mockResolvingPrefix();
        mockSeparator();
        mockNullableParts();

        IsbnBuilder.REGISTRATION_GROUP_RANGES
                .forEach(group -> {
                    String min = String.valueOf(group.getMin());
                    mockCheckDigitForModTenFormula(PREFIX + min + "12");
                    Isbn actual = isbnBuilder
                            .withRegistrationGroup(min)
                            .withRegistrant("1")
                            .withPublication("2")
                            .build();
                    assertNotNull(actual);
                    assertEquals(min, actual.getRegistrationGroup());
                });
    }

    private void mockNullableParts() {
        when(dummy4j.of((List<Object>) null))
                .thenReturn(null);
    }

    @Test
    void shouldBuildIsbnForAllRegistrationGroupMaximumValues() {
        mockFirstElementFromAnyList();
        mockRandomType();
        mockExpressionResolver();
        mockResolvingPrefix();
        mockSeparator();
        mockNullableParts();

        IsbnBuilder.REGISTRATION_GROUP_RANGES
                .forEach(group -> {
                    String max = String.valueOf(group.getMax());
                    mockCheckDigitForModTenFormula(PREFIX + max + "12");
                    Isbn actual = isbnBuilder
                            .withRegistrationGroup(max)
                            .withRegistrant("1")
                            .withPublication("2")
                            .build();
                    assertNotNull(actual);
                    assertEquals(max, actual.getRegistrationGroup());
                });
    }

    @Test
    void shouldThrowExceptionOnInvalidPrefixResolved() {
        mockRandomType();
        mockExpressionResolver();
        mockNullableParts();
        when(expressionResolver.resolve(IsbnBuilder.ISBN_PREFIX_KEY))
                .thenReturn("invalid");
        doThrow(InvalidIsbnParameterException.class)
                .when(isbnValidator).testForInvalidPrefix("invalid");

        assertThrows(InvalidDefinitionException.class, () -> isbnBuilder.build());
    }

    @Test
    void shouldGenerateRegistrant() {
        mockFirstElementFromAnyList();
        mockNumberService();
        mockCheckDigitForModTenFormula("000123");
        when(numberService.nextInt(1, 7))
                .thenReturn(7);
        when(numberService.digits(1))
                .thenReturn("2");
        when(numberService.digits(7))
                .thenReturn("3");

        Isbn actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("000")
                .withRegistrationGroup("1")
                .withSeparator("-")
                .build();

        assertEquals("2", actual.getRegistrant());
    }

    @Test
    void shouldGenerateRegistrantBasedOnPublication() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModTenFormula("000122222223");
        mockNumberService();
        when(numberService.digits(7))
                .thenReturn("2222222");

        Isbn actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("000")
                .withRegistrationGroup("1")
                .withPublication("3")
                .withSeparator("-")
                .build();

        assertEquals("2222222", actual.getRegistrant());
    }

    @Test
    void shouldThrowExceptionWhenRegistrationGroupTooLong() {
        doThrow(InvalidIsbnParameterException.class)
                .when(isbnValidator).testForInvalidRegistrationGroup("111111");

        assertThrows(InvalidIsbnParameterException.class,
                () -> isbnBuilder
                        .withRegistrationGroup("111111"));
    }

    @Test
    void shouldNotGenerateRegistrantWhenNoSpaceLeft() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModTenFormula("000111113333");

        String actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("000")
                .withRegistrationGroup("11111")
                .withPublication("3333")
                .withSeparator("-")
                .build()
                .toString();

        assertEquals("000-11111--3333-8", actual);
    }

    @Test
    void shouldNotGenerateRegistrantAndPublicationWhenNoSpaceLeft() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModTenFormula("000111111111");
        mockNumberService();
        when(numberService.digits(0))
                .thenReturn("");

        String actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("000")
                .withRegistrationGroup("111111111")
                .withSeparator("-")
                .build()
                .toString();

        assertEquals("000-111111111---8", actual);
    }

    @Test
    void shouldNotGeneratePublicationWhenNoSpaceLeft() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModTenFormula("000111112222");
        mockNumberService();
        when(numberService.digits(0))
                .thenReturn("");

        String actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("000")
                .withRegistrationGroup("11111")
                .withRegistrant("2222")
                .withSeparator("-")
                .build()
                .toString();

        assertEquals("000-11111-2222--8", actual);
    }

    @Test
    void shouldReplaceNullSeparatorWithRandomOne() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModTenFormula("111222333444");
        mockExpressionResolver();
        mockSeparator();

        String actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("111")
                .withRegistrationGroup("222")
                .withRegistrant("333")
                .withPublication("444")
                .withRandomSeparator(null, null)
                .build()
                .toString();

        assertEquals("111-222-333-444-8", actual);
    }

    @Test
    void shouldClearFormatting() {
        mockFirstElementFromAnyList();
        mockCheckDigitForModTenFormula("111222333444");

        String actual = isbnBuilder
                .withType(IsbnType.ISBN_13)
                .withPrefix("111")
                .withRegistrationGroup("222")
                .withRegistrant("333")
                .withPublication("444")
                .withoutSeparator()
                .build()
                .toString();

        assertEquals("1112223334448", actual);
    }

    @Test
    void shouldConvertToString() {
        String expected = "IsbnBuilder{isbnType=null, prefixes=null, registrationGroups=null, registrants=null, " +
                "publications=null, separators=null}";

        assertEquals(expected, isbnBuilder.toString());
    }
}