package com.automation.api.constants;

public class EndPoints {
    // Base URL - this is correct
    public static final String BASE_URL = "https://restful-booker.herokuapp.com";  // Changed to HTTPS

    // Auth
    public static final String AUTH = "/auth";

    // Booking
    public static final String BOOKING = "/booking";
    public static final String BOOKING_BY_ID = "/booking/{id}";

    // Health Check
    public static final String PING = "/ping";
}