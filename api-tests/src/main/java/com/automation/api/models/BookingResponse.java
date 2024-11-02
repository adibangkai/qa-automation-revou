package com.automation.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    @JsonProperty("bookingid")
    private Integer bookingid;

    @JsonProperty("booking")
    private Booking booking;
}