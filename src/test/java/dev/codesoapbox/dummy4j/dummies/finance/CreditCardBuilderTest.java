package dev.codesoapbox.dummy4j.dummies.finance;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.NumberService;
import dev.codesoapbox.dummy4j.dummies.DateAndTimeDummy;
import dev.codesoapbox.dummy4j.dummies.NameDummy;
import dev.codesoapbox.dummy4j.dummies.address.Address;
import dev.codesoapbox.dummy4j.dummies.address.AddressDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardBuilderTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private NameDummy nameDummy;

    @Mock
    private AddressDummy addressDummy;

    @Mock
    private DateAndTimeDummy dateAndTimeDummy;

    @Mock
    private NumberService numberService;

    @Mock
    private CreditCardNumberBuilder numberBuilder;

    private CreditCardBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new CreditCardBuilder(dummy4j, numberBuilder);
    }

    @Test
    void shouldReturnCreditCardWithOneDigitMonthPadded() {
        Address address = getAddress();
        mockCreditCardData(address, 5);
        when(dummy4j.nextEnum(CreditCardProvider.class))
                .thenReturn(CreditCardProvider.AMERICAN_EXPRESS);
        mockNumber();

        CreditCard actual = builder.build();

        assertAll(
                () -> assertEquals("3412 345678 90127", actual.getNumber()),
                () -> assertEquals(CreditCardProvider.AMERICAN_EXPRESS, actual.getProvider()),
                () -> assertEquals("Zoe Anderson", actual.getOwnerName()),
                () -> assertEquals(address, actual.getOwnerAddress()),
                () -> assertEquals("05/2030", actual.getExpiryDate()),
                () -> assertEquals("111", actual.getSecurityCode()),
                () -> {
                    String expectedString = "CreditCard{number='3412 345678 90127', provider=American Express, " +
                            "ownerName='Zoe Anderson', ownerAddress='street, 123 city, country', " +
                            "expiryDate='05/2030', securityCode='111'}";
                    assertEquals(expectedString, actual.toString());
                }
        );
    }

    private Address getAddress() {
        return new Address("street", "123", "city", "country");
    }

    private void mockCreditCardData(Address address, int month) {
        mockOwnerName();
        mockOwnerAddress(address);
        mockSecurityCode();
        mockExpiryDate(month);
    }

    private void mockOwnerName() {
        when(dummy4j.name())
                .thenReturn(nameDummy);
        when(nameDummy.firstName())
                .thenReturn("Zoe");
        when(nameDummy.lastName())
                .thenReturn("Anderson");
    }

    private void mockOwnerAddress(Address address) {
        when(dummy4j.address())
                .thenReturn(addressDummy);
        when(addressDummy.full())
                .thenReturn(address);
    }

    private void mockExpiryDate(int month) {
        when(dummy4j.dateAndTime())
                .thenReturn(dateAndTimeDummy);
        when(dateAndTimeDummy.future(CreditCardBuilder.MAX_DAYS_FOR_EXPIRY_DATE, ChronoUnit.DAYS))
                .thenReturn(LocalDate.of(2030, month, 15).atStartOfDay());
    }

    private void mockSecurityCode() {
        when(dummy4j.number())
                .thenReturn(numberService);
        when(dummy4j.number())
                .thenReturn(numberService);
        when(numberService.nextInt(CreditCardBuilder.MIN_SECURITY_CODE, CreditCardBuilder.MAX_SECURITY_CODE))
                .thenReturn(111);
    }

    private void mockNumber() {
        when(numberBuilder.withProvider(CreditCardProvider.AMERICAN_EXPRESS)).
                thenReturn(numberBuilder);
        when(numberBuilder.build()).
                thenReturn("3412 345678 90127");
    }

    @Test
    void shouldReturnCreditCardWithTwoDigitMonth() {
        Address address = getAddress();
        mockCreditCardData(address, 10);
        when(dummy4j.nextEnum(CreditCardProvider.class))
                .thenReturn(CreditCardProvider.AMERICAN_EXPRESS);
        mockNumber();

        CreditCard actual = builder.build();

        assertEquals("10/2030", actual.getExpiryDate());
    }

    @Test
    void shouldReturnCreditCardForProvider() {
        Address address = getAddress();
        mockCreditCardData(address, 5);
        when(dummy4j.oneOf(singletonList(CreditCardProvider.AMERICAN_EXPRESS)))
                .thenReturn(CreditCardProvider.AMERICAN_EXPRESS);
        mockNumber();

        CreditCard actual = builder
                .withProvider(CreditCardProvider.AMERICAN_EXPRESS)
                .build();

        assertEquals(CreditCardProvider.AMERICAN_EXPRESS, actual.getProvider());
    }

    @Test
    void shouldReturnCreditCardForRandomProvider() {
        Address address = getAddress();
        mockCreditCardData(address, 5);
        when(dummy4j.oneOf(emptyList()))
                .thenReturn(null);
        when(dummy4j.nextEnum(CreditCardProvider.class))
                .thenReturn(CreditCardProvider.VISA);
        when(numberBuilder.withProvider(CreditCardProvider.VISA)).
                thenReturn(numberBuilder);
        when(numberBuilder.build()).
                thenReturn("3412 345678 90127");

        CreditCard actual = builder
                .withRandomProvider()
                .build();

        assertEquals(CreditCardProvider.VISA, actual.getProvider());
    }

    @Test
    void shouldReturnCreditCardForProviderChosenFromProvidedArguments() {
        Address address = getAddress();
        mockCreditCardData(address, 5);
        when(dummy4j.oneOf(asList(CreditCardProvider.AMERICAN_EXPRESS, CreditCardProvider.VISA)))
                .thenReturn(CreditCardProvider.VISA);
        when(numberBuilder.withProvider(CreditCardProvider.VISA)).
                thenReturn(numberBuilder);
        when(numberBuilder.build()).
                thenReturn("3412 345678 90127");

        CreditCard actual = builder
                .withRandomProvider(CreditCardProvider.AMERICAN_EXPRESS, CreditCardProvider.VISA)
                .build();

        assertEquals(CreditCardProvider.VISA, actual.getProvider());
    }

    @Test
    void shouldReturnCreditCardNumberWithoutFormatting() {
        Address address = getAddress();
        mockCreditCardData(address, 5);
        when(dummy4j.nextEnum(CreditCardProvider.class))
                .thenReturn(CreditCardProvider.AMERICAN_EXPRESS);
        when(numberBuilder.withProvider(CreditCardProvider.AMERICAN_EXPRESS)).
                thenReturn(numberBuilder);
        when(numberBuilder.build()).
                thenReturn("341234567890127");

        CreditCard actual = builder
                .withoutNumberFormatting()
                .build();

        assertEquals("341234567890127", actual.getNumber());
    }

    @Test
    void shouldConvertToString() {
        assertEquals("CreditCardBuilder{numberBuilder=numberBuilder, providers=[]}", builder.toString());
    }
}