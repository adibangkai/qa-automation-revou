package com.automation.web.tests;

import com.automation.web.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

public class VisualLayoutTest extends BaseTest {

    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        LoginPage loginPage = new LoginPage(driver);
        inventoryPage = loginPage.loginAs(getCurrentUser());
        cartPage = new CartPage(driver);
    }

    /**
     * Test shopping cart icon positioning on inventory page
     */
    @Test(description = "Verify shopping cart icon position on inventory page")
    public void testShoppingCartIconPosition() {
        // Get shopping cart icon position and header container
        Point cartIconPosition = inventoryPage.getShoppingCartPosition();
        Rectangle headerBounds = inventoryPage.getHeaderBounds();

        // Verify cart icon is properly aligned within header
        Assert.assertTrue(cartIconPosition.getX() > 0,
                "Cart icon should not be at far left");
        Assert.assertTrue(cartIconPosition.getX() < headerBounds.getWidth(),
                "Cart icon should be within header bounds");

        // Verify vertical alignment
        int expectedTopMargin = 10; // Standard margin from top
        Assert.assertEquals(cartIconPosition.getY(), expectedTopMargin,
                "Cart icon should have correct top margin");

        // Verify right alignment
        int expectedRightMargin = 24; // Standard margin from right
        int expectedX = headerBounds.getWidth() - expectedRightMargin;
        Assert.assertEquals(cartIconPosition.getX(), expectedX,
                "Cart icon should be correctly aligned to the right");
    }

    /**
     * Test checkout button positioning on cart page
     */
    @Test(description = "Verify checkout button position on cart page")
    public void testCheckoutButtonPosition() {
        // Add item and go to cart
        inventoryPage.addItemToCart(0);
        inventoryPage.clickCart();

        // Get positions
        Point checkoutButtonPosition = cartPage.getCheckoutButtonPosition();
        Point continueShoppingButtonPosition = cartPage.getContinueShoppingButtonPosition();
        Rectangle cartContainerBounds = cartPage.getCartContainerBounds();

        // Verify horizontal alignment
        Assert.assertTrue(checkoutButtonPosition.getX() > continueShoppingButtonPosition.getX(),
                "Checkout button should be to the right of Continue Shopping button");

        // Verify vertical alignment
        Assert.assertEquals(checkoutButtonPosition.getY(), continueShoppingButtonPosition.getY(),
                "Buttons should be vertically aligned");

        // Verify proper spacing
        int buttonSpacing = checkoutButtonPosition.getX() -
                (continueShoppingButtonPosition.getX() + cartPage.getContinueShoppingButtonWidth());
        int expectedSpacing = 840; // Standard spacing between buttons
        Assert.assertEquals(buttonSpacing, expectedSpacing,
                "Buttons should have correct spacing");

        // Verify bottom positioning
        int expectedBottomMargin = 24; // Standard bottom margin
        int actualBottomMargin = cartContainerBounds.getHeight() -
                (checkoutButtonPosition.getY() + cartPage.getCheckoutButtonHeight());
        Assert.assertEquals(actualBottomMargin, expectedBottomMargin,
                "Checkout button should have correct bottom margin");
    }

    /**
     * Test visual alignment of cart items
     */
    @Test(description = "Verify cart items visual alignment")
    public void testCartItemsAlignment() {
        // Add items and go to cart
        inventoryPage.addItemToCart(0);
        inventoryPage.addItemToCart(1);
        inventoryPage.clickCart();

        // Verify item alignment
        Assert.assertTrue(cartPage.areItemsProperlyAligned(),
                "Cart items should be properly aligned vertically");

        // Verify consistent spacing
        Assert.assertTrue(cartPage.hasConsistentItemSpacing(),
                "Cart items should have consistent spacing");

        // Verify price alignment
        Assert.assertTrue(cartPage.arePricesProperlyAligned(),
                "Item prices should be properly aligned");

        // Verify quantity alignment
        Assert.assertTrue(cartPage.areQuantitiesProperlyAligned(),
                "Item quantities should be properly aligned");
    }
}