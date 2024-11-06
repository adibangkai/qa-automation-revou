# src/test/resources/features/login.feature
Feature: Login Functionality

  Scenario: Successful login with valid credentials
    Given I am on the home screen
    When I navigate to the login screen
    And I enter username "bob@example.com" and password "10203040"
    Then I should be logged in successfully

  Scenario Outline: Login with different user types
    Given I am on the home screen
    When I navigate to the login screen
    And I enter username "<username>" and password "<password>"
    Then I should be logged in successfully

    Examples:
      | username                | password     |
      | bob@example.com          | 10203040 |
