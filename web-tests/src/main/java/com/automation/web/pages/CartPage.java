package com.automation.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.Point;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = ".cart_item .cart_button")
    private List<WebElement> removeButtons;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(className = "cart_quantity")
    private List<WebElement> itemQuantities;

    @FindBy(css = "#cart_contents_container")
    private WebElement cartContainer;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnCartPage() {
        return getTitle().equals("Your Cart");
    }

    public String getTitle() {
        return getText(driver.findElement(By.className("title")));
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public void removeItem(int index) {
        if (index >= 0 && index < removeButtons.size()) {
            click(removeButtons.get(index));
        }
    }

    public void continueShopping() {
        click(continueShoppingButton);
    }

    public void proceedToCheckout() {
        click(checkoutButton);
    }

    public String getItemName(int index) {
        if (index >= 0 && index < itemNames.size()) {
            return getText(itemNames.get(index));
        }
        return null;
    }

    public List<String> getItemNames() {
        List<String> itemNames = new ArrayList<>();
        for (int i = 0; i < this.itemNames.size(); i++) {
            String itemName = getItemName(i);
            if (itemName != null) {
                itemNames.add(itemName);
            }
        }
        return itemNames;
    }

    /**
     * Get the position of the checkout button
     */
    public Point getCheckoutButtonPosition() {
        return checkoutButton.getLocation();
    }

    /**
     * Get the height of the checkout button
     */
    public int getCheckoutButtonHeight() {
        return checkoutButton.getSize().getHeight();
    }

    /**
     * Get the position of the continue shopping button
     */
    public Point getContinueShoppingButtonPosition() {
        return continueShoppingButton.getLocation();
    }

    /**
     * Get the width of the continue shopping button
     */
    public int getContinueShoppingButtonWidth() {
        return continueShoppingButton.getSize().getWidth();
    }

    /**
     * Get the bounds of the cart container
     */
    public Rectangle getCartContainerBounds() {
        return cartContainer.getRect();
    }

    /**
     * Check if cart items are properly aligned vertically
     */
    public boolean areItemsProperlyAligned() {
        int expectedX = cartItems.get(0).getLocation().getX();
        return cartItems.stream()
                .allMatch(item -> item.getLocation().getX() == expectedX);
    }

    /**
     * Check if cart items have consistent spacing
     */
    public boolean hasConsistentItemSpacing() {
        if (cartItems.size() < 2) return true;

        int expectedSpacing = 16; // Standard spacing between items
        int prevY = cartItems.get(0).getLocation().getY();

        for (int i = 1; i < cartItems.size(); i++) {
            int currentY = cartItems.get(i).getLocation().getY();
            int spacing = currentY - (prevY + cartItems.get(i-1).getSize().getHeight());
            if (spacing != expectedSpacing) return false;
            prevY = currentY;
        }
        return true;
    }

    /**
     * Check if item prices are properly aligned
     */
    public boolean arePricesProperlyAligned() {
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        if (prices.isEmpty()) return true;

        int expectedX = prices.get(0).getLocation().getX();
        return prices.stream()
                .allMatch(price -> price.getLocation().getX() == expectedX);
    }

    /**
     * Check if item quantities are properly aligned
     */
    public boolean areQuantitiesProperlyAligned() {
        if (itemQuantities.isEmpty()) return true;

        int expectedX = itemQuantities.get(0).getLocation().getX();
        return itemQuantities.stream()
                .allMatch(qty -> qty.getLocation().getX() == expectedX);
    }

    public boolean isItemInCart(String itemName) {
        return itemNames.stream()
                .anyMatch(element -> getText(element).equals(itemName));
    }
}