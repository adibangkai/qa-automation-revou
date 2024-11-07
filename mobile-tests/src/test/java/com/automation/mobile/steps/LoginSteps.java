package com.automation.mobile.steps;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

public class LoginSteps {
    AndroidDriver driver = BaseSteps.getDriver();

    @Given("user is not logged in")
    public void isLogOut() {
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]")).click();
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item log out\"]")).click();
        driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]")).click();
        driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]")).click();
    }

    @Given("user is on login screen")
    public void isOnLoginScreen() {
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]")).click();
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item log in\"]")).click();
    }

    @When("user enters email {string} and password {string}")
    public void login(String email, String password) {
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Username input field\"]")).clear();
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Password input field\"]")).clear();
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Username input field\"]")).sendKeys(email);
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Password input field\"]")).sendKeys(password);
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login button\"]")).click();
    }

    @Then("user should be redirected to catalog screen")
    public void isRedirectedToCatalog() {
        Assertions.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text=\"Products\"]")).isDisplayed());
    }

    @Then("user should get an error message: {string}")
    public void isErrorDisplayed(String errorMsg) {
        Assertions.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text=\"" + errorMsg + "\"]")).isDisplayed());
    }

    @Then("user should get an username error message: {string}")
    public void isUsernameErrorDisplayed(String errorMsg) {
        Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Username-error-message\"]")).isDisplayed());
    }

    @Then("user should get an password error message: {string}")
    public void isPasswordErrorDisplayed(String errorMsg) {
        Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Password-error-message\"]")).isDisplayed());
    }
}
