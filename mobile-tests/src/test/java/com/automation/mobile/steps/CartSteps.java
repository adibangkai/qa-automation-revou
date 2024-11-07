package com.automation.mobile.steps;

import com.automation.mobile.BaseSteps;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartSteps {
    AndroidDriver driver = BaseSteps.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("user adds an item to cart")
    public void addItemToCart() {
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//android.view.ViewGroup[@content-desc=\"store item\"])[1]/android.view.ViewGroup[1]")));
        product.click();

        WebElement addToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Add To Cart button\"]")));
        addToCart.click();
    }

    @When("user opens the cart")
    public void openCart() {
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]")));
        cart.click();
    }

    @When("user clicks Go Shopping")
    public void clickGoShopping() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Go Shopping button\"]")));
        button.click();
    }

    @Then("user should see No items")
    public void verifyNoItems() {
        WebElement noItemsText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text=\"No Items\"]")));
        Assertions.assertTrue(noItemsText.isDisplayed());
    }

    @Then("user should see the added item")
    public void isCartItemDisplayed() {
        List<WebElement> items = driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc=\"product row\"]"));
        Assertions.assertFalse(items.isEmpty());
    }

    @Then("item is no longer in the cart")
    public void isCartItemGone() {
        boolean cartItem = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"product row\"]")
        ));
        Assertions.assertTrue(cartItem);
    }

    @And("user clicks Remove item")
    public void clickRemove() {
        List<WebElement> items = driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc=\"product row\"]"));

        while (!items.isEmpty()) {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.view.ViewGroup[@content-desc=\"remove item\"]")));
            button.click();
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                    By.xpath("//android.view.ViewGroup[@content-desc=\"product row\"]"), items.size()));
            items = driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc=\"product row\"]"));
        }
    }
}
