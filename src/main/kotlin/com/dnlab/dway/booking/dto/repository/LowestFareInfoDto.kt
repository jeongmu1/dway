package com.dnlab.dway.booking.dto.repository

import com.dnlab.dway.flight.domain.FareGrade
import java.time.LocalDateTime

data class LowestFareInfoDto(
    val departureTime: LocalDateTime,
    val deptAirportCode: String,
    val arriAirportCode: String,
    val flightOperate: Boolean,
    val soldOut: Boolean,
    val businessOperate: Boolean?,
    val fareAmt: Int?,
    val fareType: FareGrade?
)
