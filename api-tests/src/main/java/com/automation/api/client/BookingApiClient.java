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
                .accept(ContentType.JSON)
                .body(authRequest)
                .post(EndPoints.BASE_URL + EndPoints.AUTH)
                .then()
                .log().all()  // Log response details
                .extract()
                .response();
        if (response.getStatusCode() == 200) {
            token = response.as(AuthResponse.class).getToken();
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
     * Update booking
     */
    public Response updateBooking(int bookingId, Booking booking) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", bookingId)
                .body(booking)
                .put(EndPoints.BASE_URL + EndPoints.BOOKING_BY_ID);
    }

    /**
     * Partial update booking
     */
    public Response partialUpdateBooking(int bookingId, Booking booking) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", bookingId)
                .body(booking)
                .patch(EndPoints.BASE_URL + EndPoints.BOOKING_BY_ID);
    }

    /**
     * Delete booking
     */
    public Response deleteBooking(int bookingId) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)  // Accept any content type for delete response
                .cookie("token", token)
                .pathParam("id", bookingId)
                .delete(EndPoints.BASE_URL + EndPoints.BOOKING_BY_ID);
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