package dev.codesoapbox.dummy4j.dummies.business;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating values related to business and commerce
 *
 * @since SNAPSHOT
 */
public class BusinessDummy {

    static final String PRODUCT_NAME_KEY = "#{business.product_name}";
    static final String PRODUCT_NAME_TEXTILE_KEY = "#{business.product_name_textile}";
    static final String PRODUCT_NAME_TOOL_KEY = "#{business.product_name_tool}";
    static final String PRODUCT_NAME_FURNITURE_KEY = "#{business.product_name_furniture}";
    static final String PRODUCT_NAME_TECH_KEY = "#{business.product_name_tech}";
    static final String DEPARTMENT_NAME_KEY = "#{business.department_name}";
    static final String COMPANY_NAME_KEY = "#{business.company_name}";
    static final String COMPANY_TYPE_KEY = "#{business.company_type}";

    private final Dummy4j dummy4j;

    public BusinessDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Generates a random product name.
     * E.g {@code Intelligent Light Bulb}
     */
    public String productName() {
        return dummy4j.expressionResolver().resolve(PRODUCT_NAME_KEY);
    }

    /**
     * Generates a random product name from the textile category.
     * E.g {@code Cotton Dress}
     */
    public String productNameTextile() {
        return dummy4j.expressionResolver().resolve(PRODUCT_NAME_TEXTILE_KEY);
    }

    /**
     * Generates a random product name from the tools category.
     * E.g {@code Iron Cordless Drill}
     */
    public String productNameTool() {
        return dummy4j.expressionResolver().resolve(PRODUCT_NAME_TOOL_KEY);
    }

    /**
     * Generates a random product name from the furniture category.
     * E.g {@code Marble Table}
     */
    public String productNameFurniture() {
        return dummy4j.expressionResolver().resolve(PRODUCT_NAME_FURNITURE_KEY);
    }

    /**
     * Generates random product name from technology and home appliances categories.
     * E.g {@code Energy Saving Dishwasher}
     */
    public String productNameTech() {
        return dummy4j.expressionResolver().resolve(PRODUCT_NAME_TECH_KEY);
    }

    /**
     * Generates a random department name.
     * E.g {@code Stationery}
     */
    public String departmentName() {
        return dummy4j.expressionResolver().resolve(DEPARTMENT_NAME_KEY);
    }

    /**
     * Generates a random company name.
     * E.g {@code Anderson & Anderson Co.}
     */
    public String companyName() {
        return dummy4j.expressionResolver().resolve(COMPANY_NAME_KEY);
    }

    /**
     * Generates a random company type.
     * E.g {@code Nonprofit}
     */
    public String companyType() {
        return dummy4j.expressionResolver().resolve(COMPANY_TYPE_KEY);
    }
}
