package com.automation.mobile.pages;

import com.automation.mobile.TestContext;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginSteps {
    private final LoginPage loginPage;
    private final AndroidDriver driver;
    private final WebDriverWait wait;
    private final CatalogPage catalogsPage;

    public LoginSteps() {
        this.driver = TestContext.getDriver();
        this.wait = TestContext.getWait();
        this.loginPage = new LoginPage(driver);
        this.catalogsPage = new CatalogPage(driver);
    }

    @Given("User is on the catalogs page")
    public void userIsOnProductsPage() {
        // Verify we're on catalogs page
        WebElement catalogsTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text='Products']")));
        Assert.assertTrue(catalogsTitle.isDisplayed(), "Not on catalogs page");
    }

    @When("User clicks on menu button")
    public void userClicksOnMenuButton() {
        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView")));
        menuButton.click();
    }

    @And("User clicks on login menu item")
    public void userClicksOnLoginMenuItem() {
        WebElement loginMenuItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.view.ViewGroup[@content-desc=\"menu item log in\"]")));
        loginMenuItem.click();

        // Verify we're on login page
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.EditText[@content-desc=\"Username input field\"]")));
    }

    @When("User enter username {string}")
    public void enterUsername(String username) {
        loginPage.setUsername(username);
    }

    @When("User enter password {string}")
    public void enterPassword(String password) {
        loginPage.setPassword(password);
    }

    @When("User click the login button")
    public void clickLoginButton() throws InterruptedException {
        loginPage.clickLogin();
        Thread.sleep(100); // Small wait for response
    }

    @Then("User should be redirected to the catalog page")
    public void verifyRedirectToInventoryPage() {
        Assert.assertEquals(catalogsPage.getTitle(), "Products",
                "Login successful but not navigated to inventory page");
    }

    @Then("User should see an error message {string}")
    public void seeAnErrorMessage(String errorMessage) {
        Assert.assertEquals(loginPage.getErrorMessage(), errorMessage,
                "Login failed, error message doesn't match");
    }

    @Then("User should see username error message {string}")
    public void userShouldSeeUsernameErrorMessage(String errorMessage) {
        String actualError = loginPage.getUsernameErrorMessage();
        Assert.assertFalse(actualError.isEmpty(),
                "No username error message is displayed when it should show: " + errorMessage);
        Assert.assertEquals(actualError, errorMessage,
                "Username error message doesn't match expected text");
    }

    @Then("User should see password error message {string}")
    public void userShouldSeePasswordErrorMessage(String errorMessage) {
        String actualError = loginPage.getPasswordErrorMessage();
        Assert.assertFalse(actualError.isEmpty(),
                "No password error message is displayed when it should show: " + errorMessage);
        Assert.assertEquals(actualError, errorMessage,
                "Password error message doesn't match expected text");
    }
}