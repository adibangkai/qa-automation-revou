package com.automation.web.tests;

import com.automation.web.pages.*;
import org.testng.annotations.Test;
import org.testng.Assert;

public class InventoryTest extends PerformanceTestBase {


    /**
     * Test adding an item to the cart
     */
    @Test(description = "User can add item to cart")
    public void testAddItemToCart() {
        // Login and navigate to inventory page
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());

        // Initial cart should be empty
        Assert.assertEquals(inventoryPage.getCartItemCount(), 0,
                "Cart should be empty initially");

        // Add item to cart
        inventoryPage.addItemToCart(0);

        // Verify cart count increased
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
                "Cart should show 1 item after adding");

        // Verify "Remove" button is displayed for the item
        Assert.assertTrue(inventoryPage.isRemoveButtonDisplayed(0),
                "Remove button should be displayed after adding item");
    }

    /**
     * Test removing an item from the cart
     */
    @Test(description = "User can remove item from cart")
    public void testRemoveItemFromCart() {
        // Login and navigate to inventory page
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());

        // Add item to cart first
        inventoryPage.addItemToCart(0);
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
                "Cart should show 1 item after adding");

        // Remove item from cart
        inventoryPage.removeItemFromCart(0);

        // Verify cart is empty
        Assert.assertEquals(inventoryPage.getCartItemCount(), 0,
                "Cart should be empty after removing item");

        // Verify "Add to Cart" button is displayed again
        Assert.assertTrue(inventoryPage.isAddToCartButtonDisplayed(0),
                "Add to Cart button should be displayed after removing item");
    }

    @Test(description = "User can navigate to cart")
    public void testNavigateToCart() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());

        inventoryPage.addItemToCart(0); // Add item before going to cart
        inventoryPage.clickCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(),
                "User should be able to navigate to cart");
    }
}
