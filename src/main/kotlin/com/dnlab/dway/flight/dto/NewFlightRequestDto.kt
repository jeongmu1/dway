package com.dnlab.dway.flight.dto

import com.dnlab.dway.flight.domain.SeatGrade
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import java.sql.Timestamp

data class NewFlightRequestDto(
    val aircraftModel: String,
    @Pattern(regexp = "^DW\\d{3}\$", message = "올바른 항공편 번호 양식으로 입력해주세요.")
    val code: String,
    @Pattern(regexp = "^[A-Z]{3}\$", message = "올바른 공항 코드를 입력해주세요.")
    val departureAirport: String,
    @Pattern(regexp = "^[A-Z]{3}\$", message = "올바른 공항 코드를 입력해주세요.")
    val arrivalAirport: String,
    val departureTime: Timestamp,
    val arrivalTime: Timestamp,
    val flightSeatInfo: List<NewFlightSeatsRequestDto>
)

data class NewFlightSeatsRequestDto(
    val grade: SeatGrade,
    @Min(0)
    val fare: Int,
    @Min(0)
    val maxPassengers: Int
)