package dev.codesoapbox.dummy4j.dummies.business;

import dev.codesoapbox.dummy4j.Dummy4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BusinessDummyIntegrationTest {

    private Dummy4j dummy4j;

    @BeforeEach
    void setUp() {
        dummy4j = new Dummy4j();
    }

    @Test
    void shouldReturnProductName() {
        String actual = dummy4j.business().productName();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnProductNameTextile() {
        String actual = dummy4j.business().productNameTextile();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnProductNameTool() {
        String actual = dummy4j.business().productNameTool();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnProductNameFurniture() {
        String actual = dummy4j.business().productNameFurniture();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnProductNameTech() {
        String actual = dummy4j.business().productNameTech();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnDepartmentName() {
        String actual = dummy4j.business().departmentName();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnCompanyName() {
        String actual = dummy4j.business().companyName();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnCompanyType() {
        String actual = dummy4j.business().companyType();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }
}
