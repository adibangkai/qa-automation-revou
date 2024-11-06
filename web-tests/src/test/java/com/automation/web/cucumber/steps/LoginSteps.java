package com.automation.web.cucumber.steps;

import com.automation.web.cucumber.TestContext;
import com.automation.web.enums.UserType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class LoginSteps extends BaseSteps {
    public LoginSteps(TestContext context) {
        super(context);
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        context.getDriver().get(BASE_URL);
        Assert.assertTrue(loginPage.isOnLoginPage(), "User should be on login page");
    }

    @When("I login with {string} credentials")
    public void iLoginWithCredentials(String userType) {
        UserType user = UserType.valueOf(userType);
        context.setCurrentUser(user);
        inventoryPage = loginPage.loginAs(user);
        context.setInventoryPage(inventoryPage);
    }

    @Then("I should be redirected to inventory page")
    public void iShouldBeRedirectedToInventoryPage() {
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "User should be redirected to inventory page");
    }
}