package com.automation.web.tests;

import com.automation.web.pages.*;
import com.automation.web.enums.UserType;
import org.testng.annotations.Test;
import org.testng.Assert;

public class StandardUserTest extends BaseTest {
//
//    @Test(description = "User can add and remove items from cart")
//    public void testAddRemoveItems() {
//        LoginPage loginPage = new LoginPage(driver);
//        InventoryPage inventoryPage = loginPage.loginAs(
//                UserType.STANDARD_USER.getUsername(),
//                UserType.VALID_PASSWORD
//        );
//
//        // Add items to cart
//        inventoryPage.addItemToCart(0);
//        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
//                "Cart should have 1 item");
//
//        // Remove item from cart
//        inventoryPage.addItemToCart(0); // This will remove the item
//        Assert.assertEquals(inventoryPage.getCartItemCount(), 0,
//                "Cart should be empty");
//    }
//
//    @Test(description = "User can navigate to about page")
//    public void testAboutNavigation() {
//        LoginPage loginPage = new LoginPage(driver);
//        InventoryPage inventoryPage = loginPage.loginAs(
//                UserType.STANDARD_USER.getUsername(),
//                UserType.VALID_PASSWORD
//        );
//
//        inventoryPage.openMenu();
//        inventoryPage.clickAboutLink();
//
//        Assert.assertTrue(driver.getCurrentUrl().contains("saucelabs.com"),
//                "Should navigate to Sauce Labs website");
//    }
//
//    @Test(description = "User can logout")
//    public void testLogout() {
//        LoginPage loginPage = new LoginPage(driver);
//        InventoryPage inventoryPage = loginPage.loginAs(
//                UserType.STANDARD_USER.getUsername(),
//                UserType.VALID_PASSWORD
//        );
//
//        inventoryPage.openMenu();
//        inventoryPage.clickLogout();
//
//        Assert.assertTrue(loginPage.isOnLoginPage(),
//                "Should return to login page");
//    }
//
//    @Test(description = "User can navigate to cart")
//    public void testCartNavigation() {
//        LoginPage loginPage = new LoginPage(driver);
//        InventoryPage inventoryPage = loginPage.loginAs(
//                UserType.STANDARD_USER.getUsername(),
//                UserType.VALID_PASSWORD
//        );
//
//        inventoryPage.addItemToCart(0);
//        inventoryPage.clickCart();
//
//        CartPage cartPage = new CartPage(driver);
//        Assert.assertTrue(cartPage.isOnCartPage(),
//                "Should navigate to cart page");
//    }
}