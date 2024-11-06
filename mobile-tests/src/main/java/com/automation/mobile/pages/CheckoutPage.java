package com.automation.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private final By fullName = By.xpath("//android.widget.EditText[@content-desc='Full Name* input field']");
    private final By addressLine1 = By.xpath("//android.widget.EditText[@content-desc='Address Line 1* input field']");
    private final By addressLine2 = By.xpath("//android.widget.EditText[@content-desc='Address Line 2 input field']");
    private final By city = By.xpath("//android.widget.EditText[@content-desc='City* input field']");
    private final By state = By.xpath("//android.widget.EditText[@content-desc='State/Region input field']");
    private final By zipCode = By.xpath("//android.widget.EditText[@content-desc='Zip Code* input field']");
    private final By country = By.xpath("//android.widget.EditText[@content-desc='Country* input field']");
    private final By toPaymentButton = By.xpath("//android.view.ViewGroup[@content-desc='To Payment button']");

    // Error message locators
    private final By fullNameError = By.xpath("//android.view.ViewGroup[@content-desc='Full Name*-error-message']//android.widget.TextView");
    private final By addressLine1Error = By.xpath("//android.view.ViewGroup[@content-desc='Address Line 1*-error-message']//android.widget.TextView");
    private final By cityError = By.xpath("//android.view.ViewGroup[@content-desc='City*-error-message']//android.widget.TextView");
    private final By zipCodeError = By.xpath("//android.view.ViewGroup[@content-desc='Zip Code*-error-message']//android.widget.TextView");
    private final By countryError = By.xpath("//android.view.ViewGroup[@content-desc='Country*-error-message']//android.widget.TextView");

    // Methods
    public void enterFullName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullName)).sendKeys(name);
    }

    public void enterAddressLine1(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressLine1)).sendKeys(address);
    }

    public void enterAddressLine2(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressLine2)).sendKeys(address);
    }

    public void enterCity(String cityName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(city)).sendKeys(cityName);
    }

    public void enterState(String stateName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(state)).sendKeys(stateName);
    }

    public void enterZipCode(String code) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(zipCode)).sendKeys(code);
    }

    public void enterCountry(String countryName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(country)).sendKeys(countryName);
    }

    public void proceedToPayment() {
        wait.until(ExpectedConditions.elementToBeClickable(toPaymentButton)).click();
    }

    public String getFullNameError() {
        return getErrorMessage(fullNameError);
    }

    public String getAddressLine1Error() {
        return getErrorMessage(addressLine1Error);
    }

    public String getCityError() {
        return getErrorMessage(cityError);
    }

    public String getZipCodeError() {
        return getErrorMessage(zipCodeError);
    }

    public String getCountryError() {
        return getErrorMessage(countryError);
    }

    private String getErrorMessage(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void fillShippingInfo(String name, String address1, String address2,
                                 String cityName, String stateName, String zipCodeValue, String countryName) {
        enterFullName(name);
        enterAddressLine1(address1);
        enterAddressLine2(address2);
        enterCity(cityName);
        enterState(stateName);
        enterZipCode(zipCodeValue);
        enterCountry(countryName);
    }
}