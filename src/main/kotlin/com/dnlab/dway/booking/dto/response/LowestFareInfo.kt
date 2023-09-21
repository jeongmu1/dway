package com.dnlab.dway.booking.dto.response

import com.dnlab.dway.flight.domain.FareGrade

data class LowestFareInfo(
    val flightDate: String,
    val deptAirportCode: String?,
    val arriAirportCode: String?,
    val soldOut: Boolean,
    val flightOperate: Boolean,
    val businessOperate: Boolean,
    val fareAmt: Int?,
    val fareType: FareGrade?,
    val dayOfTheWeek: Char,
    val date: String,
)