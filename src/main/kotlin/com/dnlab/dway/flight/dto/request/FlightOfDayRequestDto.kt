package com.dnlab.dway.flight.dto.request

import com.dnlab.dway.common.annotation.validation.AirportCodeFormat
import com.dnlab.dway.common.annotation.validation.DateFormat
import java.time.LocalDate

data class FlightOfDayRequestDto(
    @AirportCodeFormat
    val deptAirportId: String,
    @AirportCodeFormat
    val arriAirportId: String,
    @DateFormat
    val flightDate: LocalDate
)