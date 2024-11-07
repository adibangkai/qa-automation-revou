Feature: Cart Functionality

  Background:
    Given user is on catalog screen
    And the app state is reset

  # Positive Scenarios

  Scenario: View Empty Cart Initially
    When user opens the cart
    Then user should see No items
    And cart should be empty

  Scenario: Navigate Back to Shopping from Empty Cart
    When user opens the cart
    And user clicks Go Shopping
    Then user should be redirected to catalog screen

  Scenario: Add Single Item to Cart
    Given user adds an item to cart
    When user opens the cart
    Then user should see the added item
    And cart icon should display a badge

  Scenario: Add Multiple Items to Cart
    Given user adds an item to cart
    And user is on catalog screen
    And user adds an item to cart
    When user opens the cart
    Then user should see the added item
    And cart icon should display a badge

  Scenario: Remove Item from Cart
    Given user adds an item to cart
    When user opens the cart
    And user clicks Remove item
    Then item is no longer in the cart
    And cart should be empty

  Scenario: Cart Persistence After App Restart
    Given user adds an item to cart
    When user opens the cart
    Then user should see the added item
    And the app state is reset
    And user opens the cart
    Then user should see the added item

  # Negative Scenarios

  Scenario: Add Item with Zero Quantity
    When user clicks on one of the product
    And quantity should display "1"
    When user clicks on minus button
    Then quantity should display "0"
    And add to cart button should be disabled

  Scenario: Add Same Item Multiple Times
    When user clicks on one of the product
    And quantity should display "1"
    When user clicks on plus button
    And quantity should display "2"
    When user clicks on add to cart button
    And user opens the cart
    Then user should see the added item
    And cart icon should display a badge

  Scenario: Cart State After Logout
    Given user adds an item to cart
    And user opens the cart
    Then user should see the added item
    When user is not logged in
    And user opens the cart
    Then user should see the added item