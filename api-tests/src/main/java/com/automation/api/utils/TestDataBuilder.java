package com.automation.api.utils;

import com.automation.api.models.*;

public class TestDataBuilder {
    public static Booking createDefaultBooking() {
        return Booking.builder()
                .firstname("Jim")
                .lastname("Brown")
                .totalprice(111)
                .depositpaid(true)
                .bookingdates(BookingDates.builder()
                        .checkin("2018-01-01")
                        .checkout("2019-01-01")
                        .build())
                .additionalneeds("Breakfast")
                .build();
    }
}