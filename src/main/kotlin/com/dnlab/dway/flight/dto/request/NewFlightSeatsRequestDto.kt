package com.dnlab.dway.flight.dto.request

import com.dnlab.dway.flight.domain.FareGrade
import com.dnlab.dway.flight.domain.SeatGrade
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class NewFlightSeatsRequestDto(
    @NotNull(message = "좌석 등급은 반드시 포함되어야 합니다.")
    val grade: SeatGrade,
    @NotNull(message = "요금 등급은 받드시 포함되어야 합니다.")
    val fareGrade: FareGrade,
    @Min(0, message = "좌석 요금은 0 이상이여야 합니다.")
    val fare: Int,
    @Min(0, message = "탑승 승객은 0 이상이여야 합니다.")
    val maxPassengers: Int,
    @Min(0, message = "기내식은 0 이상이여야 합니다.")
    val inflightMeal: Int = 0,
    @Min(0, message = "위탁 수하물 무게는 0 이상이여야 합니다.")
    val checkedBaggageWeight: Int = 10,
    @Min(0, message = "기내 수하물 무게는 0 이상이여야 합니다.")
    val carryOnBaggageWeight: Int = 0,
    @Min(0, message = "기내 수하물 개수는 0 이상이여야 합니다.")
    val carryOnBaggageCount: Int = 0,
)