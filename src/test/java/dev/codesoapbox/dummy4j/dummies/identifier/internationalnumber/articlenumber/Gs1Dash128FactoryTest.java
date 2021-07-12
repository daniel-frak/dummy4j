package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.definitions.UniqueValues;
import dev.codesoapbox.dummy4j.dummies.DateAndTimeDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Gs1Dash128FactoryTest {

    private static final LocalDate LOCAL_DATE = LocalDate.of(2000, Month.JANUARY, 1);
    private static final String DEFAULT_DATE = "000101";

    private final Clock clock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.of("GMT")).toInstant(), ZoneId.of("GMT"));

    private Gs1Dash128Factory factory;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private Gtin14Builder gtin14Builder;

    @Mock
    private SsccFactory ssccFactory;

    @Mock
    private UniqueValues uniqueValues;

    @Mock
    private NumberService numberService;

    @Mock
    private DateAndTimeDummy dateAndTimeDummy;

    @BeforeEach
    void setUp() {
        factory = new Gs1Dash128Factory(dummy4j, gtin14Builder, ssccFactory);
    }

    @Test
    void shouldCreateGs1Dash128() {
        List<Supplier<String>> suppliers = Arrays.asList(() -> "1", () -> "2");
        when(dummy4j.unique())
                .thenReturn(uniqueValues);
        when(dummy4j.number())
                .thenReturn(numberService);
        when(uniqueValues.of(any(), any()))
                .thenReturn(suppliers);
        when(numberService.nextInt(2, 3))
                .thenReturn(2);

        String actual = factory.createCode();

        assertEquals("12", actual);
    }

    @Test
    void shouldSetGtin14Supplier() {
        when(gtin14Builder.withApplicationIdentifier())
                .thenReturn(gtin14Builder);
        when(gtin14Builder.build())
                .thenReturn("01");

        String actual = factory.suppliers.get(0).get();

        assertEquals("01", actual);
    }

    @Test
    void shouldSetSSCCSupplier() {
        when(ssccFactory.createCode())
                .thenReturn("00");

        String actual = factory.suppliers.get(1).get();

        assertEquals("00", actual);
    }

    @Test
    void shouldSetPastDateSuppliers() {
        when(dummy4j.dateAndTime())
                .thenReturn(dateAndTimeDummy);
        when(dateAndTimeDummy.past(anyLong(), eq(ChronoUnit.MONTHS)))
                .thenReturn(LocalDateTime.now(clock));

        assertEquals("(11)" + DEFAULT_DATE, factory.suppliers.get(2).get());
        assertEquals("(13)" + DEFAULT_DATE, factory.suppliers.get(3).get());
    }

    @Test
    void shouldSetFutureDateSuppliers() {
        when(dummy4j.dateAndTime())
                .thenReturn(dateAndTimeDummy);
        when(dateAndTimeDummy.future(anyLong(), eq(ChronoUnit.MONTHS)))
                .thenReturn(LocalDateTime.now(clock));

        assertEquals("(12)" + DEFAULT_DATE, factory.suppliers.get(4).get());
        assertEquals("(15)" + DEFAULT_DATE, factory.suppliers.get(5).get());
        assertEquals("(17)" + DEFAULT_DATE, factory.suppliers.get(6).get());
    }

    @Test
    void shouldSetDigitSuppliers() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(1, 99))
                .thenReturn(1);

        assertEquals("(20)01", factory.suppliers.get(7).get());
    }

    @Test
    void shouldSetCounterSupplier() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(1, 99_999_999))
                .thenReturn(1);

        assertEquals("(30)1<FNC1>", factory.suppliers.get(8).get());
    }

    @Test
    void shouldSetPricePerUnitSupplier() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(anyInt(), anyInt()))
                .thenReturn(1);

        assertEquals("(8005)1<FNC1>", factory.suppliers.get(9).get());
    }
}