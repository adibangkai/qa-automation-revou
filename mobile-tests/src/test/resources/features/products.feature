Feature: Product

  Background:
    Given user is on catalog screen
    And the app state is reset

  Scenario: Disable Add to Cart Button
    Given user clicks on one of the product
    When user clicks on minus button
    Then quantity should display "0"
    And add to cart button should be disabled

  Scenario: Sort Product Name Ascending
    When user clicks on filter button
    And selects "Name - Ascending"
    Then products should be sorted by name "ascending"

  Scenario: Sort Product Name Descending
    When user clicks on filter button
    And selects "Name - Descending"
    Then products should be sorted by name "descending"

  Scenario: Sort Product Price Ascending
    When user clicks on filter button
    And selects "Price - Ascending"
    Then products should be sorted by price "descending"

  Scenario: Sort Product Price Descending
    When user clicks on filter button
    And selects "Price - Descending"
    Then products should be sorted by price 'descending'

  Scenario: View Product Detail
    When user clicks on filter button
    And selects "Name - Ascending"
    Then user should be redirected to product detail screen

  Scenario: Adjust Product Quantity
    Given user clicks on one of the product
    When user clicks on plus button
    Then quantity should display "2"
    When user clicks on plus button
    Then quantity should display "3"
    When user clicks on minus button
    Then quantity should display "2"

  Scenario: Add Product to Cart
    Given user clicks on one of the product
    When user clicks on add to cart button
    Then cart icon should display a badge


