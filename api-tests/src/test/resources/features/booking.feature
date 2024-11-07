Feature: Booking API CRUD Operations

  Background:
    Given the API health check is successful

  @auth
  Scenario: Create authentication token
    When I create an auth token with credentials:
      | username | password    |
      | admin    | password123 |
    Then the response status code should be 200
    And the response should contain a valid token

  @smoke @booking
  Scenario: Get all booking IDs
    When I request all booking IDs
    Then the response status code should be 200
    And the response should contain a list of booking IDs

  @booking
  Scenario: Create a new booking
    When I create a booking with the following details:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | John | Doe      | 100        | true        | 2024-12-01 | 2024-12-05 | Breakfast       |
    Then the booking should be created successfully
    And the response status code should be 200
    And the booking details should match the request

  @booking
  Scenario: Get booking by ID
    Given there is an existing booking
    When I retrieve the booking by its ID
    Then the response status code should be 200
    And the booking details should be returned

  @booking
  Scenario: Filter bookings by name
    When I search for bookings with the following criteria:
      | firstname | lastname |
      | John     | Doe      |
    Then the response status code should be 200
    And the response should contain matching bookings

  @booking
  Scenario: Update an existing booking
    Given there is an existing booking
    And I have a valid auth token
    When I update the booking with the following details:
      | firstname      | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Janes          | Smith    | 200        | true        | 2024-12-10 | 2024-12-15 | Lunch          |
    Then the response status code should be 200
    And the booking should be updated successfully
    And the updated booking details should match the request

  @booking
  Scenario: Partial update of a booking
    Given there is an existing booking
    And I have a valid auth token
    When I partially update the booking with the following details:
      | firstname | lastname |
      | Robert   | Johnson  |
    Then the response status code should be 200
    And the booking should be partially updated
    And the modified fields should match the request

  @booking
  Scenario: Delete a booking
    Given there is an existing booking
    And I have a valid auth token
    When I delete the booking
    Then the response status code should be 201
    And the booking should be deleted successfully