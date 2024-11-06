package com.automation.web.cucumber.steps;

import com.automation.web.cucumber.TestContext;
import com.automation.web.cucumber.context.LoginContext;
import com.automation.web.pages.LoginPage;
import com.automation.web.pages.InventoryPage;

public class BaseSteps {
    protected final TestContext context;
    protected final LoginContext loginContext;
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected static final String BASE_URL = "https://www.saucedemo.com/";

    public BaseSteps(TestContext context) {
        this.context = context;
        this.loginContext = new LoginContext(context);
        this.loginPage = new LoginPage(context.getDriver());
        this.inventoryPage = context.getInventoryPage();
    }
}