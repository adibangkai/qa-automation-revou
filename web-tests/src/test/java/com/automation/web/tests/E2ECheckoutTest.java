package com.automation.web.tests;

import com.automation.web.pages.*;
import com.automation.web.enums.UserType;
import com.automation.web.data.TestDataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class E2ECheckoutTest extends BaseTest {

    @Test(dataProvider = "checkoutUsers",
            dataProviderClass = TestDataProvider.class,
            description = "Complete checkout flow test")
    public void testCompleteCheckoutFlow(UserType userType, String firstName,
                                         String lastName, String zipCode) {
        // 1. Login
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(getCurrentUser());

        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should land on inventory page after login");

        // 2. Add items to cart
        inventoryPage.addItemToCart(0);  // Add first item
        inventoryPage.addItemToCart(1);  // Add second item
        Assert.assertEquals(inventoryPage.getCartItemCount(), 2,
                "Should have 2 items in cart");

        // 3. Go to cart
        inventoryPage.clickCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should be on cart page");

        // 4. Start checkout
        cartPage.proceedToCheckout();
        CheckoutStepOnePage checkoutStepOne = new CheckoutStepOnePage(driver);

        // 5. Fill checkout information
        checkoutStepOne.fillForm(firstName, lastName, zipCode);
        checkoutStepOne.clickContinue();

        // 6. Verify checkout overview
        CheckoutStepTwoPage checkoutStepTwo = new CheckoutStepTwoPage(driver);
        Assert.assertTrue(checkoutStepTwo.isOnCheckoutStepTwoPage(),
                "Should be on checkout step two");
//        Assert.assertEquals(checkoutStepTwo.getCartItemCount(), 2,
//                "Should still have 2 items");

        // 7. Complete checkout
        checkoutStepTwo.clickFinish();

        // 8. Verify success
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"),
                "Should be on checkout complete page");
    }
}