package com.dnlab.dway.flight.dto.response

import java.time.LocalDateTime

data class FlightInfo(
    val flightCode: String,
    val aircraft: String,
    val deptAirport: String,
    val arriAirport: String,
    val deptTime: LocalDateTime,
    val arriTime: LocalDateTime,
    val flightSeatInfoList: List<FlightSeatInfo>
)
