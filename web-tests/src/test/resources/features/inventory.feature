Feature: Inventory Page Functionality
  As a user of the Sauce Demo site
  I want to be able to interact with the inventory page
  So that I can manage items in my cart

  Scenario Outline: Add item to cart
    Given I am logged in as "<userType>" user
    And my shopping cart is empty
    When I add item 0 to the cart
    Then the cart count should be 1
    And the Remove button should be displayed for item 0

    Examples:
      | userType      |
      | STANDARD_USER |

  Scenario Outline: Remove item from cart
    Given I am logged in as "<userType>" user
    And I have added item 0 to the cart
    When I remove item 0 from the cart
    Then the cart count should be 0
    And the Add to Cart button should be displayed for item 0

    Examples:
      | userType      |
      | STANDARD_USER |

  Scenario Outline: Navigate to cart
    Given I am logged in as "<userType>" user
    And I have added item 0 to the cart
    When I click on the cart icon
    Then I should be on the cart page

    Examples:
      | userType      |
      | STANDARD_USER |