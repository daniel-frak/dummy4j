package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.RandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateAndTimeDummyTest {

    private final static LocalDate LOCAL_DATE = LocalDate.of(2000, Month.JANUARY, 1);

    private final Clock clock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault());

    private DateAndTimeDummy dateAndTimeDummy;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private RandomService randomService;

    @BeforeEach
    void setUp() {
        dateAndTimeDummy = new DateAndTimeDummy(dummy4j, clock);
        when(dummy4j.random())
                .thenReturn(randomService);
    }

    @Test
    void shouldReturnAnyDateAndTime() {
        LocalDateTime expected = LocalDateTime.parse("1993-03-17T00:00:00");
        when(randomService.nextLong())
                .thenReturn(732326400L);

        assertEquals(expected, dateAndTimeDummy.any());
    }

    @Test
    void shouldReturnBirthdayForDefaultAges() {
        LocalDate expected = LocalDate.parse("1943-01-20");

        when(randomService.nextLong(0, 23010))
                .thenReturn(8784L);

        assertEquals(expected, dateAndTimeDummy.birthday());
    }

    @Test
    void shouldReturnBirthdayForAge() {
        LocalDate expected = LocalDate.parse("1975-01-01");
        when(randomService.nextLong(0, 364))
                .thenReturn(364L);

        assertEquals(expected, dateAndTimeDummy.birthday(25));
    }

    @Test
    void shouldReturnBirthdayBetweenAges() {
        LocalDate expected = LocalDate.parse("1980-01-01");
        when(randomService.nextLong(0, 4016))
                .thenReturn(4016L);

        assertEquals(expected, dateAndTimeDummy.birthday(20, 30));
    }

    @Test
    void shouldReturnDateBetween() {
        LocalDate startDate = LocalDate.parse("1980-01-01");
        LocalDate endDate = LocalDate.parse("1980-02-01");
        LocalDate expected = LocalDate.parse("1980-01-21");
        when(randomService.nextLong(0, 31))
                .thenReturn(20L);

        assertEquals(expected, dateAndTimeDummy.between(startDate, endDate));
    }

    @Test
    void shouldReturnDateAndTimeBetween() {
        LocalDateTime startDate = LocalDateTime.parse("1980-01-01T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("1980-02-01T00:00:00");
        LocalDateTime expected = LocalDateTime.parse("1980-01-06T05:38:41");
        when(randomService.nextLong(0, 2678400))
                .thenReturn(452321L);

        assertEquals(expected, dateAndTimeDummy.between(startDate, endDate));
    }

    @Test
    void shouldReturnDateAndTimeInThePast() {
        LocalDateTime expected = LocalDateTime.parse("1999-12-27T00:00");
        when(randomService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.past(10, ChronoUnit.DAYS));
    }

    @Test
    void shouldReturnDateAndTimeInThePastWithReferenceDate() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T06:11:24");
        LocalDateTime expected = LocalDateTime.parse("1998-05-12T06:11:24");
        when(randomService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.past(10, ChronoUnit.DAYS, referenceDate));
    }

    @Test
    void shouldReturnDateAndTimeInTheFuture() {
        LocalDateTime expected = LocalDateTime.parse("2000-01-06T00:00");
        when(randomService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.future(10, ChronoUnit.DAYS));
    }

    @Test
    void shouldReturnDateAndTimeInTheFutureWithReferenceDate() {
        LocalDateTime referenceDate = LocalDateTime.parse("1998-05-17T06:11:24");
        LocalDateTime expected = LocalDateTime.parse("1998-05-22T06:11:24");
        when(randomService.nextLong(0, 10))
                .thenReturn(5L);

        assertEquals(expected, dateAndTimeDummy.future(10, ChronoUnit.DAYS, referenceDate));
    }
}