package com.dnlab.dway.booking.dto.response

import com.dnlab.dway.flight.domain.FareGrade
import com.dnlab.dway.flight.domain.SeatGrade

data class SimpleFlightSeatInfo(
    val fareGrade: FareGrade,
    val seatGrade: SeatGrade,
    val fare: Int,
    val inflightMeal: Int,
    val checkedBaggageWeight: Int,
    val carryOnBaggageWeight: Int,
    val carryOnBaggageCount: Int,
    val remainingSeats: Int
)
