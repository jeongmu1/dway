package com.dnlab.dway.flight.dto.request

import com.dnlab.dway.flight.domain.SeatGrade
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class NewFlightSeatsRequestDto(
    @NotNull(message = "좌석 등급은 반드시 포함되어야 합니다.")
    val grade: SeatGrade,
    @Min(0, message = "좌석 요금은 0 이상이여야 합니다.")
    val fare: Int,
    @Min(0, message = "탑승 승객은 0 이상이여야 합니다.")
    val maxPassengers: Int
)