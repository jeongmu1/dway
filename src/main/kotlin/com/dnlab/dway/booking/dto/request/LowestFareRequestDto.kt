package com.dnlab.dway.booking.dto.request

import com.dnlab.dway.common.annotation.validation.AirportCodeFormat
import com.dnlab.dway.common.annotation.validation.DateFormat
import jakarta.validation.constraints.NotNull
import java.util.Date

data class LowestFareRequestDto(
    @NotNull
    @DateFormat
    val criteriaDate: Date,
    @NotNull
    val period: Period,
    @NotNull
    @AirportCodeFormat
    val deptAirportCode: String,
    @NotNull
    @AirportCodeFormat
    val arriAirportCode: String,
)
