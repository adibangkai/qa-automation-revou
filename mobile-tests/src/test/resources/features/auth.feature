Feature: Auth

  Background:
    Given user is not logged in

  Scenario Outline: Login
    Given user is on login screen
    When user enters email "<email>" and password "<password>"
    Then <outcome>

    Examples:
      | email             | password   | outcome                                                                                                      |
      | bob@example.com   | 10203040   | user should be redirected to catalog screen                                                                  |
      | alice@example.com | 10203040   | user should get an error: "Sorry, this user has been locked out."                                            |
      |                   | 100223010  | user should get an username error message: "Username is required"                                            |
      | bob@dylan.com     |            | user should get an password error message: "Password is required"                                            |
      | longpass@example.com | 123456789987654321123456789 | user should get an error: "Provided credentials do not match any user in this service."  |
