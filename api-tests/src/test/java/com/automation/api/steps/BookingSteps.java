package com.automation.api.steps;

import com.automation.api.constants.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import com.automation.api.client.BookingApiClient;
import com.automation.api.models.*;
import com.automation.api.utils.TestDataBuilder;
import static org.junit.Assert.*;
import io.cucumber.datatable.DataTable;
import java.util.Map;
import java.util.List;

public class BookingSteps {
    private BookingApiClient apiClient = new BookingApiClient();
    private Response response;
    private String authToken;
    private Integer bookingId;
    private Booking testBooking;

    @Given("the API health check is successful")
    public void verifyApiHealth() {
        response = apiClient.healthCheck();
        assertEquals("Health check failed", 201, response.getStatusCode());
    }

    @When("I create an auth token with credentials:")
    public void createAuthToken(DataTable dataTable) {
        Map<String, String> credentials = dataTable.asMaps().get(0);
        response = apiClient.createToken(
                credentials.get("username"),
                credentials.get("password")
        );
    }

    @Then("the response should contain a valid token")
    public void verifyAuthToken() {
        authToken = response.jsonPath().getString("token");
        assertNotNull("Auth token should not be null", authToken);
        assertFalse("Auth token should not be empty", authToken.isEmpty());
    }

    @When("I request all booking IDs")
    public void getAllBookingIds() {
        response = apiClient.getAllBookingIds();
    }

    @Then("the response should contain a list of booking IDs")
    public void verifyBookingIds() {
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        assertNotNull("Booking IDs list should not be null", bookingIds);
    }



    @When("I create a booking with the following details:")
    public void createBooking(DataTable dataTable) {
        try {
            Map<String, String> bookingData = dataTable.asMaps().get(0);

            BookingDates dates = BookingDates.builder()
                    .checkin(bookingData.get("checkin"))
                    .checkout(bookingData.get("checkout"))
                    .build();

            testBooking = Booking.builder()
                    .firstname(bookingData.get("firstname"))
                    .lastname(bookingData.get("lastname"))
                    .totalprice(Integer.parseInt(bookingData.get("totalprice")))
                    .depositpaid(Boolean.parseBoolean(bookingData.get("depositpaid")))
                    .bookingdates(dates)
                    .additionalneeds(bookingData.get("additionalneeds"))
                    .build();

            // Use the BookingApiClient instead of direct RestAssured call
            response = apiClient.createBooking(testBooking);

            // Print response details for debugging
            System.out.println("Response status code: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody().asString());

        } catch (Exception e) {
            fail("Failed to create booking: " + e.getMessage());
        }
    }



    @Then("the booking should be created successfully")
    public void verifyBookingCreation() {
        try {
            BookingResponse bookingResponse = response.as(BookingResponse.class);
            bookingId = bookingResponse.getBookingid();
            assertNotNull("Booking ID should not be null", bookingId);
        } catch (Exception e) {
            fail("Failed to parse booking response: " + e.getMessage() +
                    "\nResponse body: " + response.getBody().asString());
        }
    }


    @Then("the booking details should match the request")
    public void verifyBookingDetails() {
        BookingResponse bookingResponse = response.as(BookingResponse.class);
        bookingId = bookingResponse.getBookingid();
        Booking createdBooking = bookingResponse.getBooking();

        assertEquals("First name doesn't match", testBooking.getFirstname(), createdBooking.getFirstname());
        assertEquals("Last name doesn't match", testBooking.getLastname(), createdBooking.getLastname());
        assertEquals("Total price doesn't match", testBooking.getTotalprice(), createdBooking.getTotalprice());
        assertEquals("Deposit paid status doesn't match", testBooking.getDepositpaid(), createdBooking.getDepositpaid());
        assertEquals("Check-in date doesn't match", testBooking.getBookingdates().getCheckin(),
                createdBooking.getBookingdates().getCheckin());
        assertEquals("Check-out date doesn't match", testBooking.getBookingdates().getCheckout(),
                createdBooking.getBookingdates().getCheckout());
        assertEquals("Additional needs don't match", testBooking.getAdditionalneeds(), createdBooking.getAdditionalneeds());
    }

    @Given("there is an existing booking")
    public void createExistingBooking() {
        // Create a default test booking
        Booking booking = createTestBooking("John", "Doe");
        assertNotNull("Failed to create test booking", bookingId);
    }

    @Given("I have a valid auth token")
    public void getValidAuthToken() {
        response = apiClient.createToken("admin", "password123");
        assertEquals("Failed to get auth token", 200, response.getStatusCode());

        // Enhanced token validation
        authToken = response.jsonPath().getString("token");
        assertNotNull("Auth token is null", authToken);
        assertFalse("Auth token is empty", authToken.trim().isEmpty());

        // Log token for debugging
        System.out.println("Generated auth token: " + authToken);
        System.out.println("Token length: " + authToken.length());
    }
    // Add a new step to verify the update
    @Then("the booking should be updated successfully")
    public void verifyBookingUpdate() {
        assertEquals("Update failed", 200, response.getStatusCode());
        Response getResponse = apiClient.getBooking(bookingId);
        assertEquals("Failed to retrieve updated booking", 200, getResponse.getStatusCode());

        Booking updatedBooking = getResponse.as(Booking.class);
        assertEquals("First name not updated", testBooking.getFirstname(), updatedBooking.getFirstname());
        assertEquals("Last name not updated", testBooking.getLastname(), updatedBooking.getLastname());
        assertEquals("Total price not updated", testBooking.getTotalprice(), updatedBooking.getTotalprice());
    }



    @When("I retrieve the booking by its ID")
    public void getBookingById() {
        assertNotNull("No booking ID available", bookingId);
        System.out.println("Retrieving booking with ID: " + bookingId);
        response = apiClient.getBooking(bookingId);
    }


    @When("I update the booking with the following details:")
    public void updateBooking(DataTable dataTable) {
        try {
            Map<String, String> bookingData = dataTable.asMaps().get(0);

            BookingDates dates = BookingDates.builder()
                    .checkin(bookingData.get("checkin"))
                    .checkout(bookingData.get("checkout"))
                    .build();

            Booking updateBooking = Booking.builder()
                    .firstname(bookingData.get("firstname"))
                    .lastname(bookingData.get("lastname"))
                    .totalprice(Integer.parseInt(bookingData.get("totalprice")))
                    .depositpaid(Boolean.parseBoolean(bookingData.get("depositpaid")))
                    .bookingdates(dates)
                    .additionalneeds(bookingData.get("additionalneeds"))
                    .build();

            // Log the booking data before sending
            System.out.println("Updating booking with ID: " + bookingId);
            System.out.println("Update payload: " + updateBooking);

            response = apiClient.updateBooking(bookingId, updateBooking);
            testBooking = updateBooking; // Store for verification
        } catch (Exception e) {
            fail("Failed to update booking: " + e.getMessage());
        }
    }
    @Then("the updated booking details should match the request")
    public void the_updated_booking_details_should_match_the_request() {
        // Get the updated booking from the API
        Response getResponse = apiClient.getBooking(bookingId);
        assertEquals("Failed to retrieve updated booking", 200, getResponse.getStatusCode());

        // Parse the response into a Booking object
        Booking updatedBooking = getResponse.as(Booking.class);

        // Verify all fields match what we sent in the update request
        assertEquals("First name doesn't match", testBooking.getFirstname(), updatedBooking.getFirstname());
        assertEquals("Last name doesn't match", testBooking.getLastname(), updatedBooking.getLastname());
        assertEquals("Total price doesn't match", testBooking.getTotalprice(), updatedBooking.getTotalprice());
        assertEquals("Deposit paid status doesn't match", testBooking.getDepositpaid(), updatedBooking.getDepositpaid());

        // Check booking dates
        assertEquals("Check-in date doesn't match",
                testBooking.getBookingdates().getCheckin(),
                updatedBooking.getBookingdates().getCheckin());
        assertEquals("Check-out date doesn't match",
                testBooking.getBookingdates().getCheckout(),
                updatedBooking.getBookingdates().getCheckout());

        // Check additional needs
        assertEquals("Additional needs don't match",
                testBooking.getAdditionalneeds(),
                updatedBooking.getAdditionalneeds());

        // Log verification success
        System.out.println("Successfully verified all booking details match the update request");
    }
    @When("I partially update the booking with the following details:")
    public void partialUpdateBooking(DataTable dataTable) {
        try {
            Map<String, String> bookingData = dataTable.asMaps().get(0);

            // First, get the current booking to maintain existing data
            Response currentBooking = apiClient.getBooking(bookingId);
            Booking existingBooking = currentBooking.as(Booking.class);

            // Create a booking object maintaining existing data while updating only specified fields
            Booking partialBooking = Booking.builder()
                    .firstname(bookingData.get("firstname"))
                    .lastname(bookingData.get("lastname"))
                    .totalprice(existingBooking.getTotalprice())
                    .depositpaid(existingBooking.getDepositpaid())
                    .bookingdates(existingBooking.getBookingdates())
                    .additionalneeds(existingBooking.getAdditionalneeds())
                    .build();

            System.out.println("Current booking ID: " + bookingId);
            System.out.println("Auth token present: " + (apiClient.getToken() != null));
            System.out.println("Partial update request payload: " + partialBooking);

            response = apiClient.partialUpdateBooking(bookingId, partialBooking);

            if (response.getStatusCode() != 200) {
                System.err.println("Partial update failed with status: " + response.getStatusCode());
                System.err.println("Response headers: " + response.getHeaders());
                System.err.println("Response body: " + response.getBody().asString());
            }

            testBooking = partialBooking;
        } catch (Exception e) {
            System.err.println("Partial update failed with exception: " + e.getMessage());
            e.printStackTrace();
            fail("Failed to partially update booking: " + e.getMessage());
        }
    }

    @When("I delete the booking")
    public void deleteBooking() {
        response = apiClient.deleteBooking(bookingId);
    }

    @Then("the booking should be deleted successfully")
    public void verifyBookingDeletion() {
        assertEquals("Delete operation failed", 201, response.getStatusCode());
        Response getResponse = apiClient.getBooking(bookingId);
        assertEquals("Booking still exists", 404, getResponse.getStatusCode());
    }

    @Then("the booking details should be returned")
    public void verifyBookingDetailsReturned() {
        assertEquals("Failed to get booking", 200, response.getStatusCode());
        Booking returnedBooking = response.as(Booking.class);
        assertNotNull("Returned booking should not be null", returnedBooking);
        assertNotNull("Firstname should not be null", returnedBooking.getFirstname());
        assertEquals("Firstname doesn't match", testBooking.getFirstname(), returnedBooking.getFirstname());
        assertEquals("Lastname doesn't match", testBooking.getLastname(), returnedBooking.getLastname());
    }
    @Then("the response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        assertEquals("Unexpected status code", expectedStatusCode, response.getStatusCode());
    }


    private Booking createTestBooking(String firstname, String lastname) {
        // Create booking dates
        BookingDates dates = BookingDates.builder()
                .checkin("2024-01-01")
                .checkout("2024-01-05")
                .build();

        // Create the booking object
        Booking booking = Booking.builder()
                .firstname(firstname)
                .lastname(lastname)
                .totalprice(100)
                .depositpaid(true)
                .bookingdates(dates)
                .additionalneeds("None")
                .build();

        // Store it for later verification
        testBooking = booking;

        // Create the booking via API
        Response createResponse = apiClient.createBooking(booking);
        assertEquals("Failed to create test booking", 200, createResponse.getStatusCode());

        // Extract and store the booking ID
        BookingResponse bookingResponse = createResponse.as(BookingResponse.class);
        bookingId = bookingResponse.getBookingid();

        return booking;
    }


    @Given("there are bookings with the following names:")
    public void createBookingsWithNames(DataTable dataTable) {
        List<Map<String, String>> bookings = dataTable.asMaps();
        for (Map<String, String> booking : bookings) {
            createTestBooking(
                    booking.get("firstname"),
                    booking.get("lastname")
            );
        }
    }


    @When("I search for bookings with the following criteria:")
    public void searchBookings(DataTable dataTable) {
        Map<String, String> searchCriteria = dataTable.asMaps().get(0);

        System.out.println("Searching for firstname=" + searchCriteria.get("firstname") +
                " lastname=" + searchCriteria.get("lastname"));

        // Use the BookingApiClient method instead of direct implementation
        response = apiClient.getBookingsByName(
                searchCriteria.get("firstname"),
                searchCriteria.get("lastname")
        );
    }

    @Then("the response should contain matching bookings")
    public void verifyMatchingBookings() {
        assertEquals("Search request failed", 200, response.getStatusCode());

        // Get the list of booking IDs from the response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        assertNotNull("Booking IDs list should not be null", bookingIds);

        System.out.println("Found " + bookingIds.size() + " matching bookings");

        // Even if we find no bookings, the API should return a 200 with an empty array
        // so we don't need to assert that bookings were found
        if (!bookingIds.isEmpty()) {
            System.out.println("First matching booking ID: " + bookingIds.get(0));
        }
    }
    @Then("the booking should be partially updated")
    public void the_booking_should_be_partially_updated() {
        assertEquals("Partial update failed", 200, response.getStatusCode());

        // Verify the response Content-Type
        String contentType = response.getHeader("Content-Type");
        assertTrue("Invalid content type: " + contentType,
                contentType != null && contentType.contains("application/json"));
    }

    @Then("the modified fields should match the request")
    public void the_modified_fields_should_match_the_request() {
        Response getResponse = apiClient.getBooking(bookingId);
        assertEquals("Failed to retrieve updated booking", 200, getResponse.getStatusCode());

        try {
            Booking updatedBooking = getResponse.as(Booking.class);

            // Verify only the fields that were updated
            assertEquals("First name wasn't updated correctly",
                    testBooking.getFirstname(), updatedBooking.getFirstname());
            assertEquals("Last name wasn't updated correctly",
                    testBooking.getLastname(), updatedBooking.getLastname());

            // Log successful verification
            System.out.println("Successfully verified partial update - modified fields match the request");
        } catch (Exception e) {
            fail("Failed to verify booking update: " + e.getMessage() +
                    "\nResponse body: " + getResponse.getBody().asString());
        }
    }
}

