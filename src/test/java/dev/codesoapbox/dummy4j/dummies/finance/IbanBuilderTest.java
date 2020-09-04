package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock
    private NumberService numberService;

    private IbanBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new IbanBuilder(dummy4j, ibanFormula);
        mockFinanceDummy();
    }

    private void mockFinanceDummy() {
        when(dummy4j.finance())
                .thenReturn(financeDummy);
    }

    @Test
    void shouldBuildIbanForRandomCountryByDefault() {
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        when(dummy4j.nextEnum(CountrySupportingBankAccount.class))
                .thenReturn(country);
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");

        String actual = builder.build();

        assertEquals("DE5112345678901", actual);
    }

    @Test
    void shouldBuildIbanForRandomCountryAsSpecified() {
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        when(dummy4j.nextEnum(CountrySupportingBankAccount.class))
                .thenReturn(country);
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");

        String actual = builder
                .withRandomCountry()
                .build();

        assertEquals("DE5112345678901", actual);
    }

    private String mockAccountNumber(CountrySupportingBankAccount country) {
        String accountNumber = "12345678901";
        when(financeDummy.bankAccountNumber(country))
                .thenReturn(accountNumber);
        return accountNumber;
    }

    @Test
    void shouldBuildIbanForGivenCountry() {
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");

        String actual = builder
                .withCountry(country)
                .build();

        assertEquals("DE5112345678901", actual);
    }

    @Test
    void shouldBuildIbanForGivenCountries() {
        mockNumberService();
        when(numberService.nextInt(1))
                .thenReturn(0);
        CountrySupportingBankAccount albania = CountrySupportingBankAccount.ALBANIA;
        String accountNumber = mockAccountNumber(albania);
        when(ibanFormula.getCheckDigits(accountNumber, albania.getCode()))
                .thenReturn("57");

        String actual = builder
                .withRandomCountry(albania, CountrySupportingBankAccount.MACEDONIA)
                .build();

        assertEquals("AL5712345678901", actual);
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    @Test
    void shouldAllowReusingBuilder() {
        CountrySupportingBankAccount germany = CountrySupportingBankAccount.GERMANY;
        String accountNumber = mockAccountNumber(germany);
        when(ibanFormula.getCheckDigits(accountNumber, germany.getCode()))
                .thenReturn("51");

        String actual = builder
                .withRandomCountry(CountrySupportingBankAccount.ALBANIA, CountrySupportingBankAccount.MACEDONIA)
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
        CountrySupportingBankAccount country = CountrySupportingBankAccount.ALBANIA;
        when(dummy4j.nextEnum(CountrySupportingBankAccount.class))
                .thenReturn(country);
        when(financeDummy.bankAccountNumber(country))
                .thenReturn(accountNumber);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("57");

        String actual = builder
                .format()
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldBuildFormattedIbanForGivenCountry() {
        CountrySupportingBankAccount country = CountrySupportingBankAccount.GERMANY;
        String accountNumber = mockAccountNumber(country);
        when(ibanFormula.getCheckDigits(accountNumber, country.getCode()))
                .thenReturn("51");

        String actual = builder
                .withCountry(country)
                .format()
                .build();

        assertEquals("DE51 1234 5678 901", actual);
    }
}