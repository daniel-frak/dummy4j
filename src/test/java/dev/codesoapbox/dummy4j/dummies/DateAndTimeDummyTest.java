package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateAndTimeDummyTest {

    private final static LocalDate LOCAL_DATE = LocalDate.of(2000, Month.JANUARY, 1);

    private final Clock clock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.of("GMT")).toInstant(), ZoneId.of("GMT"));

    private DateAndTimeDummy dateAndTimeDummy;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NumberService numberService;

    @BeforeEach
    void setUp() {
        dateAndTimeDummy = new DateAndTimeDummy(dummy4j, clock);
    }

    @Test
    void shouldReturnAnyDateAndTime() {
        LocalDateTime expected = LocalDateTime.parse("1993-03-17T00:00:00");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong())
                .thenReturn(732326400L);

        assertEquals(expected, dateAndTimeDummy.any());
    }

    @Test
    void shouldReturnBirthdayForDefaultAges() {
        LocalDate expected = LocalDate.parse("1943-01-20");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 23010))
                .thenReturn(8784L);

        assertEquals(expected, dateAndTimeDummy.birthday());
    }

    @Test
    void shouldReturnBirthdayForAge() {
        LocalDate expected = LocalDate.parse("1975-01-01");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 364))
                .thenReturn(364L);

        assertEquals(expected, dateAndTimeDummy.birthday(25));
    }

    @Test
    void shouldThrowExceptionWhenAskedForBirthdayForNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.birthday(-25));
    }

    @Test
    void shouldReturnBirthdayIfAgeInYearsIsZero() {
        LocalDate expected = LocalDate.parse("1999-01-03");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 364))
                .thenReturn(1L);

        assertEquals(expected, dateAndTimeDummy.birthday(0));
    }

    @Test
    void shouldReturnBirthdayBetweenAges() {
        LocalDate expected = LocalDate.parse("1980-01-01");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 4016))
                .thenReturn(4016L);

        assertEquals(expected, dateAndTimeDummy.birthday(20, 30));
    }

    @ParameterizedTest
    @CsvSource({
            "1979-01-02,0",
            "1980-01-01,364"
    })
    void shouldReturnBirthdayBetweenSameAges(String expectedDate, long random) {
        LocalDate expected = LocalDate.parse(expectedDate);
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 364))
                .thenReturn(random);

        assertEquals(expected, dateAndTimeDummy.birthday(20, 20));
    }

    @ParameterizedTest
    @CsvSource({
            "-10,2",
            "1,-2",
            "-10,-2"
    })
    void shouldThrowExceptionIfAnyAgeFromRageIsNegative(int minAge, int maxAge) {
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.birthday(minAge, maxAge));
    }

    @Test
    void shouldThrowExceptionWhenMinAgeIsGreaterThanMaxAge() {
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.birthday(30, 20));
    }

    @Test
    void shouldReturnDateBetween() {
        LocalDate startDate = LocalDate.parse("1980-01-01");
        LocalDate endDate = LocalDate.parse("1980-02-01");
        LocalDate expected = LocalDate.parse("1980-01-21");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 31))
                .thenReturn(20L);

        assertEquals(expected, dateAndTimeDummy.between(startDate, endDate));
    }

    @Test
    void shouldThrowExceptionWhenEndDatePrecedesStartDate() {
        LocalDate startDate = LocalDate.parse("2020-01-01");
        LocalDate endDate = LocalDate.parse("1980-02-01");

        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.between(startDate, endDate));
    }

    @Test
    void shouldReturnDateAndTimeBetween() {
        LocalDateTime startDate = LocalDateTime.parse("1980-01-01T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("1980-02-01T00:00:00");
        LocalDateTime expected = LocalDateTime.parse("1980-01-06T05:38:41");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 2678400))
                .thenReturn(452321L);

        assertEquals(expected, dateAndTimeDummy.between(startDate, endDate));
    }

    @Test
    void shouldThrowExceptionWhenEndDateTimePrecedesStartDateTime() {
        LocalDateTime startDateTime = LocalDateTime.parse("2000-12-27T00:00");
        LocalDateTime endDateTime = LocalDateTime.parse("1999-12-27T00:00");

        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.between(startDateTime, endDateTime));
    }

    @Test
    void shouldReturnDateAndTimeInThePast() {
        LocalDateTime expected = LocalDateTime.parse("1999-12-27T00:00");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.past(10, ChronoUnit.DAYS));
    }

    @Test
    void pastShouldReturnNowWhenAtMostIsZero() {
        LocalDateTime expected = LocalDateTime.parse("2000-01-01T00:00");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 0))
                .thenReturn(0L);

        assertEquals(expected, dateAndTimeDummy.past(0, ChronoUnit.DAYS));
    }

    @Test
    void pastShouldThrowExceptionWhenAtMostIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.past(-10, ChronoUnit.DAYS));
    }

    @Test
    void shouldReturnDateAndTimeBeforeReferenceDate() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T06:11:24");
        LocalDateTime expected = LocalDateTime.parse("1998-05-12T06:11:24");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.before(referenceDate, 10, ChronoUnit.DAYS));
    }

    @Test
    void beforeShouldReturnReferenceDateWhenAtMostIsZero() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T00:00");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 0))
                .thenReturn(0L);

        assertEquals(referenceDate, dateAndTimeDummy.before(referenceDate, 0, ChronoUnit.DAYS));
    }

    @Test
    void beforeShouldThrowExceptionWhenAtMostIsNegative() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T00:00");
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.before(referenceDate, -10,
                ChronoUnit.DAYS));
    }

    @Test
    void shouldReturnDateAndTimeInTheFuture() {
        LocalDateTime expected = LocalDateTime.parse("2000-01-06T00:00");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.future(10, ChronoUnit.DAYS));
    }

    @Test
    void futureShouldReturnNowWhenAtMostIsZero() {
        LocalDateTime expected = LocalDateTime.parse("2000-01-01T00:00");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 0))
                .thenReturn(0L);

        assertEquals(expected, dateAndTimeDummy.future(0, ChronoUnit.DAYS));
    }

    @Test
    void futureShouldThrowExceptionWhenAtMostIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.future(-10, ChronoUnit.DAYS));
    }

    @Test
    void shouldReturnDateAndTimeAfterReferenceDate() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T06:11:24");
        LocalDateTime expected = LocalDateTime.parse("1998-05-22T06:11:24");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.after(referenceDate, 10, ChronoUnit.DAYS));
    }

    @Test
    void afterShouldReturnReferenceDateWhenAtMostIsZero() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T00:00");
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextLong(0, 0))
                .thenReturn(0L);

        assertEquals(referenceDate, dateAndTimeDummy.after(referenceDate, 0, ChronoUnit.DAYS));
    }

    @Test
    void afterShouldThrowExceptionWhenAtMostIsNegative() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T00:00");
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeDummy.after(referenceDate,-10,
                ChronoUnit.DAYS));
    }
}