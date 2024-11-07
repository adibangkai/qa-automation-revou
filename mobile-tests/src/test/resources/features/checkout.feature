Feature: Checkout

  Background:
    Given user is not logged in
    And app state is reset
    Given user is on catalog screen
    And user adds an item to cart
    And user is on cart screen

  Scenario: Checkout Without Login
    When user clicks proceed to checkout button
    Then user should be redirected to login screen

  Scenario: Checkout
    When user clicks proceed to checkout button
    And user logs in to the app
    Then user should be redirected to checkout screen

  Scenario Outline: Checkout Shipping Form
    When user clicks proceed to checkout button
    And user logs in to the app
    Then user should be redirected to checkout screen
    When user enters the following shipping details:
      | Full Name      | <Full Name>      |
      | Address Line 1 | <Address Line 1> |
      | Address Line 2 | <Address Line 2> |
      | City           | <City>           |
      | State          | <State>          |
      | Zip Code       | <Zip Code>       |
      | Country        | <Country>        |
    And user clicks on To Payment button
    Then <Outcome>

    Examples:
      | Full Name | Address Line 1 | Address Line 2 | City     | State | Zip Code | Country       | Outcome                                            |
      | Bob Dylan | 4th avenue     | Taylor         | New York | NY    | 10001    | United States | user should be redirected to payment method        |
      | Bob Dylan | 4th avenue     |                | New York |       | 10001    | United States | user should be redirected to payment method        |
      |           | 4th avenue     | Taylor         | New York | NY    | 10001    | United States | user should get an error on "full name" field      |
      | Bob Dylan |                | Taylor         | New York | NY    | 10001    | United States | user should get an error on "address line 1" field |
      | Bob Dylan | 4th avenue     | Taylor         |          | NY    | 10001    | United States | user should get an error on "city" field           |
      | Bob Dylan | 4th avenue     | Taylor         | New York | NY    |          | United States | user should get an error on "zip code" field       |
      | Bob Dylan | 4th avenue     | Taylor         | New York | NY    | 10001    |               | user should get an error on "country" field        |
      | Bob Dylan | 4th avenue     | Taylor         | New York | NY    | asdfg    | United States | user should get an error on "zip code" field       |

  Scenario Outline: Checkout Payment Form
    When user clicks proceed to checkout button
    And user logs in to the app
    Then user should be redirected to checkout screen
    When user enters the following shipping details:
      | Full Name      | Bob Dylan     |
      | Address Line 1 | 4th avenue    |
      | Address Line 2 | Taylor        |
      | City           | New York      |
      | State          | NY            |
      | Zip Code       | 10001         |
      | Country        | United States |
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
      | Bob Dylan | 999              | 01/25           | 999           | user should get an error on "card number" field     |
      | Bob Dylan | 1111222233334444 | qw/er           | 999           | user should get an error on "expiration date" field |
      | Bob Dylan | 1111222233334444 | 1               | 999           | user should get an error on "expiration date" field |
      | Bob Dylan | 1111222233334444 | 01/25           | asd           | user should get an error on "security code" field   |
      | Bob Dylan | 1111222233334444 | 01/25           | 1             | user should get an error on "security code" field   |

  Scenario: Checkout Review
    When user clicks proceed to checkout button
    And user logs in to the app
    Then user should be redirected to checkout screen
    When user enters the following shipping details:
      | Full Name      | Bob Dylan     |
      | Address Line 1 | 4th avenue    |
      | Address Line 2 | Taylor        |
      | City           | New York      |
      | State          | NY            |
      | Zip Code       | 10001         |
      | Country        | United States |
    And user clicks on To Payment button
    Then user should be redirected to payment method
    When user enters the following payment details:
      | Full Name       | Bob Dylan        |
      | Card Number     | 1111222233334444 |
      | Expiration Date | 01/25            |
      | Security Code   | 999              |
    And user clicks on Review Order button
    Then user should be redirected to order review
    And user should see cart items
    And user should see delivery address
    And user should see payment method
    When user clicks Place Order button
    Then user should be redirected to checkout complete
    And cart should be empty
