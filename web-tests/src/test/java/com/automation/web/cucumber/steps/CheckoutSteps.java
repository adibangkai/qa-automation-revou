package com.automation.web.cucumber.steps;

import com.automation.web.cucumber.TestContext;
import com.automation.web.pages.CheckoutStepOnePage;
import com.automation.web.pages.CheckoutStepTwoPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.testng.Assert;
import java.util.List;
import java.util.Map;

public class CheckoutSteps extends BaseSteps {
    private CheckoutStepOnePage checkoutOne;
    private CheckoutStepTwoPage checkoutTwo;

    public CheckoutSteps(TestContext context) {
        super(context);
        this.checkoutOne = new CheckoutStepOnePage(context.getDriver());
    }

    @When("I fill the form with:")
    public void iFillTheFormWith(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> form = data.get(0);
        checkoutOne.fillForm(
                form.get("firstName"),
                form.get("lastName"),
                form.get("postalCode")
        );
    }

    @When("I click continue")
    public void iClickContinue() {
        checkoutOne.clickContinue();
        checkoutTwo = new CheckoutStepTwoPage(context.getDriver());
    }

    @When("I click continue with empty fields")
    public void iClickContinueWithEmptyFields() {
        checkoutOne.clickContinue();
    }

    @When("I enter {string} in first name field")
    public void iEnterInFirstNameField(String firstName) {
        checkoutOne.enterFirstName(firstName);
    }

    @When("I click cancel on step one")
    public void iClickCancelOnStepOne() {
        checkoutOne.clickCancel();
    }

    @When("I click cancel on step two")
    public void iClickCancelOnStepTwo() {
        checkoutTwo.clickCancel();
    }

    @When("I click finish")
    public void iClickFinish() {
        checkoutTwo.clickFinish();
    }

    @Then("I should be on checkout step two page")
    public void iShouldBeOnCheckoutStepTwoPage() {
        Assert.assertTrue(checkoutTwo.isOnCheckoutStepTwoPage(),
                "Should be on checkout step two page");
    }

    @Then("I should be on checkout complete page")
    public void iShouldBeOnCheckoutCompletePage() {
        Assert.assertTrue(context.getDriver().getCurrentUrl().contains("checkout-complete"),
                "Should be on checkout complete page");
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        Assert.assertTrue(checkoutOne.isErrorDisplayed(),
                "Error should be displayed");
    }

    @Then("I should see an error message for missing fields")
    public void iShouldSeeAnErrorMessageForMissingFields() {
        Assert.assertTrue(checkoutOne.isErrorDisplayed(),
                "Error should be displayed for missing fields");
    }
}
