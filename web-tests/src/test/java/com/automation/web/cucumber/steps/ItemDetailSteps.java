package com.automation.web.cucumber.steps;

import com.automation.web.cucumber.TestContext;
import com.automation.web.pages.ItemDetailPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import java.util.List;

public class ItemDetailSteps extends BaseSteps {
    private ItemDetailPage itemDetailPage;
    private String expectedItemName;
    private String expectedImageSrc;
    private String[] expectedNames;
    private String[] expectedImages;
    private int initialCartCount;

    public ItemDetailSteps(TestContext context) {
        super(context);
    }

    @Given("I am viewing item {int}")
    public void iAmViewingItem(int itemIndex) {
        inventoryPage.clickItemName(itemIndex);
        itemDetailPage = new ItemDetailPage(context.getDriver());
    }

    @When("I store details of item {int}")
    public void iStoreDetailsOfItem(int itemIndex) {
        expectedItemName = inventoryPage.getItemName(itemIndex);
        expectedImageSrc = inventoryPage.getItemImageSrc(itemIndex);
    }

    @When("I click on item {int}")
    public void iClickOnItem(int itemIndex) {
        inventoryPage.clickItemName(itemIndex);
        itemDetailPage = new ItemDetailPage(context.getDriver());
    }

    @When("I check the first 3 items")
    public void iCheckTheFirstThreeItems() {
        List<String> allItemNames = inventoryPage.getItemNames();
        int itemsToTest = Math.min(3, allItemNames.size());
        expectedNames = new String[itemsToTest];
        expectedImages = new String[itemsToTest];

        for (int i = 0; i < itemsToTest; i++) {
            expectedNames[i] = inventoryPage.getItemName(i);
            expectedImages[i] = inventoryPage.getItemImageSrc(i);
        }
    }

    @When("I store the image source of item {int}")
    public void iStoreTheImageSourceOfItem(int itemIndex) {
        expectedImageSrc = inventoryPage.getItemImageSrc(itemIndex);
    }

    @When("I store details of first three items")
    public void iStoreDetailsOfFirstThreeItems() {
        expectedNames = new String[3];
        expectedImages = new String[3];
        for (int i = 0; i < 3; i++) {
            expectedNames[i] = inventoryPage.getItemName(i);
            expectedImages[i] = inventoryPage.getItemImageSrc(i);
        }
    }

    @When("I rapidly switch between these items")
    public void iRapidlySwitchBetweenTheseItems() {
        for (int i = 0; i < 3; i++) {
            inventoryPage.clickItemName(i);
            itemDetailPage = new ItemDetailPage(context.getDriver());
            Assert.assertEquals(itemDetailPage.getItemName(), expectedNames[i],
                    "Item " + i + " should show correct name");
            Assert.assertTrue(itemDetailPage.isImageMatchingItem(expectedImages[i]),
                    "Item " + i + " should show correct image");

            if (i < 2) {
                itemDetailPage.clickBackToProducts();
            }
        }
    }

    @When("I click Add to Cart button on detail page")
    public void iClickAddToCartButtonOnDetailPage() {
        initialCartCount = itemDetailPage.getCartCount();
        itemDetailPage.clickAddToCart();
    }

    @When("I click Remove button on detail page")
    public void iClickRemoveButtonOnDetailPage() {
        itemDetailPage.removeFromCart();
    }

    @When("I click on {string}")
    public void iClickOnItem(String itemName) {
        inventoryPage.clickItemName(0); // Assuming first item is Backpack
        itemDetailPage = new ItemDetailPage(context.getDriver());
    }

    @When("I click back to products")
    public void iClickBackToProducts() {
        itemDetailPage.clickBackToProducts();
    }

    @Then("I should be on the item detail page")
    public void iShouldBeOnTheItemDetailPage() {
        Assert.assertTrue(itemDetailPage.isOnItemDetailPage(),
                "Should be on item detail page");
    }

    @Then("the item details should match the inventory")
    public void theItemDetailsShouldMatchTheInventory() {
        Assert.assertEquals(itemDetailPage.getItemName(), expectedItemName,
                "Detail page should show the same item name as inventory");
        Assert.assertEquals(itemDetailPage.getItemImageSrc(), expectedImageSrc,
                "Detail page should show the same image as inventory");
    }

    @Then("all items should show consistent details")
    public void allItemsShouldShowConsistentDetails() {
        for (int i = 0; i < expectedNames.length; i++) {
            inventoryPage.clickItemName(i);
            itemDetailPage = new ItemDetailPage(context.getDriver());

            Assert.assertTrue(itemDetailPage.isOnItemDetailPage(),
                    "Should be on item detail page for item " + i);
            Assert.assertEquals(itemDetailPage.getItemName(), expectedNames[i],
                    "Detail page should show correct item name for item " + i);
            Assert.assertTrue(itemDetailPage.isImageMatchingItem(expectedImages[i]),
                    "Detail page should show correct image for item " + i);

            itemDetailPage.clickBackToProducts();
        }
    }

    @Then("the detail page image should match the inventory image")
    public void theDetailPageImageShouldMatchTheInventoryImage() {
        Assert.assertTrue(itemDetailPage.isImageDisplayed(),
                "Item image should be displayed on detail page");
        Assert.assertTrue(itemDetailPage.isImageMatchingItem(expectedImageSrc),
                "Detail page should show the same image as inventory");
    }

    @Then("the item should be added to cart")
    public void theItemShouldBeAddedToCart() {
        Assert.assertTrue(itemDetailPage.isItemAddedToCart(),
                "Item should be added to cart");
    }

    @Then("the cart count should increase by 1")
    public void theCartCountShouldIncreaseByOne() {
        Assert.assertEquals(itemDetailPage.getCartCount(), initialCartCount + 1,
                "Cart count should increase by 1");
    }

    @Then("the item should be removed from cart")
    public void theItemShouldBeRemovedFromCart() {
        Assert.assertFalse(itemDetailPage.isItemAddedToCart(),
                "Item should be removed from cart");
    }

    @Then("the cart count should decrease by 1")
    public void theCartCountShouldDecreaseByOne() {
        int currentCartCount = itemDetailPage.getCartCount();
        Assert.assertEquals(currentCartCount, initialCartCount - 1,
                "Cart count should decrease by 1");
    }


    @Then("the item name should be {string}")
    public void theItemNameShouldBe(String expectedName) {
        Assert.assertEquals(itemDetailPage.getItemName(), expectedName,
                "Should display correct item name");
    }

    @Then("the item price should be {string}")
    public void theItemPriceShouldBe(String expectedPrice) {
        Assert.assertEquals(itemDetailPage.getItemPrice(), expectedPrice,
                "Should display correct price");
    }

    @Then("the item description should not be empty")
    public void theItemDescriptionShouldNotBeEmpty() {
        Assert.assertFalse(itemDetailPage.getItemDescription().isEmpty(),
                "Should display item description");
    }

    @Then("the item image should be displayed")
    public void theItemImageShouldBeDisplayed() {
        Assert.assertTrue(itemDetailPage.isImageDisplayed(),
                "Should display item image");
    }

    @Then("I should return to inventory page")
    public void iShouldReturnToInventoryPage() {
        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should return to inventory page");
    }

    @Given("I have added the item to cart from detail page")
    public void iHaveAddedTheItemToCartFromDetailPage() {
        initialCartCount = itemDetailPage.getCartCount();  // Store count before adding
        itemDetailPage.clickAddToCart();
        Assert.assertTrue(itemDetailPage.isItemAddedToCart(),
                "Item should be added to cart");
        initialCartCount = itemDetailPage.getCartCount();  // Update count after adding
    }

}