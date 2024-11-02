package com.automation.web.tests;

import com.automation.web.pages.*;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LoginTest extends PerformanceTestBase {

    @Test(description = "User can login successfully")
    public void testLogin() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());

        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "User should be redirected to inventory page after login");
    }
}