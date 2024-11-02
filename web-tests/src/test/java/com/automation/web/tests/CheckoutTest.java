package com.automation.web.tests;

import com.automation.web.pages.*;
import org.testng.annotations.Test;
import org.testng.Assert;

public class CheckoutTest extends PerformanceTestBase {

    @Test(description = "User can complete checkout process")
    public void testCompleteCheckout() {
        // Login and add item to cart
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());
        inventoryPage.addItemToCart(0);
        inventoryPage.clickCart();

        // Go to checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();

        // Fill checkout info
        CheckoutStepOnePage checkoutOne = new CheckoutStepOnePage(driver);
        checkoutOne.fillForm("Test", "User", "12345");
        checkoutOne.clickContinue();

        // Verify on step two
        CheckoutStepTwoPage checkoutTwo = new CheckoutStepTwoPage(driver);
        Assert.assertTrue(checkoutTwo.isOnCheckoutStepTwoPage(),
                "User should proceed to checkout step two");

        // Complete checkout
        checkoutTwo.clickFinish();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"),
                "User should complete checkout successfully");
    }

    @Test(description = "User must enter all required information")
    public void testCheckoutValidation() {
        // Login and add item to cart
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());
        inventoryPage.addItemToCart(0);
        inventoryPage.clickCart();

        // Go to checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();

        // Test empty fields validation
        CheckoutStepOnePage checkoutOne = new CheckoutStepOnePage(driver);
        checkoutOne.clickContinue();
        Assert.assertTrue(checkoutOne.isErrorDisplayed(),
                "Error should be displayed for empty fields");

        // Test partial information
        checkoutOne.enterFirstName("Test");
        checkoutOne.clickContinue();
        Assert.assertTrue(checkoutOne.isErrorDisplayed(),
                "Error should be displayed for missing fields");
    }

    @Test(description = "User can navigate back from checkout")
    public void testCheckoutNavigation() {
        // Login and add item to cart
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());
        inventoryPage.addItemToCart(0);
        inventoryPage.clickCart();

        // Go to checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();

        // Test navigation from step one
        CheckoutStepOnePage checkoutOne = new CheckoutStepOnePage(driver);
        checkoutOne.clickCancel();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should return to cart from step one");

        // Go back to checkout and proceed to step two
        cartPage.proceedToCheckout();
        checkoutOne.fillForm("Test", "User", "12345");
        checkoutOne.clickContinue();

        // Test navigation from step two
        CheckoutStepTwoPage checkoutTwo = new CheckoutStepTwoPage(driver);
        checkoutTwo.clickCancel();
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should return to inventory from step two");
    }
}