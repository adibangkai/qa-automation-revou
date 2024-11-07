Feature: Shopping Cart Functionality
  As a user of the Sauce Demo site
  I want to be able to manage items in my cart
  So that I can prepare for checkout

  Scenario Outline: Add single item to cart
    Given I am logged in as "<userType>" user
    When I store the name of item 0
    And I add item 0 to the cart
    Then the cart count should be 1
    When I click on the cart icon
    Then I should be on the cart page
    And the cart should show 1 item
    And the cart should contain the stored item name

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |

  Scenario Outline: Add multiple items to cart
    Given I am logged in as "<userType>" user
    When I store the names of items 0 and 1
    And I add item 0 to the cart
    And I add item 1 to the cart
    Then the cart count should be 2
    When I click on the cart icon
    Then I should be on the cart page
    And the cart should show 2 items
    And the cart should contain both stored item names

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |

  Scenario Outline: Remove single item from cart
    Given I am logged in as "<userType>" user
    And I store the name of item 0
    And I add item 0 to the cart
    And I click on the cart icon
    When I remove item 0 from the cart
    Then the decreased cart should show 0 items
#    And the removed item should not be in cart

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |

  Scenario Outline: Remove item from multiple items in cart
    Given I am logged in as "<userType>" user
    And I store the names of items 0 and 1
    And I add item 0 to the cart
    And I add item 1 to the cart
    And I click on the cart icon
    When I remove item 0 from the cart
    Then the decreased cart should show 1 items
#    And the first item should be removed from cart
#    And the second item should still be in cart

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |

  Scenario Outline: Navigate through cart
    Given I am logged in as "<userType>" user
    And I add item 0 to the cart
    And I click on the cart icon
    When I click continue shopping
    Then I should be on the inventory page
    When I click on the cart icon
    And I click checkout
    Then I should be on checkout step one page

    Examples:
      | userType      |
      | STANDARD_USER |
      | LOCKED_OUT_USER |
      | PROBLEM_USER |
      | PERFORMANCE_GLITCH_USER |
      | ERROR_USER |