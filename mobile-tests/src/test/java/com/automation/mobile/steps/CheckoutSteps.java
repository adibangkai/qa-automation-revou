package com.automation.mobile.steps;

import com.automation.mobile.BaseSteps;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class CheckoutSteps {
    AndroidDriver driver = BaseSteps.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @When("user clicks proceed to checkout button")
    public void clickCheckout() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Proceed To Checkout button\"]")));
        button.click();
    }

    @When("user enters the following shipping details:")
    public void inputShippingDetails(DataTable dataTable) {
        Map<String, String> shippingData = dataTable.asMap(String.class, String.class);

        shippingData.forEach((key, value) -> {
            switch (key) {
                case "Full Name":
                    WebElement fullNameField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Full Name* input field\"]"));
                    fullNameField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        fullNameField.sendKeys(value);
                    }
                    break;
                case "Address Line 1":
                    WebElement address1Field = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Address Line 1* input field\"]"));
                    address1Field.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        address1Field.sendKeys(value);
                    }
                    break;
                case "Address Line 2":
                    WebElement address2Field = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Address Line 2 input field\"]"));
                    address2Field.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        address2Field.sendKeys(value);
                    }
                    break;
                case "City":
                    WebElement cityField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"City* input field\"]"));
                    cityField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        cityField.sendKeys(value);
                    }
                    break;
                case "State":
                    WebElement stateField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"State/Region input field\"]"));
                    stateField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        stateField.sendKeys(value);
                    }
                    break;
                case "Zip Code":
                    WebElement zipCodeField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Zip Code* input field\"]"));
                    zipCodeField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        zipCodeField.sendKeys(value);
                    }
                    break;
                case "Country":
                    WebElement countryField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Country* input field\"]"));
                    countryField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        countryField.sendKeys(value);
                    }
                    break;
            }
        });
    }

    @When("user enters the following payment details:")
    public void inputPaymentDetails(DataTable dataTable) {
        Map<String, String> shippingData = dataTable.asMap(String.class, String.class);

        shippingData.forEach((key, value) -> {
            switch (key) {
                case "Full Name":
                    WebElement fullNameField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Full Name* input field\"]"));
                    fullNameField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        fullNameField.sendKeys(value);
                    }
                    break;
                case "Card Number":
                    WebElement cardNumberField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Card Number* input field\"]"));
                    cardNumberField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        cardNumberField.sendKeys(value);
                    }
                    break;
                case "Expiration Date":
                    WebElement expirationDateField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Expiration Date* input field\"]"));
                    expirationDateField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        expirationDateField.sendKeys(value);
                    }
                    break;
                case "Security Code":
                    WebElement securityCodeField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Security Code* input field\"]"));
                    securityCodeField.clear();
                    if (value != null && !value.trim().isEmpty()) {
                        securityCodeField.sendKeys(value);
                    }
                    break;
            }
        });
    }

    @When("user clicks Place Order button")
    public void clickPlaceOrder() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Place Order button\"]")));
        button.click();
    }

    @Then("user should be redirected to login screen")
    public void isRedirectedToLogin() {
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//android.widget.TextView[@text=\"Login\"])[1]")));
        Assertions.assertTrue(text.isDisplayed());
    }

    @Then("user should be redirected to checkout screen")
    public void redirectedToCheckout() {
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text=\"Checkout\"]")));
        Assertions.assertTrue(text.isDisplayed());
    }

    @Then("user should be redirected to payment method")
    public void isRedirectedToPaymentMethod() {
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text=\"Enter a payment method\"]")));
        Assertions.assertTrue(text.isDisplayed());
    }

    @Then("user should be redirected to order review")
    public void isRedirectedToOrderReview() {
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text=\"Review your order\"]")));
        Assertions.assertTrue(text.isDisplayed());
    }

    @Then("user should be redirected to checkout complete")
    public void isRedirectedToCheckoutComplete() {
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text=\"Checkout Complete\"]")));
        Assertions.assertTrue(text.isDisplayed());
    }

    @Then("user should get an error on {string} field")
    public void validateError(String field) {
        switch(field) {
            case "full name":
                WebElement fullNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"Full Name*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(fullNameError.isDisplayed());
                break;
            case "address line 1":
                WebElement address1Error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"Address Line 1*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(address1Error.isDisplayed());
                break;
            case "city":
                WebElement cityError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"City*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(cityError.isDisplayed());
                break;
            case "zip code":
                WebElement zipCodeError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"Zip Code*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(zipCodeError.isDisplayed());
                break;
            case "country":
                WebElement countryError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"Country*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(countryError.isDisplayed());
                break;
            case "card number":
                WebElement cardNumberError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"Card Number*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(cardNumberError.isDisplayed());
                break;
            case "expiration date":
                WebElement expirationDateError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"Expiration Date*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(expirationDateError.isDisplayed());
                break;
            case "security code":
                WebElement securityCodeError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"Security Code*-error-message\"]//android.widget.TextView")));
                Assertions.assertTrue(securityCodeError.isDisplayed());
                break;
        }
    }

    @And("user is on cart screen")
    public void isOnCart() {
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]")));
        cart.click();
    }

    @And("user logs in to the app")
    public void login() {
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Username input field\"]")).clear();
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Password input field\"]")).clear();
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Username input field\"]")).sendKeys("bob@example.com");
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Password input field\"]")).sendKeys("10203040");
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login button\"]")).click();
    }

    @And("user clicks on To Payment button")
    public void clickToPayment() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"To Payment button\"]")));
        button.click();
    }

    @And("user clicks on Review Order button")
    public void clickReviewOrder() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Review Order button\"]")));
        button.click();
        button.click();
    }

    @And("user should see cart items")
    public void verifyCartItems() {
        List<WebElement> items = driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc=\"product row\"]"));
        Assertions.assertFalse(items.isEmpty());
    }

    @And("user should see delivery address")
    public void verifyDeliveryAddress() {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"checkout delivery address\"]")));
        Assertions.assertTrue(container.isDisplayed());
        List<WebElement> textLists = container.findElements(By.xpath("//android.widget.TextView"));
        for (WebElement textList : textLists) {
            String text = textList.getText();
            Assertions.assertNotNull(text);
            Assertions.assertFalse(text.trim().isEmpty());
        }
    }

    @And("user should see payment method")
    public void verifyPaymentMethod() {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"checkout payment info\"]")));
        Assertions.assertTrue(container.isDisplayed());
        List<WebElement> textLists = container.findElements(By.xpath("//android.widget.TextView"));
        for (WebElement textList : textLists) {
            String text = textList.getText();
            Assertions.assertNotNull(text);
            Assertions.assertFalse(text.trim().isEmpty());
        }
    }

    @And("cart should be empty")
    public void verifyCartEmpty() {
        boolean cartBadge = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]//android.widget.TextView")
        ));
        Assertions.assertTrue(cartBadge);
    }

}
