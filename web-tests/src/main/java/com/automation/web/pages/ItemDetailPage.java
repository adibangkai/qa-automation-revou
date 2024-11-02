package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemDetailPage extends BasePage {

    @FindBy(css = ".inventory_details_name")
    private WebElement itemName;

    @FindBy(css = ".inventory_details_desc")
    private WebElement itemDescription;

    @FindBy(css = ".inventory_details_price")
    private WebElement itemPrice;

    @FindBy(css = ".inventory_details_container button")
    private WebElement addRemoveButton;

    @FindBy(css = ".left_component")
    private WebElement backToProductsButton;

    @FindBy(css = ".inventory_details_img")
    private WebElement itemImage;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    public ItemDetailPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the displayed item name
     */
    public String getItemName() {
        return getText(itemName);
    }

    /**
     * Gets the item description
     */
    public String getItemDescription() {
        return getText(itemDescription);
    }

    /**
     * Gets the displayed item price
     */
    public String getItemPrice() {
        return getText(itemPrice);
    }

    /**
     * Gets the image source URL
     */
    public String getItemImageSrc() {
        return itemImage.getAttribute("src");
    }

    /**
     * Checks if the item image is properly displayed
     */
    public boolean isImageDisplayed() {
        try {
            return isDisplayed(itemImage) &&
                    !itemImage.getAttribute("src").contains("sl-404") &&
                    itemImage.getAttribute("src") != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks the Add to Cart button if available
     */
    public void clickAddToCart() {
        if (getAddRemoveButtonText().equals("Add to cart")) {
            click(addRemoveButton);
        }
    }

    /**
     * Clicks the Remove button if available
     */
    public void removeFromCart() {
        if (getAddRemoveButtonText().equals("Remove")) {
            click(addRemoveButton);
        }
    }

    /**
     * Gets the text of the Add/Remove button
     */
    public String getAddRemoveButtonText() {
        return getText(addRemoveButton);
    }

    /**
     * Returns to the products page
     */
    public void clickBackToProducts() {
        click(backToProductsButton);
    }

    /**
     * Checks if currently on the item detail page
     */
    public boolean isOnItemDetailPage() {
        return isDisplayed(itemImage) &&
                isDisplayed(itemName) &&
                isDisplayed(itemDescription) &&
                isDisplayed(itemPrice);
    }

    /**
     * Checks if the item is currently in the cart
     */
    public boolean isItemAddedToCart() {
        try {
            return getAddRemoveButtonText().equals("Remove");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the current cart count
     */
    public int getCartCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Checks if the item image matches the expected image
     */
    public boolean isImageMatchingItem(String expectedImageSrc) {
        String currentImageSrc = getItemImageSrc();
        return currentImageSrc != null &&
                currentImageSrc.equals(expectedImageSrc) &&
                !currentImageSrc.contains("sl-404");
    }
}
