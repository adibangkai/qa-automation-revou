package com.automation.web.cucumber.steps;

import com.automation.web.cucumber.TestContext;
import com.automation.web.enums.UserType;
import com.automation.web.pages.CartPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class CommonSteps extends BaseSteps {
    private CartPage cartPage;

    public CommonSteps(TestContext context) {
        super(context);
        this.cartPage = new CartPage(context.getDriver());
    }

    @Given("I am logged in as {string} user")
    public void iAmLoggedInAsUser(String userType) {
        context.getDriver().get(BASE_URL);
        UserType user = UserType.valueOf(userType);
        context.setCurrentUser(user);
        inventoryPage = loginPage.loginAs(user);
        context.setInventoryPage(inventoryPage);  // Store in context
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should be redirected to inventory page after login");
    }

    @Then("I should be on the inventory page")
    public void iShouldBeOnTheInventoryPage() {
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should be on inventory page");
    }

    @Then("I should be on the cart page")
    public void iShouldBeOnTheCartPage() {
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should be on cart page");
    }
}
