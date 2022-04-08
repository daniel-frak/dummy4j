package dev.codesoapbox.dummy4j.dummies.business;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    private BusinessDummy businessDummy;

    @BeforeEach
    void setUp() {
        businessDummy = new BusinessDummy(dummy4j);
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void shouldReturnRandomProductName() {
        when(expressionResolver.resolve(BusinessDummy.PRODUCT_NAME_KEY))
                .thenReturn("Silk Socks");

        String actual = businessDummy.productName();

        assertEquals("Silk Socks", actual);
    }

    @Test
    void shouldReturnRandomProductTextileName() {
        when(expressionResolver.resolve(BusinessDummy.PRODUCT_NAME_TEXTILE_KEY))
                .thenReturn("Silk Socks");

        String actual = businessDummy.productNameTextile();

        assertEquals("Silk Socks", actual);
    }

    @Test
    void shouldReturnRandomProductNameTool() {
        when(expressionResolver.resolve(BusinessDummy.PRODUCT_NAME_TOOL_KEY))
                .thenReturn("Iron Mallet");

        String actual = businessDummy.productNameTool();

        assertEquals("Iron Mallet", actual);
    }

    @Test
    void shouldReturnRandomProductNameFurniture() {
        when(expressionResolver.resolve(BusinessDummy.PRODUCT_NAME_FURNITURE_KEY))
                .thenReturn("Plastic Bench");

        String actual = businessDummy.productNameFurniture();

        assertEquals("Plastic Bench", actual);
    }

    @Test
    void shouldReturnRandomProductNameTech() {
        when(expressionResolver.resolve(BusinessDummy.PRODUCT_NAME_TECH_KEY))
                .thenReturn("Intelligent Lamp");

        String actual = businessDummy.productNameTech();

        assertEquals("Intelligent Lamp", actual);
    }

    @Test
    void shouldReturnRandomDepartmentName() {
        when(expressionResolver.resolve(BusinessDummy.DEPARTMENT_NAME_KEY))
                .thenReturn("Toys");

        String actual = businessDummy.departmentName();

        assertEquals("Toys", actual);
    }

    @Test
    void shouldReturnRandomCompanyName() {
        when(expressionResolver.resolve(BusinessDummy.COMPANY_NAME_KEY))
                .thenReturn("ACME Inc.");

        String actual = businessDummy.companyName();

        assertEquals("ACME Inc.", actual);
    }

    @Test
    void shouldReturnRandomCompanyType() {
        when(expressionResolver.resolve(BusinessDummy.COMPANY_TYPE_KEY))
                .thenReturn("Self-Employed");

        String actual = businessDummy.companyType();

        assertEquals("Self-Employed", actual);
    }
}