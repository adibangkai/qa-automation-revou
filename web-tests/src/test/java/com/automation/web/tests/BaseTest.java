package com.automation.web.tests;

import com.automation.web.driver.DriverManager;
import com.automation.web.enums.UserType;
import com.automation.web.pages.InventoryPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    protected final String BASE_URL = "https://www.saucedemo.com/";
    protected UserType currentUser;

    @Parameters({"userType", "browser"})
    @BeforeMethod
    public void setup(@Optional("STANDARD_USER") String userType,
                      @Optional("chrome") String browser) {
        DriverManager.setDriver(browser);
        driver = DriverManager.getDriver();
        driver.get(BASE_URL);

        // Set the current user based on parameter
        currentUser = UserType.valueOf(userType);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            // Reset app state if we're on a page with menu
            try {
                InventoryPage inventoryPage = new InventoryPage(driver);
                inventoryPage.openMenu();
                inventoryPage.clickReset();
            } catch (Exception e) {
                // If we can't reset (e.g., if we're on login page), just continue to quit
            }

            // Quit driver
            DriverManager.quitDriver();
        }
    }

    protected UserType getCurrentUser() {
        return currentUser;
    }
}
