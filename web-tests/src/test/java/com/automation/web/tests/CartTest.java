package com.automation.web.tests;

import com.automation.web.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

public class CartTest extends PerformanceTestBase {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        inventoryPage = loginPage.loginAs(getCurrentUser());
        cartPage = new CartPage(driver);
    }

    /**
     * Test adding single item to cart and verifying cart details
     */
    @Test(description = "User can add single item to cart")
    public void testAddSingleItemToCart() {
        // Get item details before adding to cart
        String expectedItemName = inventoryPage.getItemName(0);

        // Add item to cart
        inventoryPage.addItemToCart(0);
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
                "Cart badge should show 1 item");

        // Navigate to cart and verify
        inventoryPage.clickCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should be on cart page");
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should show 1 item");
        Assert.assertEquals(cartPage.getItemName(0), expectedItemName,
                "Cart should contain the correct item");
    }

    /**
     * Test adding multiple items to cart and verifying cart details
     */
    @Test(description = "User can add multiple items to cart")
    public void testAddMultipleItemsToCart() {
        // Get item names before adding to cart
        String firstItemName = inventoryPage.getItemName(0);
        String secondItemName = inventoryPage.getItemName(1);

        // Add items to cart
        inventoryPage.addItemToCart(0);
        inventoryPage.addItemToCart(1);
        Assert.assertEquals(inventoryPage.getCartItemCount(), 2,
                "Cart badge should show 2 items");

        // Navigate to cart and verify
        inventoryPage.clickCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should be on cart page");
        Assert.assertEquals(cartPage.getCartItemCount(), 2,
                "Cart should show 2 items");
        Assert.assertTrue(cartPage.isItemInCart(firstItemName),
                "First item should be in cart");
        Assert.assertTrue(cartPage.isItemInCart(secondItemName),
                "Second item should be in cart");
    }

    /**
     * Test removing single item from cart
     */
    @Test(description = "User can remove single item from cart")
    public void testRemoveSingleItemFromCart() {
        // Add item and navigate to cart
        String itemToRemove = inventoryPage.getItemName(0);
        inventoryPage.addItemToCart(0);
        inventoryPage.clickCart();

        // Verify initial state
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should initially have 1 item");

        // Remove item and verify
        cartPage.removeItem(0);
        Assert.assertEquals(cartPage.getCartItemCount(), 0,
                "Cart should be empty after removing item");
        Assert.assertFalse(cartPage.isItemInCart(itemToRemove),
                "Removed item should not be in cart");
    }

    /**
     * Test removing items from cart with multiple items
     */
    @Test(description = "User can remove items from cart containing multiple items")
    public void testRemoveItemFromMultipleItems() {
        // Add multiple items and navigate to cart
        String firstItemName = inventoryPage.getItemName(0);
        String secondItemName = inventoryPage.getItemName(1);

        inventoryPage.addItemToCart(0);
        inventoryPage.addItemToCart(1);
        inventoryPage.clickCart();

        // Verify initial state
        Assert.assertEquals(cartPage.getCartItemCount(), 2,
                "Cart should initially have 2 items");

        // Remove first item and verify
        cartPage.removeItem(0);
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should have 1 item after removal");
        Assert.assertFalse(cartPage.isItemInCart(firstItemName),
                "First item should be removed from cart");
        Assert.assertTrue(cartPage.isItemInCart(secondItemName),
                "Second item should still be in cart");
    }

    /**
     * Test cart navigation functions
     */
    @Test(description = "User can navigate from cart")
    public void testCartNavigation() {
        // Add item and go to cart
        inventoryPage.addItemToCart(0);
        inventoryPage.clickCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should be on cart page");

        // Test continue shopping
        cartPage.continueShopping();
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should return to inventory page");

        // Return to cart and test checkout
        inventoryPage.clickCart();
        cartPage.proceedToCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),
                "Should proceed to checkout page");
    }
}