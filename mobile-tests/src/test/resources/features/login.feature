Feature: Login Page Functionality

  Background:
    Given User is on the catalogs page
    When User clicks on menu button
    And User clicks on login menu item

  Scenario: Successful login with valid credentials
    When User enter username "bob@example.com"
    And User enter password "10203040"
    And User click the login button
    Then User should be redirected to the catalog page
