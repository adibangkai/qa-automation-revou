package com.automation.web.data;

import org.testng.annotations.DataProvider;
import com.automation.web.enums.UserType;

public class TestDataProvider {

    @DataProvider(name = "standardUsers")
    public static Object[][] standardUsers() {
        return new Object[][] {
                { UserType.STANDARD_USER },
                { UserType.PERFORMANCE_GLITCH_USER }
        };
    }

    @DataProvider(name = "problemUsers")
    public static Object[][] problemUsers() {
        return new Object[][] {
                { UserType.PROBLEM_USER },
                { UserType.ERROR_USER },
                { UserType.VISUAL_USER }
        };
    }

    @DataProvider(name = "checkoutUsers")
    public static Object[][] checkoutUsers() {
        return new Object[][] {
                { UserType.STANDARD_USER, "John", "Doe", "12345" },
                { UserType.PERFORMANCE_GLITCH_USER, "Jane", "Smith", "67890" }
        };
    }

    @DataProvider(name = "inventoryTestData")
    public static Object[][] inventoryTestData() {
        return new Object[][] {
                { UserType.STANDARD_USER, "Sauce Labs Backpack", "$29.99" },
                { UserType.PERFORMANCE_GLITCH_USER, "Sauce Labs Backpack", "$29.99" },
                { UserType.PROBLEM_USER, "Sauce Labs Backpack", "$29.99" },
                { UserType.VISUAL_USER, "Sauce Labs Backpack", "$29.99" }
        };
    }

    @DataProvider(name = "cartTestData")
    public static Object[][] cartTestData() {
        return new Object[][] {
                { UserType.STANDARD_USER, 2 },  // User type and number of items to add
                { UserType.PERFORMANCE_GLITCH_USER, 1 }
        };
    }
}