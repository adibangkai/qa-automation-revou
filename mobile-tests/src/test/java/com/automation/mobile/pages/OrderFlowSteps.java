package com.automation.mobile.pages;

import com.automation.mobile.TestContext;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Map;

public class OrderFlowSteps {
    private final AndroidDriver driver;
    private final WebDriverWait wait;
    private final CatalogPage catalogPage;
    private final ProductDetailPage productDetailPage;
    private final CheckoutPage checkoutPage;
    private final CartPage cartPage;
    private final PaymentPage paymentPage;
    private final CheckoutCompletePage checkoutCompletePage;

    public OrderFlowSteps() {
        this.driver = TestContext.getDriver();
        this.wait = TestContext.getWait();
        this.catalogPage = new CatalogPage(driver);
        this.productDetailPage = new ProductDetailPage(driver);
        this.checkoutPage = new CheckoutPage(driver);
        this.cartPage = new CartPage(driver);
        this.paymentPage = new PaymentPage(driver);
        this.checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @When("User selects product {string}")
    public void userSelectsProduct(String productName) {
        catalogPage.selectProduct(productName);
        String actualProduct = productDetailPage.getProductTitle();
        System.out.println(actualProduct);
        Assert.assertEquals(actualProduct, productName,
                "Selected product page doesn't match expected product");
    }

    @And("User selects color {string}")
    public void userSelectsColor(String color) {
        productDetailPage.selectColor(color);
    }

    @And("User sets quantity to {string}")
    public void userSetsQuantity(String quantity) {
        int currentQty = Integer.parseInt(productDetailPage.getCurrentQuantity());
        int targetQty = Integer.parseInt(quantity);

        if (currentQty < targetQty) {
            productDetailPage.incrementQuantity(targetQty - currentQty);
        } else if (currentQty > targetQty) {
            productDetailPage.decrementQuantity(currentQty - targetQty);
        }

        Assert.assertEquals(productDetailPage.getCurrentQuantity(), quantity,
                "Quantity was not set correctly");
    }

    @And("User adds item to cart")
    public void userAddsItemToCart() {
        productDetailPage.addToCart();
    }

    @And("User navigates back to catalog")
    public void userNavigatesBackToCatalog() {
        driver.navigate().back();
        Assert.assertTrue(catalogPage.isCatalogPageDisplayed(),
                "Failed to navigate back to catalog page");
    }

    @And("User opens cart")
    public void userOpensCart() {
        cartPage.openCart();
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cart page is not displayed");
    }

    @And("User proceeds to checkout")
    public void userProceedsToCheckout() {
        cartPage.proceedToCheckout();
    }

    @When("User fills shipping information")
    public void userFillsShippingInformation(DataTable dataTable) {
        Map<String, String> shippingInfo = dataTable.asMap(String.class, String.class);

        checkoutPage.fillShippingInfo(
                shippingInfo.get("fullName"),
                shippingInfo.get("addressLine1"),
                shippingInfo.get("addressLine2"),
                shippingInfo.get("city"),
                shippingInfo.get("state"),
                shippingInfo.get("zipCode"),
                shippingInfo.get("country")
        );
    }

    @And("User clicks proceed to payment")
    public void userClicksProceedToPayment() {
        checkoutPage.proceedToPayment();
    }

    @Then("User should see payment screen")
    public void userShouldSeePaymentScreen() {
        // Add verification for payment screen when implemented
        // This would depend on the payment page implementation
        Assert.assertTrue(driver.findElements(By.xpath("//android.widget.TextView[contains(@text, 'Payment')]")).size() > 0,
                "Payment screen is not displayed");
    }


    @Then("User should be on checkout complete page")
    public void userShouldBeOnCheckoutCompletePage() {
        Assert.assertTrue(checkoutCompletePage.isCheckoutCompletePageDisplayed(),
                "Checkout complete page is not displayed");
    }

    @Then("Order confirmation details should be displayed")
    public void orderConfirmationDetailsShouldBeDisplayed() {
        Assert.assertTrue(checkoutCompletePage.verifyOrderDetails(),
                "Order confirmation details are not properly displayed");
    }

    @And("User notes the order number")
    public void userNotesOrderNumber() {
        String orderNumber = checkoutCompletePage.getOrderNumber();
        // Store order number in test context if needed for later use
        TestContext.setOrderNumber(orderNumber);
        Assert.assertFalse(orderNumber.isEmpty(),
                "Order number is not displayed");
    }

    @When("User continues shopping")
    public void userContinuesShopping() {
        checkoutCompletePage.continueShopping();
        Assert.assertTrue(catalogPage.isCatalogPageDisplayed(),
                "Did not return to catalog page after continuing shopping");
    }
}