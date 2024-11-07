package com.automation.mobile.steps;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductSteps {
    AndroidDriver driver = BaseSteps.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("user is on catalog screen")
    public void isOnCatalogScreen() {
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]")).click();
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item catalog\"]")).click();
    }

    @When("user clicks on one of the product")
    public void clickProduct() {
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//android.view.ViewGroup[@content-desc=\"store item\"])[1]/android.view.ViewGroup[1]")));
        product.click();
    }

    @When("user clicks on plus button")
    public void clickPlusButton() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"counter plus button\"]")));
        button.click();
    }

    @When("user clicks on minus button")
    public void clickMinusButton() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"counter minus button\"]")));
        button.click();
    }

    @When("user clicks on add to cart button")
    public void clickAddToCart() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Add To Cart button\"]")));
        button.click();
    }

    @When("user clicks on filter button")
    public void clickFilter() {
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"sort button\"]")).click();
    }

    @Then("user should be redirected to product detail screen")
    public void isDetailScreen() {
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']")));
        Assertions.assertTrue(productTitle.isDisplayed());
    }

    @Then("quantity should display {string}")
    public void verifyQuantity(String amount) {
        WebElement counter = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc='counter amount']//android.widget.TextView")));
        String counterText = counter.getText();
        Assertions.assertEquals(amount, counterText);
    }

    @Then("cart icon should display a badge")
    public void isBadgeDisplayed() {
        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]//android.widget.TextView")));
        Assertions.assertTrue(badge.isDisplayed());
    }

    @Then("products should be sorted by name {string}")
    public void checkSortingName(String order) {
        List<WebElement> items = driver.findElements(
                By.xpath("//android.view.ViewGroup[@content-desc='store item']//android.widget.TextView[@content-desc=\"store item text\"]"));

        List<String> products = new ArrayList<>();
        for (WebElement item : items) {
            products.add(item.getText());
        }

        List<String> expected = new ArrayList<>(products);
        switch (order) {
            case "ascending":
                Collections.sort(products);
                Collections.sort(expected);
                break;
            case "descending":
                expected.sort(Collections.reverseOrder());
                break;
        }

        // Change the order of assertion - compare actual against expected
        Assertions.assertEquals(expected, products, "Products are not sorted correctly");
    }

    @Then("products should be sorted by price {string}")
    public void checkSortingPrice(String order) {
        List<WebElement> items = driver.findElements(
                By.xpath("//android.view.ViewGroup[@content-desc='store item']//android.widget.TextView[@content-desc=\"store item price\"]"));

        List<String> products = new ArrayList<>();
        for (WebElement item : items) {
            products.add(item.getText());
        }

        List<String> expected = new ArrayList<>(products);
        switch (order) {
            case "ascending":
                Collections.sort(products);
                Collections.sort(expected);
                break;
            case "descending":
                expected.sort(Collections.reverseOrder());
                products.sort(Collections.reverseOrder());
                break;
        }

        // Change the order of assertion - compare actual against expected
        Assertions.assertEquals(expected, products, "Products are not sorted correctly");
    }

    @And("selects {string}")
    public void clickSort(String sort) {
        switch (sort) {
            case "Name - Ascending":
                WebElement nameAsc = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"nameAsc\"]")));
                nameAsc.click();
                break;
            case "Name - Descending":
                WebElement nameDesc = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"nameDesc\"]")));
                nameDesc.click();
                break;
            case "Price - Descending":
                WebElement priceDesc = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"priceDesc\"]")));
                priceDesc.click();
                break;
            case "Price - Ascending":
                WebElement priceAsc = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.ViewGroup[@content-desc=\"priceAsc\"]")));
                priceAsc.click();
                break;
        }
    }

    @And("screen should display:")
    public void verifyScreenElement(DataTable dataTable) {
        List<String> elements = dataTable.asList();

        for (String element : elements) {
            switch(element.toLowerCase().trim()) {
                case "product image":
                    WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//android.widget.ScrollView[@content-desc=\"product screen\"]/android.view.ViewGroup/android.widget.ImageView")));
                    Assertions.assertTrue(productImage.isDisplayed());
                    break;
                case "product name":
                    WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']")));
                    Assertions.assertTrue(productTitle.isDisplayed());
                    break;
                case "product price":
                    WebElement productPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//android.widget.TextView[@content-desc=\"product price\"]")));
                    Assertions.assertTrue(productPrice.isDisplayed());
                    break;
                case "product description":
                    WebElement productDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//android.widget.TextView[@content-desc=\"product description\"]")));
                    Assertions.assertTrue(productDescription.isDisplayed());
                    break;
                case "add to cart button":
                    WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//android.view.ViewGroup[@content-desc=\"Add To Cart button\"]")));
                    Assertions.assertTrue(button.isDisplayed());
                    break;
                case "ratings":
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"review star 1\"]")).isDisplayed());
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"review star 2\"]")).isDisplayed());
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"review star 3\"]")).isDisplayed());
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"review star 4\"]")).isDisplayed());
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"review star 5\"]")).isDisplayed());

                    break;
                case "quantity":
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"counter minus button\"]")).isDisplayed());
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"counter amount\"]")).isDisplayed());
                    Assertions.assertTrue(driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"counter plus button\"]")).isDisplayed());
                    break;
            }
        }
    }

    @And("add to cart button should be disabled")
    public void isButtonDisabled() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Add To Cart button\"]")));
        Assertions.assertFalse(button.isEnabled());
    }
}
