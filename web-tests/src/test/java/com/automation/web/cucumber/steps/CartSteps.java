package com.automation.web.cucumber.steps;

import com.automation.web.cucumber.TestContext;
import com.automation.web.pages.CartPage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.testng.Assert;

public class CartSteps extends BaseSteps {
    private CartPage cartPage;
    private String storedItemName;
    private String firstItemName;
    private String secondItemName;

    public CartSteps(TestContext context) {
        super(context);
        this.cartPage = new CartPage(context.getDriver());
    }

    @When("I store the name of item {int}")
    public void iStoreTheNameOfItem(int itemIndex) {
        storedItemName = inventoryPage.getItemName(itemIndex);
    }

    @When("I store the names of items {int} and {int}")
    public void iStoreTheNamesOfItems(int firstIndex, int secondIndex) {
        firstItemName = inventoryPage.getItemName(firstIndex);
        secondItemName = inventoryPage.getItemName(secondIndex);
    }

    @When("I click continue shopping")
    public void iClickContinueShopping() {
        cartPage.continueShopping();
    }

    @When("I click checkout")
    public void iClickCheckout() {
        cartPage.proceedToCheckout();
    }

    @Then("the cart should show {int} item(s)")
    public void theCartShouldShowItems(int expectedCount) {
        Assert.assertEquals(cartPage.getCartItemCount(), expectedCount,
                String.format("Cart should show %d item(s)", expectedCount));
    }


    @Then("the decreased cart should show {int} items")
    public void theDecreasedCartShouldShowIntItems(int expectedCount) {
        int currentCartCount = cartPage.getCartItemCount();
        Assert.assertEquals(currentCartCount, currentCartCount, String.format("Decreased Cart should show %d item(s)", expectedCount));
    }

    @Then("the cart should contain the stored item name")
    public void theCartShouldContainTheStoredItemName() {
        Assert.assertTrue(cartPage.isItemInCart(storedItemName),
                "Cart should contain the correct item");
    }

    @Then("the cart should contain both stored item names")
    public void theCartShouldContainBothStoredItemNames() {
        Assert.assertTrue(cartPage.isItemInCart(firstItemName),
                "First item should be in cart");
        Assert.assertTrue(cartPage.isItemInCart(secondItemName),
                "Second item should be in cart");
    }

    @Then("the removed item should not be in cart")
    public void theRemovedItemShouldNotBeInCart() {
        Assert.assertFalse(cartPage.isItemInCart(storedItemName),
                "Removed item should not be in cart");
    }

    @Then("the first item should be removed from cart")
    public void theFirstItemShouldBeRemovedFromCart() {
        Assert.assertFalse(cartPage.isItemInCart(firstItemName),
                "First item should be removed from cart");
    }

    @Then("the second item should still be in cart")
    public void theSecondItemShouldStillBeInCart() {
        Assert.assertTrue(cartPage.isItemInCart(secondItemName),
                "Second item should still be in cart");
    }

    @Then("I should be on checkout step one page")
    public void iShouldBeOnCheckoutStepOnePage() {
        Assert.assertTrue(context.getDriver().getCurrentUrl().contains("checkout-step-one"),
                "Should be on checkout step one page");
    }


}