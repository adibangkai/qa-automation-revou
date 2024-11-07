Feature: Checkout Functionality
  As a user of the Sauce Demo site
  I want to be able to complete the checkout process
  So that I can purchase my items

  Scenario Outline: Complete checkout process
    Given I am logged in as "<userType>" user
    And I add item 0 to the cart
    And I click on the cart icon
    And I click checkout
    When I fill the form with:
      | firstName | lastName | postalCode |
      | Bob     | Dylan     | 12345      |
    And I click continue
    Then I should be on checkout step two page
    When I click finish
    Then I should be on checkout complete page

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |

  Scenario Outline: Verify checkout validation
    Given I am logged in as "<userType>" user
    And I add item 0 to the cart
    And I click on the cart icon
    And I click checkout
    When I click continue with empty fields
    Then I should see an error message
    When I enter "Test" in first name field
    And I click continue
    Then I should see an error message for missing fields

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |

  Scenario Outline: Navigate back from checkout
    Given I am logged in as "<userType>" user
    And I add item 0 to the cart
    And I click on the cart icon
    And I click checkout
    When I click cancel on step one
    Then I should be on the cart page
    When I click checkout
    And I fill the form with:
      | firstName | lastName | postalCode |
      | Bob     | Dylan| 12345      |
    And I click continue
    And I click cancel on step two
    Then I should be on the inventory page

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |