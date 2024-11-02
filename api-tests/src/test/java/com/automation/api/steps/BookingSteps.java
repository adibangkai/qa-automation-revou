package com.automation.api.steps;

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
        testBooking = TestDataBuilder.createDefaultBooking();
        response = apiClient.createBooking(testBooking);
        bookingId = response.as(BookingResponse.class).getBookingid();
        assertNotNull("Failed to create test booking", bookingId);
    }

    @Given("I have a valid auth token")
    public void getValidAuthToken() {
        response = apiClient.createToken("admin", "password123");
        assertEquals("Failed to get auth token", 200, response.getStatusCode());
        authToken = response.jsonPath().getString("token");
        assertNotNull("Auth token is null", authToken);
    }

    @When("I retrieve the booking by its ID")
    public void getBookingById() {
        bookingId = 1153; // or any other known ID
        System.out.println("Retrieving booking with ID: " + bookingId);
        response = apiClient.getBooking(bookingId);
        System.out.println("Get booking response: " + response.getBody().asString());
    }
    @When("I search for bookings with the following criteria:")
    public void searchBookings(DataTable dataTable) {
        Map<String, String> searchCriteria = dataTable.asMaps().get(0);
        response = apiClient.getBookingIds(
                searchCriteria.get("firstname"),
                searchCriteria.get("lastname"),
                null,
                null
        );
    }

    @When("I update the booking with the following details:")
    public void updateBooking(DataTable dataTable) {
        try {
            Map<String, String> bookingData = dataTable.asMaps().get(0);

            BookingDates dates = new BookingDates(
                    bookingData.get("checkin"),
                    bookingData.get("checkout")
            );

            Booking updateBooking = new Booking();
            updateBooking.setFirstname(bookingData.get("firstname"));
            updateBooking.setLastname(bookingData.get("lastname"));
            updateBooking.setTotalprice(Integer.parseInt(bookingData.get("totalprice")));
            updateBooking.setDepositpaid(Boolean.parseBoolean(bookingData.get("depositpaid")));
            updateBooking.setBookingdates(dates);
            updateBooking.setAdditionalneeds(bookingData.get("additionalneeds"));

            response = apiClient.updateBooking(bookingId, updateBooking);
            testBooking = updateBooking; // Store for verification
        } catch (Exception e) {
            fail("Failed to update booking: " + e.getMessage());
        }
    }

    @When("I partially update the booking with the following details:")
    public void partialUpdateBooking(DataTable dataTable) {
        try {
            Map<String, String> bookingData = dataTable.asMaps().get(0);

            Booking partialBooking = new Booking();
            partialBooking.setFirstname(bookingData.get("firstname"));
            partialBooking.setLastname(bookingData.get("lastname"));

            response = apiClient.partialUpdateBooking(bookingId, partialBooking);
            testBooking = partialBooking; // Store for verification
        } catch (Exception e) {
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
        Booking returnedBooking = response.as(Booking.class);
        assertNotNull("Returned booking should not be null", returnedBooking);
        assertNotNull("Firstname should not be null", returnedBooking.getFirstname());
    }

    @Then("the response should contain matching bookings")
    public void verifyMatchingBookings() {
        List<BookingId> bookings = response.jsonPath().getList("$", BookingId.class);
        assertNotNull("No matching bookings found", bookings);
    }

    @Then("the response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        assertEquals("Unexpected status code", expectedStatusCode, response.getStatusCode());
    }
}