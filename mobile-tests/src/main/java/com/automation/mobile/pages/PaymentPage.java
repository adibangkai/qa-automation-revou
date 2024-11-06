package com.automation.mobile.pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PaymentPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Credit card type constants
    public static final String VISA = "Visa";
    public static final String MASTERCARD = "MasterCard";
    public static final String AMEX = "American Express";

    // Locator strings (using content-desc from similar patterns in the app)
    private static final String USERNAME_FIELD = "//android.widget.EditText[@content-desc='Username input field']";
    private static final String CARD_NUMBER_FIELD = "//android.widget.EditText[@content-desc='Card Number input field']";
    private static final String EXPIRY_DATE_FIELD = "//android.widget.EditText[@content-desc='Expiry Date input field']";
    private static final String SECURITY_CODE_FIELD = "//android.widget.EditText[@content-desc='Security Code input field']";
    private static final String REVIEW_ORDER_BUTTON = "//android.view.ViewGroup[@content-desc='Review Order button']";

    public PaymentPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username) {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(USERNAME_FIELD)));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterCardNumber(String cardNumber) {
        WebElement cardNumberField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(CARD_NUMBER_FIELD)));
        cardNumberField.clear();
        cardNumberField.sendKeys(cardNumber);
    }

    public void enterExpiryDate(String expiryDate) {
        WebElement expiryDateField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(EXPIRY_DATE_FIELD)));
        expiryDateField.clear();
        expiryDateField.sendKeys(expiryDate);
    }

    public void enterSecurityCode(String securityCode) {
        WebElement securityCodeField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(SECURITY_CODE_FIELD)));
        securityCodeField.clear();
        securityCodeField.sendKeys(securityCode);
    }

    public void selectCardType(String cardType) {
        String cardTypeLocator = String.format("//android.view.ViewGroup[@content-desc='%s card type']", cardType);
        WebElement cardTypeElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(cardTypeLocator)));
        cardTypeElement.click();
    }

    public void fillPaymentInfo(String username, String cardNumber, String expiryDate, String securityCode) {
        enterUsername(username);
        enterCardNumber(cardNumber);
        enterExpiryDate(expiryDate);
        enterSecurityCode(securityCode);
    }

    public void reviewOrder() {
        WebElement reviewButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(REVIEW_ORDER_BUTTON)));
        reviewButton.click();
    }

    public boolean isPaymentPageDisplayed() {
        try {
            return driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Payment')]"))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPaymentError() {
        try {
            WebElement errorElement = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='payment-error-message']"));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPaymentSuccessful() {
        try {
            return driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Order Confirmation')]"))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}