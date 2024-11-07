package com.automation.api.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.automation.api.models.*;
import com.automation.api.constants.EndPoints;
import static io.restassured.RestAssured.given;

public class BookingApiClient {
    private String token;
    /**
     * Create auth token
     */
    public Response createToken(String username, String password) {
        AuthRequest authRequest = AuthRequest.builder()
                .username(username)
                .password(password)
                .build();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .post(EndPoints.BASE_URL + EndPoints.AUTH);

        if (response.getStatusCode() == 200) {
            token = response.jsonPath().getString("token");
        }

        return response;
    }

    /**
     * Get all booking IDs
     */
    public Response getAllBookingIds() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get(EndPoints.BASE_URL + EndPoints.BOOKING);
    }

    /**
     * Get booking IDs with filters
     */
    public Response getBookingIds(String firstname, String lastname, String checkin, String checkout) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .queryParam("checkin", checkin)
                .queryParam("checkout", checkout)
                .get(EndPoints.BASE_URL + EndPoints.BOOKING);
    }

    /**
     * Get specific booking by ID
     */
    public Response getBooking(int bookingId) {
        System.out.println("Getting booking with ID: " + bookingId);
        return given()
                .log().all()
                .header("Accept", "application/json")
                .pathParam("id", bookingId)
                .when()
                .get(EndPoints.BASE_URL + EndPoints.BOOKING_BY_ID)
                .then()
                .log().all()
                .extract()
                .response();
    }
    public Response getBookingsByName(String firstname, String lastname) {
        return given()
                .log().all()  // Log request details
                .baseUri(EndPoints.BASE_URL)
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .when()
                .get("/booking")
                .then()
                .log().all()  // Log response details
                .extract()
                .response();
    }

    /**
     * Create new booking
     */
    public Response createBooking(Booking booking) {
        return given()
                .log().all()  // Log request details
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .headers(
                        "User-Agent", "RestAssured",
                        "Connection", "keep-alive"
                )
                .body(booking)
                .post(EndPoints.BASE_URL + EndPoints.BOOKING)
                .then()
                .log().all()  // Log response details
                .extract()
                .response();
    }


    /**
     * Update booking with exact headers as per API documentation
     */
    public Response updateBooking(int bookingId, Booking booking) {
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("No valid token available. Please authenticate first.");
        }

        return given()
                .log().all()  // Log request details
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)  // Correct cookie format as per docs
                .pathParam("id", bookingId)
                .body(booking)
                .when()
                .put(EndPoints.BASE_URL + EndPoints.BOOKING_BY_ID)
                .then()
                .log().all()  // Log response details
                .extract()
                .response();
    }



    /**
     * Partial update booking with exact headers as per API documentation
     */
    public Response partialUpdateBooking(int bookingId, Booking booking) {
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("No valid token available. Please authenticate first.");
        }

        return given()
                .log().all()  // Log request details
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)  // Correct cookie format as per docs
                .pathParam("id", bookingId)
                .body(booking)
                .when()
                .patch(EndPoints.BASE_URL + EndPoints.BOOKING_BY_ID)
                .then()
                .log().all()  // Log response details
                .extract()
                .response();
    }

    /**
     * Delete booking with correct header format
     */
    public Response deleteBooking(int bookingId) {
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("No valid token available. Please authenticate first.");
        }

        return given()
                .log().all()  // Log request details
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)  // Correct cookie format as per docs
                .pathParam("id", bookingId)
                .when()
                .delete(EndPoints.BASE_URL + EndPoints.BOOKING_BY_ID)
                .then()
                .log().all()  // Log response details
                .extract()
                .response();
    }

    /**
     * Health check
     */
    public Response healthCheck() {
        return given()
                .accept(ContentType.ANY)  // Accept any content type for health check
                .get(EndPoints.BASE_URL + EndPoints.PING);
    }
}