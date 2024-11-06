Feature: Login Functionality
  As a user of the Sauce Demo website
  I want to be able to login with different user types
  So that I can access the application

  Scenario Outline: Login with different user types
    Given I am on the login page
    When I login with "<userType>" credentials
    Then I should be redirected to inventory page

    Examples:
      | userType |
      | STANDARD_USER |
