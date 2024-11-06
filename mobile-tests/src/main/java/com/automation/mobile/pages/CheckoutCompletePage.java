package com.automation.mobile.pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutCompletePage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final String COMPLETE_HEADER = "//android.widget.TextView[contains(@text, 'Checkout Complete')]";
    private static final String THANK_YOU_MESSAGE = "//android.widget.TextView[contains(@text, 'Thank you')]";
    private static final String ORDER_NUMBER = "//android.widget.TextView[contains(@text, 'Order Number')]";
    private static final String CONTINUE_SHOPPING_BUTTON = "//android.view.ViewGroup[@content-desc='Continue Shopping button']";

    public CheckoutCompletePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isCheckoutCompletePageDisplayed() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(COMPLETE_HEADER))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getThankYouMessage() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(THANK_YOU_MESSAGE))).getText();
    }

    public String getOrderNumber() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(ORDER_NUMBER))).getText();
    }

    public void continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(CONTINUE_SHOPPING_BUTTON))).click();
    }

    public boolean verifyOrderDetails() {
        try {
            // Verify all essential elements are present
            boolean hasThankYou = !getThankYouMessage().isEmpty();
            boolean hasOrderNumber = !getOrderNumber().isEmpty();
            boolean hasContinueButton = driver.findElement(
                    By.xpath(CONTINUE_SHOPPING_BUTTON)).isDisplayed();

            return hasThankYou && hasOrderNumber && hasContinueButton;
        } catch (Exception e) {
            return false;
        }
    }
}