package com.automation.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CartPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private final By cartBadge = By.xpath("//android.view.ViewGroup[@content-desc='cart badge']");
    private final By proceedToCheckoutButton = By.xpath("//android.view.ViewGroup[@content-desc='Proceed To Checkout button']");
    private final By cartItems = By.xpath("//android.view.ViewGroup[@content-desc='cart item']");
    private final By cartTitle = By.xpath("//android.widget.TextView[contains(@text, 'My Cart')]");
    private final By totalAmount = By.xpath("//android.widget.TextView[contains(@text, 'Total:')]");

    // Methods
    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartBadge)).click();
    }

    public boolean isCartPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle)).isDisplayed();
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
    }

    public List<WebElement> getCartItems() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));
    }

    public String getTotalAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmount)).getText();
    }

    public void removeItemFromCart(String productName) {
        String removeButtonXPath = String.format(
                "//android.widget.TextView[@text='%s']/../..//android.view.ViewGroup[@content-desc='remove item']",
                productName
        );
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(removeButtonXPath))).click();
    }

    public boolean isItemInCart(String productName) {
        String itemXPath = String.format(
                "//android.widget.TextView[@text='%s']",
                productName
        );
        return !driver.findElements(By.xpath(itemXPath)).isEmpty();
    }

    public String getItemQuantity(String productName) {
        String quantityXPath = String.format(
                "//android.widget.TextView[@text='%s']/../..//android.widget.TextView[@content-desc='counter amount']",
                productName
        );
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(quantityXPath))).getText();
    }
}