package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(css = ".inventory_item_img img")
    private List<WebElement> itemImages;

    @FindBy(css = ".inventory_item_description .btn_inventory")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".btn_inventory:contains('Remove')")
    private List<WebElement> removeButtons;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".bm-burger-button")
    private WebElement menuButton;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetLink;


    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Verifies if user is on the inventory page
     */
    public boolean isOnInventoryPage() {
        return getText(pageTitle).equals("Products");
    }

    /**
     * Opens the menu sidebar
     */
    public void openMenu() {
        click(menuButton);
        // Add a small wait to ensure menu animation completes
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Clicks the About link in the menu
     */
    public void clickAboutLink() {
        click(aboutLink);
    }

    /**
     * Clicks the Logout link in the menu
     */
    public void clickLogout() {
        click(logoutLink);
    }

    /**
     * Clicks the Reset App State link in the menu
     */
    public void clickReset() {
        click(resetLink);
    }

    /**
     * Resets the application state through the menu
     */
    public void resetAppState() {
        openMenu();
        clickReset();
    }

    /**
     * Clicks on an item name by index
     */
    public void clickItemName(int index) {
        if (index >= 0 && index < itemNames.size()) {
            click(itemNames.get(index));
        }
    }

    /**
     * Gets the name of an item by index
     */
    public String getItemName(int index) {
        if (index >= 0 && index < itemNames.size()) {
            return getText(itemNames.get(index));
        }
        return null;
    }

    /**
     * Checks if images are displayed correctly
     */
    public boolean areImagesDisplayedCorrectly() {
        for (WebElement img : itemImages) {
            String srcAttribute = img.getAttribute("src");
            if (srcAttribute != null && srcAttribute.contains("sl-404")) {
                return false;
            }
        }
        boolean correctCount = itemImages.size() == 6;
        boolean allImagesDisplayed = itemImages.stream()
                .allMatch(WebElement::isDisplayed);
        return correctCount && allImagesDisplayed;
    }

    /**
     * Gets a list of all item names
     */
    public List<String> getItemNames() {
        return itemNames.stream()
                .map(this::getText)
                .collect(Collectors.toList());
    }

    /**
     * Gets a list of all item prices as doubles
     */
    public List<Double> getItemPrices() {
        return itemPrices.stream()
                .map(element -> getText(element).replace("$", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    /**
     * Adds an item to the cart by index if it's not already added
     */
    public void addItemToCart(int index) {
        if (index >= 0 && index < addToCartButtons.size() && isAddToCartButtonDisplayed(index)) {
            click(addToCartButtons.get(index));
        }
    }

    /**
     * Removes an item from the cart by index
     */
    public void removeItemFromCart(int index) {
        if (index >= 0 && index < addToCartButtons.size() && isRemoveButtonDisplayed(index)) {
            click(addToCartButtons.get(index));
        }
    }

    /**
     * Gets the number of items in the cart
     */
    public int getCartItemCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Checks if the cart badge is displayed
     */
    public boolean isCartBadgeDisplayed() {
        return isDisplayed(cartBadge);
    }

    /**
     * Clicks the shopping cart link
     */
    public void clickCart() {
        click(shoppingCartLink);
    }

    /**
     * Checks if the Add to Cart/Remove buttons are working correctly
     */
    public boolean areButtonsWorkingCorrectly() {
        try {
            if (!addToCartButtons.isEmpty()) {
                addItemToCart(0);
                return getCartItemCount() == 1;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the Remove button is displayed for a specific item
     */
    public boolean isRemoveButtonDisplayed(int index) {
        if (index >= 0 && index < addToCartButtons.size()) {
            String buttonText = getText(addToCartButtons.get(index));
            return buttonText.equals("Remove");
        }
        return false;
    }

    /**
     * Checks if the Add to Cart button is displayed for a specific item
     */
    public boolean isAddToCartButtonDisplayed(int index) {
        if (index >= 0 && index < addToCartButtons.size()) {
            String buttonText = getText(addToCartButtons.get(index));
            return buttonText.equals("Add to cart");
        }
        return false;
    }

    /**
     * Gets the text of the cart button for a specific item
     */
    public String getCartButtonText(int index) {
        if (index >= 0 && index < addToCartButtons.size()) {
            return getText(addToCartButtons.get(index));
        }
        return null;
    }
    /**
     * Gets the image source URL for an item
     * @param index The index of the item
     * @return The image source URL or null if invalid index
     */
    public String getItemImageSrc(int index) {
        if (index >= 0 && index < itemImages.size()) {
            return itemImages.get(index).getAttribute("src");
        }
        return null;
    }


    /**
     * Get the position of the shopping cart icon
     */
    public Point getShoppingCartPosition() {
        return shoppingCartLink.getLocation();
    }

    /**
     * Get the bounds of the header container
     */
    public Rectangle getHeaderBounds() {
        WebElement header = driver.findElement(By.id("header_container"));
        return header.getRect();
    }

    /**
     * Validates if an image source is valid
     */
    private boolean isImageSourceValid(WebElement img) {
        try {
            String src = img.getAttribute("src");
            return src != null && !src.contains("sl-404");
        } catch (Exception e) {
            return false;
        }
    }
}
