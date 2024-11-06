Feature: Order Flow
  As a customer
  I want to add items to cart and complete checkout
  So that I can purchase products

  Scenario: Complete purchase flow with multiple items
    Given User is on the catalogs page
    When User selects product "Sauce Labs Backpack"
    And User selects color "black"
    And User sets quantity to "2"
    And User adds item to cart
    And User opens cart
    And User proceeds to checkout
    When User fills shipping information
      | fullName     | Rebecca Winter  |
      | addressLine1 | Mandorley 112  |
      | addressLine2 | Entrance 1     |
      | city        | Truro          |
      | state       | Cornwall       |
      | zipCode     | 89750          |
      | country     | United Kingdom |
    And User clicks proceed to payment
    Then User should see payment screen
    When User enters payment information
      | username     | rebecca.winter      |
      | cardType     | Visa               |
      | cardNumber   | 4111111111111111   |
      | expiryDate   | 12/25              |
      | securityCode | 123                |
    And User reviews order
    Then Payment should be successful
    And User should be on checkout complete page
    And Order confirmation details should be displayed
    And User notes the order number
    When User continues shopping
    Then User should see catalog page