Feature: Checkout

  Background:
    Given user is not logged in
    Then user is on login screen
    And user logs in to the app
    And user is on catalog screen
  #
    Scenario: Checkout Without Login
      Given user is not logged in
      And user is on catalog screen
      Then user adds an item to cart
      And user is on cart screen
      Given user clicks proceed to checkout button
      Then user should be redirected to login screen

    Scenario: Checkout
      Given user adds an item to cart
      Then user is on cart screen
      When user clicks proceed to checkout button
      Then user should be redirected to checkout screen

  Scenario Outline: Checkout Payment Form
    Given the app state is reset
    And user adds an item to cart
    Then user is on cart screen
    When user clicks proceed to checkout button
    Then user should be redirected to checkout screen
    When user enters the following shipping details:
      | Full Name      | Bob Dylan     |
      | Address Line 1 | 4th avenue    |
      | Address Line 2 | Taylor        |
      | City           | Bandung       |
      | State          | Jabar Juara   |
      | Zip Code       | 15015         |
      | Country        | Konoha        |
    And user clicks on To Payment button
    Then user should be redirected to payment method
    When user enters the following payment details:
      | Full Name       | <Full Name>       |
      | Card Number     | <Card Number>     |
      | Expiration Date | <Expiration Date> |
      | Security Code   | <Security Code>   |
    And user clicks on Review Order button
    Then <Outcome>
    Examples:
      | Full Name | Card Number      | Expiration Date | Security Code | Outcome                                             |
      | Bob Dylan | 1111222233334444 | 01/25           | 999           | user should be redirected to order review           |
      |           | 1111222233334444 | 01/25           | 999           | user should get an error on "full name" field       |
      | Bob Dylan |                  | 01/25           | 999           | user should get an error on "card number" field     |
      | Bob Dylan | 1111222233334444 |                 | 999           | user should get an error on "expiration date" field |
      | Bob Dylan | 1111222233334444 | 01/25           |               | user should get an error on "security code" field   |
      | Bob Dylan | aaaassssddddffff | 01/25           | 999           | user should get an error on "card number" field     |
      | Bob Dylan | 1111222233334444 | 01/25           | 1             | user should get an error on "security code" field   |
      | Bob Dylan | 1111222233334444 | 01/25           | 999           | user should be redirected to order review           |
#
#  Scenario: Checkout Complete
#    Given the app state is reset
#    And user adds an item to cart
#    Then user is on cart screen
#    When user clicks proceed to checkout button
#    Then user should be redirected to checkout screen
#    When user enters the following shipping details:
#      | Full Name      | Bob Dylan     |
#      | Address Line 1 | 4th avenue    |
#      | Address Line 2 | Taylor        |
#      | City           | Bandung        |
#      | State          | Jabar Juara   |
#      | Zip Code       | 15015         |
#      | Country        | Konoha |
#    And user clicks on To Payment button
#    Then user should be redirected to payment method
#    When user enters the following payment details:
#      | Full Name       | Bob Dylan        |
#      | Card Number     | 1111222233334444 |
#      | Expiration Date | 01/25            |
#      | Security Code   | 999              |
#    And user clicks on Review Order button
#    Then user should be redirected to order review
#    And user should see cart items
#    And user should see delivery address
#    And user should see payment method
#    When user clicks Place Order button
#    Then user should be redirected to checkout complete
#    And cart should be empty
