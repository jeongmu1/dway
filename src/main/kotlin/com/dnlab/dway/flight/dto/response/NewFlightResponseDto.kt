package com.dnlab.dway.flight.dto.response

import java.time.LocalDateTime

data class NewFlightResponseDto(
    val code: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureTime: LocalDateTime,
    val arrivalTime: LocalDateTime
)