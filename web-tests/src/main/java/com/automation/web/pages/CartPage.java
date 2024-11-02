package com.automation.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

    public boolean isItemInCart(String itemName) {
        return itemNames.stream()
                .anyMatch(element -> getText(element).equals(itemName));
    }
}