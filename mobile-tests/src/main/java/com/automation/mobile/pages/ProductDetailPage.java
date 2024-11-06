package com.automation.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductDetailPage {
  private final AndroidDriver driver;
  private final WebDriverWait wait;

  public ProductDetailPage(AndroidDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  // Locators
  private final By productTitle = By.xpath("//android.view.ViewGroup[@content-desc='container header']//android.widget.TextView");
  private final By productPrice = By.xpath("//android.widget.TextView[@content-desc='product price']");
  private final By productDescription = By.xpath("//android.widget.TextView[@content-desc='product description']");
  private final By addToCartButton = By.xpath("//android.view.ViewGroup[@content-desc='Add To Cart button']");
  private final By counterPlus = By.xpath("//android.view.ViewGroup[@content-desc='counter plus button']");
  private final By counterMinus = By.xpath("//android.view.ViewGroup[@content-desc='counter minus button']");
  private final By counterAmount = By.xpath("//android.view.ViewGroup[@content-desc='counter amount']//android.widget.TextView");
  private final By colorOptions = By.xpath("//android.view.ViewGroup[contains(@content-desc, ' circle')]");

  // Methods
  public String getProductTitle() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle)).getText();
  }

  public String getProductPrice() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText();
  }

  public String getProductDescription() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(productDescription)).getText();
  }

  public void addToCart() {
    wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
  }

  public void incrementQuantity(int times) {
    WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(counterPlus));
    for (int i = 0; i < times; i++) {
      plusButton.click();
    }
  }

  public void decrementQuantity(int times) {
    WebElement minusButton = wait.until(ExpectedConditions.elementToBeClickable(counterMinus));
    for (int i = 0; i < times; i++) {
      minusButton.click();
    }
  }

  public String getCurrentQuantity() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(counterAmount)).getText();
  }

  public void selectColor(String color) {
    String colorXPath = String.format("//android.view.ViewGroup[@content-desc='%s circle']", color.toLowerCase());
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(colorXPath))).click();
  }
}