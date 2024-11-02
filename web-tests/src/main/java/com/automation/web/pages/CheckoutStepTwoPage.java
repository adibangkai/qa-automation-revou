package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class CheckoutStepTwoPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemNames;

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public void clickFinish() {
        click(finishButton);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    public String getSubtotal() {
        return getText(subtotalLabel).replace("Item total: $", "");
    }

    public String getTotal() {
        return getText(totalLabel).replace("Total: $", "");
    }

    public void clickItemName(int index) {
        if (index >= 0 && index < itemNames.size()) {
            click(itemNames.get(index));
        }
    }

    public boolean isOnCheckoutStepTwoPage() {
        return driver.getCurrentUrl().contains("checkout-step-two");
    }
}