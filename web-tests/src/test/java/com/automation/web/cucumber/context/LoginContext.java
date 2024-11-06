package com.automation.web.cucumber.context;

import com.automation.web.cucumber.TestContext;
import com.automation.web.enums.UserType;
import com.automation.web.pages.LoginPage;
import com.automation.web.pages.InventoryPage;
import org.testng.Assert;

public class LoginContext {
    private final TestContext context;
    private LoginPage loginPage;
    protected InventoryPage inventoryPage;
    private static final String BASE_URL = "https://www.saucedemo.com/";

    public LoginContext(TestContext context) {
        this.context = context;
        this.loginPage = new LoginPage(context.getDriver());
    }

    public InventoryPage loginAs(String userType) {
        context.getDriver().get(BASE_URL);
        UserType user = UserType.valueOf(userType);
        context.setCurrentUser(user);
        inventoryPage = loginPage.loginAs(user);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should be redirected to inventory page after login");
        return inventoryPage;
    }

    public void navigateToLoginPage() {
        context.getDriver().get(BASE_URL);
        Assert.assertTrue(loginPage.isOnLoginPage(), "User should be on login page");
    }

    public InventoryPage getInventoryPage() {
        return inventoryPage;
    }
}