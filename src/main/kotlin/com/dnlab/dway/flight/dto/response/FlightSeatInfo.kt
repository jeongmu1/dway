package com.dnlab.dway.flight.dto.response

import com.dnlab.dway.flight.domain.FareGrade
import com.dnlab.dway.flight.domain.SeatGrade

data class FlightSeatInfo(
    val fareGrade: FareGrade,
    val seatGrade: SeatGrade,
    val checkedBaggageWeight: Short,
    val checkedBaggageCount: Short,
    val carryOnBaggageWeight: Short,
    val carryOnBaggageCount: Short,
    val inflightMeal: Short,
    val remainingSeats: Int
)
