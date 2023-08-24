package com.dnlab.dway.booking.dto.request

import com.dnlab.dway.common.annotation.validation.DateFormat
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.Date

data class ItineraryInfoRequestDto(
    @Min(0)
    val adultCount: Int,
    @Min(0)
    val childCount: Int,
    @Min(0)
    val infantCount: Int,
    @NotNull
    @NotEmpty
    val airportCodes: List<DeptArriAirportCode>,
    @DateFormat
    val schedule: Date?
)
