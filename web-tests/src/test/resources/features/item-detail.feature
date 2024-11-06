Feature: Item Detail Page Functionality
  As a user of the Sauce Demo site
  I want to be able to view and interact with item details
  So that I can learn more about products and manage my cart

  Scenario Outline: Verify item details consistency
    Given I am logged in as "<userType>" user
    When I store details of item 0
    And I click on item 0
    Then I should be on the item detail page
    And the item details should match the inventory

    Examples:
      | userType      |
      | STANDARD_USER |



  Scenario Outline: Verify item image consistency
    Given I am logged in as "<userType>" user
    When I store the image source of item 0
    And I click on item 0
    Then the detail page image should match the inventory image

    Examples:
      | userType      |
      | STANDARD_USER |


  Scenario Outline: Add item to cart from detail page
    Given I am logged in as "<userType>" user
    And I am viewing item 0
    When I click Add to Cart button on detail page
    Then the item should be added to cart
    And the cart count should increase by 1

    Examples:
      | userType      |
      | STANDARD_USER |

  Scenario Outline: Remove item from cart in detail page
    Given I am logged in as "<userType>" user
    And I am viewing item 0
    And I have added the item to cart from detail page
    When I click Remove button on detail page
    Then the item should be removed from cart
    And the cart count should decrease by 1

    Examples:
      | userType      |
      | STANDARD_USER |

  Scenario Outline: Verify specific item details display
    Given I am logged in as "<userType>" user
    When I click on "Sauce Labs Backpack"
    Then the item name should be "Sauce Labs Backpack"
    And the item price should be "$29.99"
    And the item description should not be empty
    And the item image should be displayed

    Examples:
      | userType      |
      | STANDARD_USER |

  Scenario Outline: Navigate back to products page
    Given I am logged in as "<userType>" user
    And I am viewing item 0
    When I click back to products
    Then I should return to inventory page

    Examples:
      | userType      |
      | STANDARD_USER |