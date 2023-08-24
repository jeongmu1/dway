package com.dnlab.dway.booking.dto.request

import com.dnlab.dway.common.annotation.validation.AirportCodeFormat

data class DeptArriAirportCode(
    @AirportCodeFormat
    val deptAirportCodeFormat: String,
    @AirportCodeFormat
    val arriAirportCodeFormat: String
)
