package com.dnlab.dway.booking.dto.response

import com.dnlab.dway.flight.domain.FareGrade
import com.dnlab.dway.flight.domain.SeatGrade

data class SimpleFlightSeatInfo(
    val fareGrade: FareGrade,
    val seatGrade: SeatGrade,
    val fare: Int,
    val inflightMeal: Short?,
    val checkedBaggageWeight: Short?,
    val carryOnBaggageWeight: Short?,
    val carryOnBaggageCount: Short?,
    val remainingSeats: Int
)
