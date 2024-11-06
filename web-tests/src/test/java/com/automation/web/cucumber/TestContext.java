package com.automation.web.cucumber;

import com.automation.web.driver.DriverManager;
import com.automation.web.enums.UserType;
import com.automation.web.pages.InventoryPage;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;
    private UserType currentUser;
    private InventoryPage inventoryPage;  // Add this
    private static final ThreadLocal<TestContext> CONTEXT = new ThreadLocal<>();

    public TestContext() {
        DriverManager.setDriver("chrome");
        this.driver = DriverManager.getDriver();
        CONTEXT.set(this);
    }

    public static TestContext getContext() {
        return CONTEXT.get();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setCurrentUser(UserType userType) {
        this.currentUser = userType;
    }

    public UserType getCurrentUser() {
        return currentUser;
    }

    public void setInventoryPage(InventoryPage page) {  // Add this
        this.inventoryPage = page;
    }

    public InventoryPage getInventoryPage() {  // Add this
        return inventoryPage;
    }

    public void tearDown() {
        if (driver != null) {
            DriverManager.quitDriver();
        }
        CONTEXT.remove();
    }
}