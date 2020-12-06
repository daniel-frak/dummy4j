package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IbanBuilderTest {

    @Mock
    FinanceDummy financeDummy;

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private IbanFormula ibanFormula;

    private IbanBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new IbanBuilder(dummy4j, ibanFormula);
    }

    @Test
    void shouldBuildIbanForRandomCountryByDefault() {
        mockFinanceDummy();
        BankAccountCountry country = BankAccountCountry.GERMANY;
        when(dummy4j.nextEnum(BankAccountCountry.class))
                .thenReturn(country);
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");

        String actual = builder.build();

        assertEquals("DE5112345678901", actual);
    }

    private void mockFinanceDummy() {
        when(dummy4j.finance())
                .thenReturn(financeDummy);
    }

    @Test
    void shouldBuildIbanForRandomCountryAsSpecified() {
        mockFinanceDummy();
        BankAccountCountry country = BankAccountCountry.GERMANY;
        when(dummy4j.nextEnum(BankAccountCountry.class))
                .thenReturn(country);
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");

        String actual = builder
                .withRandomCountry()
                .build();

        assertEquals("DE5112345678901", actual);
    }

    private String mockAccountNumber(BankAccountCountry country) {
        String accountNumber = "12345678901";
        when(financeDummy.bankAccountNumber(country))
                .thenReturn(accountNumber);
        return accountNumber;
    }

    @Test
    void shouldBuildIbanForGivenCountry() {
        mockFinanceDummy();
        BankAccountCountry country = BankAccountCountry.GERMANY;
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");
        when(dummy4j.of(singletonList(country)))
                .thenReturn(country);

        String actual = builder
                .withCountry(country)
                .build();

        assertEquals("DE5112345678901", actual);
    }

    @Test
    void shouldBuildIbanForGivenCountries() {
        mockFinanceDummy();
        BankAccountCountry albania = BankAccountCountry.ALBANIA;
        String accountNumber = mockAccountNumber(albania);
        when(ibanFormula.getCheckDigits(accountNumber, albania.getCode()))
                .thenReturn("57");
        when(dummy4j.of(Arrays.asList(albania, BankAccountCountry.MACEDONIA)))
                .thenReturn(albania);

        String actual = builder
                .withRandomCountry(albania, BankAccountCountry.MACEDONIA)
                .build();

        assertEquals("AL5712345678901", actual);
    }

    @Test
    void shouldAllowReusingBuilder() {
        mockFinanceDummy();
        BankAccountCountry germany = BankAccountCountry.GERMANY;
        String accountNumber = mockAccountNumber(germany);
        when(ibanFormula.getCheckDigits(accountNumber, germany.getCode()))
                .thenReturn("51");
        when(dummy4j.of(singletonList(germany)))
                .thenReturn(germany);

        String actual = builder
                .withRandomCountry(BankAccountCountry.ALBANIA, BankAccountCountry.MACEDONIA)
                .withCountry(germany)
                .build();


        assertEquals("DE5112345678901", actual);
    }

    @ParameterizedTest
    @CsvSource({
            "1234567890, AL57 1234 5678 90",
            "1234, AL57 1234",
            "12345, AL57 1234 5",
            "12345678901123456789012, AL57 1234 5678 9011 2345 6789 012",
    })
    void shouldFormatIbanForRandomCountry(String accountNumber, String expected) {
        mockFinanceDummy();
        BankAccountCountry country = BankAccountCountry.ALBANIA;
        when(dummy4j.nextEnum(BankAccountCountry.class))
                .thenReturn(country);
        when(financeDummy.bankAccountNumber(country))
                .thenReturn(accountNumber);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("57");

        String actual = builder
                .formatted()
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldBuildFormattedIbanForGivenCountry() {
        mockFinanceDummy();
        BankAccountCountry country = BankAccountCountry.GERMANY;
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");
        when(dummy4j.of(singletonList(country)))
                .thenReturn(country);

        String actual = builder
                .withCountry(country)
                .formatted()
                .build();

        assertEquals("DE51 1234 5678 901", actual);
    }

    @Test
    void shouldConvertBuilderToString() {
        String expected = "IbanBuilder{countries=[], formatted=false}";

        String actual = builder.toString();

        assertEquals(expected, actual);
    }
}