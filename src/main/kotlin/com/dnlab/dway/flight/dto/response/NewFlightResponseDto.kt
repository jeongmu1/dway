package com.dnlab.dway.flight.dto.response

import java.sql.Timestamp

data class NewFlightResponseDto(
    val code: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureTime: Timestamp,
    val arrivalTime: Timestamp
)