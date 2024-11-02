package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.automation.web.enums.UserType;

public class LoginPage extends BasePage {

    @FindBy(css = "[data-test='username']")
    private WebElement usernameInput;

    @FindBy(css = "[data-test='password']")
    private WebElement passwordInput;

    @FindBy(css = "[data-test='login-button']")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(id = "login_credentials")
    private WebElement loginCredentials;

    @FindBy(className = "login_password")
    private WebElement loginPassword;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        sendKeys(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        sendKeys(passwordInput, password);
        return this;
    }

    public void clickLogin() {
        click(loginButton);
    }

    public InventoryPage loginAs(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickLogin();
        return new InventoryPage(driver);
    }

    // Add this new method
    public InventoryPage loginAs(UserType userType) {
        return loginAs(userType.getUsername(), userType.getPassword());
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isOnLoginPage() {
        return isDisplayed(loginButton);
    }

    public String getLoginCredentials() {
        return getText(loginCredentials);
    }

    public String getLoginPassword() {
        return getText(loginPassword);
    }
}