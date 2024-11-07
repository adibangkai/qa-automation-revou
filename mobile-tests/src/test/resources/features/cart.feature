Feature: Cart

  Background:
    Given user is on catalog screen

  Scenario: Empty Cart
    When user opens the cart
    Then user should see No items

  Scenario: Go Shopping Button
    When user opens the cart
    Then user should see No items
    When user clicks Go Shopping
    Then user should be redirected to catalog screen

  Scenario: Cart Items
    Given user adds an item to cart
    When user opens the cart
    Then user should see the added item

  Scenario: Remove Item from Cart
    Given user adds an item to cart
    When user opens the cart
    And user clicks Remove item
    Then item is no longer in the cart