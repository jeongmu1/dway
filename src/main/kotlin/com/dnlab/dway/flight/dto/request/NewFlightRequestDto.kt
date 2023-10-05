package com.dnlab.dway.flight.dto.request

import com.dnlab.dway.common.annotation.validation.AirportCodeFormat
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

data class NewFlightRequestDto(
    @NotNull(message = "비행기 모델은 반드시 포함되어야 합니다.")
    val aircraftModel: String,
    @Pattern(regexp = "^DW\\d{3}\$", message = "올바른 항공편 번호 양식으로 입력해주세요.")
    val code: String,
    @AirportCodeFormat
    val departureAirport: String,
    @AirportCodeFormat
    val arrivalAirport: String,
    @NotNull(message = "출발 시간은 반드시 포함되어야 합니다.")
    val departureTime: LocalDateTime,
    @NotNull(message = "도착 시간은 반드시 포함되어야 합니다.")
    val arrivalTime: LocalDateTime,
    @NotNull(message = "좌석 정보는 반드시 포함되어야 합니다.")
    @NotEmpty(message = "좌석 정보가 비어있습니다.")
    val flightSeatInfo: List<NewFlightSeatsRequestDto>
)
