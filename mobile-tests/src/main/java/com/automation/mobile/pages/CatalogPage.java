package com.automation.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CatalogPage {
  private final AndroidDriver driver;
  private final WebDriverWait wait;

  public CatalogPage(AndroidDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  // Locators
  private final By menuButton = By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]");
  private final By sortButton = By.xpath("//android.view.ViewGroup[@content-desc=\"sort button\"]");
  private final By cartBadge = By.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]");
  private final By cartItemCount = By.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]//android.widget.TextView");
  private final By productsTitle = By.xpath("//android.widget.TextView[contains(@text, 'Products')]");
  private final By storeItems = By.xpath("//android.view.ViewGroup[@content-desc=\"store item\"]");

  // Methods
  public void openMenu() {
    wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
  }

  public void clickSortButton() {
    wait.until(ExpectedConditions.elementToBeClickable(sortButton)).click();
  }
  public String getTitle() {
    WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle));
    return titleElement.getText();
  }

  public void openCart() {
    wait.until(ExpectedConditions.elementToBeClickable(cartBadge)).click();
  }

  public String getCartItemCount() {
    try {
      return wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemCount)).getText();
    } catch (Exception e) {
      return "0";
    }
  }

  public boolean isCatalogPageDisplayed() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle)).isDisplayed();
  }

  public void selectProduct(String productName) {
    String productXPath = String.format(
            "//android.widget.TextView[@content-desc='store item text' and @text='%s']/parent::android.view.ViewGroup",
            productName
    );
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(productXPath))).click();
  }

  public List<WebElement> getAllProducts() {
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(storeItems));
  }

  public String getProductPrice(String productName) {
    String priceXPath = String.format(
            "//android.widget.TextView[@text='%s']/following-sibling::android.widget.TextView[@content-desc='store item price']",
            productName
    );
    return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(priceXPath))).getText();
  }
}