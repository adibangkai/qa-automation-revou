package com.automation.web.cucumber.steps;

import com.automation.web.cucumber.TestContext;
import com.automation.web.pages.CartPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class InventorySteps extends BaseSteps {
    private CartPage cartPage;

    public InventorySteps(TestContext context) {
        super(context);
        this.inventoryPage = context.getInventoryPage();  // Get from context
    }

    @Given("my shopping cart is empty")
    public void myShoppingCartIsEmpty() {
        Assert.assertEquals(inventoryPage.getCartItemCount(), 0,
                "Cart should be empty initially");
    }


    @Given("I have added item {int} to the cart")
    public void iHaveAddedItemToCart(int itemIndex) {
        inventoryPage.addItemToCart(itemIndex);
    }

    @When("I add item {int} to the cart")
    public void iAddItemToCart(int itemIndex) {
        inventoryPage.addItemToCart(itemIndex);
    }

    @When("I remove item {int} from the cart")
    public void iRemoveItemFromCart(int itemIndex) {
        inventoryPage.removeItemFromCart(itemIndex);
    }

    @When("I click on the cart icon")
    public void iClickOnCartIcon() {
        inventoryPage.clickCart();
        cartPage = new CartPage(context.getDriver());
    }

    @Then("the cart count should be {int}")
    public void theCartCountShouldBe(int expectedCount) {
        Assert.assertEquals(inventoryPage.getCartItemCount(), expectedCount,
                String.format("Cart should show %d item(s)", expectedCount));
    }

    @Then("the Remove button should be displayed for item {int}")
    public void theRemoveButtonShouldBeDisplayedForItem(int itemIndex) {
        Assert.assertTrue(inventoryPage.isRemoveButtonDisplayed(itemIndex),
                "Remove button should be displayed after adding item");
    }

    @Then("the Add to Cart button should be displayed for item {int}")
    public void theAddToCartButtonShouldBeDisplayedForItem(int itemIndex) {
        Assert.assertTrue(inventoryPage.isAddToCartButtonDisplayed(itemIndex),
                "Add to Cart button should be displayed after removing item");
    }


}