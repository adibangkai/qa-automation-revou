package com.automation.web.tests;

import com.automation.web.pages.*;
import com.automation.web.enums.UserType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.util.List;

public class ItemDetailTest extends PerformanceTestBase {

    private InventoryPage inventoryPage;
    private ItemDetailPage itemDetailPage;
    private static final String EXPECTED_ITEM_NAME = "Sauce Labs Backpack";
    private static final String EXPECTED_ITEM_PRICE = "$29.99";

    @BeforeMethod
    public void setupItemDetail() {
        // Login and get inventory page
        LoginPage loginPage = new LoginPage(driver);
        inventoryPage = loginPage.loginAs(getCurrentUser());
    }

    /**
     * Test item consistency when clicking each item
     */
    @Test(description = "Verify item details match selected inventory item")
    public void testItemDetailsConsistency() {
        // Store initial item details
        String expectedItemName = inventoryPage.getItemName(0);
        String expectedImageSrc = inventoryPage.getItemImageSrc(0);

        // Navigate to detail page
        inventoryPage.clickItemName(0);
        itemDetailPage = new ItemDetailPage(driver);

        // Verify details match
        Assert.assertTrue(itemDetailPage.isOnItemDetailPage(),
                "Should be on item detail page");
        Assert.assertEquals(itemDetailPage.getItemName(), expectedItemName,
                "Detail page should show the same item name as inventory");
        Assert.assertEquals(itemDetailPage.getItemImageSrc(), expectedImageSrc,
                "Detail page should show the same image as inventory");
    }

    /**
     * Test consistency across multiple items
     */
    @Test(description = "Verify consistency across multiple item selections")
    public void testMultipleItemConsistency() {
        List<String> allItemNames = inventoryPage.getItemNames();

        // Test first 3 items or all if less than 3
        int itemsToTest = Math.min(3, allItemNames.size());

        for (int i = 0; i < itemsToTest; i++) {
            // Store expected details
            String expectedName = inventoryPage.getItemName(i);
            String expectedImageSrc = inventoryPage.getItemImageSrc(i);

            // Navigate to detail page
            inventoryPage.clickItemName(i);
            itemDetailPage = new ItemDetailPage(driver);

            // Verify details
            Assert.assertTrue(itemDetailPage.isOnItemDetailPage(),
                    "Should be on item detail page for item " + i);
            Assert.assertEquals(itemDetailPage.getItemName(), expectedName,
                    "Detail page should show correct item name for item " + i);
            Assert.assertTrue(itemDetailPage.isImageMatchingItem(expectedImageSrc),
                    "Detail page should show correct image for item " + i);

            // Return to inventory for next iteration
            itemDetailPage.clickBackToProducts();
            Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                    "Should return to inventory page successfully");
        }
    }

    /**
     * Test item image consistency
     */
    @Test(description = "Verify item images match between inventory and detail pages")
    public void testItemImageConsistency() {
        String inventoryImageSrc = inventoryPage.getItemImageSrc(0);

        inventoryPage.clickItemName(0);
        itemDetailPage = new ItemDetailPage(driver);

        Assert.assertTrue(itemDetailPage.isImageDisplayed(),
                "Item image should be displayed on detail page");
        Assert.assertTrue(itemDetailPage.isImageMatchingItem(inventoryImageSrc),
                "Detail page should show the same image as inventory");
    }

    /**
     * Test for visual glitches when rapidly switching items
     */
    @Test(description = "Verify item details remain consistent when rapidly switching items")
    public void testRapidItemSwitching() {
        // Store expected details for first three items
        String[] expectedNames = new String[3];
        String[] expectedImages = new String[3];

        for (int i = 0; i < 3; i++) {
            expectedNames[i] = inventoryPage.getItemName(i);
            expectedImages[i] = inventoryPage.getItemImageSrc(i);
        }

        // Rapid switching between items
        for (int i = 0; i < 3; i++) {
            inventoryPage.clickItemName(i);
            itemDetailPage = new ItemDetailPage(driver);

            Assert.assertEquals(itemDetailPage.getItemName(), expectedNames[i],
                    "Item " + i + " should show correct name");
            Assert.assertTrue(itemDetailPage.isImageMatchingItem(expectedImages[i]),
                    "Item " + i + " should show correct image");

            if (i < 2) {
                itemDetailPage.clickBackToProducts();
            }
        }
    }

    @Test(description = "User can add item to cart from detail page")
    public void testAddItemToCart() {
        inventoryPage.clickItemName(0);
        itemDetailPage = new ItemDetailPage(driver);

        int initialCartCount = itemDetailPage.getCartCount();
        itemDetailPage.clickAddToCart();

        Assert.assertTrue(itemDetailPage.isItemAddedToCart(),
                "Item should be added to cart");
        Assert.assertEquals(itemDetailPage.getCartCount(), initialCartCount + 1,
                "Cart count should increase by 1");
    }

    @Test(description = "User can remove item from cart in detail page")
    public void testRemoveItemFromCart() {
        inventoryPage.clickItemName(0);
        itemDetailPage = new ItemDetailPage(driver);

        itemDetailPage.clickAddToCart();
        int cartCountAfterAdd = itemDetailPage.getCartCount();

        Assert.assertTrue(itemDetailPage.isItemAddedToCart(),
                "Item should be added to cart");

        itemDetailPage.removeFromCart();

        Assert.assertFalse(itemDetailPage.isItemAddedToCart(),
                "Item should be removed from cart");
        Assert.assertEquals(itemDetailPage.getCartCount(), cartCountAfterAdd - 1,
                "Cart count should decrease by 1");
    }

    @Test(description = "Verify item details are displayed correctly")
    public void testItemDetailsDisplay() {
        inventoryPage.clickItemName(0);
        itemDetailPage = new ItemDetailPage(driver);

        Assert.assertEquals(itemDetailPage.getItemName(), EXPECTED_ITEM_NAME,
                "Should display correct item name");
        Assert.assertEquals(itemDetailPage.getItemPrice(), EXPECTED_ITEM_PRICE,
                "Should display correct price");
        Assert.assertFalse(itemDetailPage.getItemDescription().isEmpty(),
                "Should display item description");
        Assert.assertTrue(itemDetailPage.isImageDisplayed(),
                "Should display item image");
    }

    @Test(description = "User can navigate back to products page")
    public void testNavigateBackToProducts() {
        inventoryPage.clickItemName(0);
        itemDetailPage = new ItemDetailPage(driver);

        itemDetailPage.clickBackToProducts();
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should return to inventory page");
    }
}