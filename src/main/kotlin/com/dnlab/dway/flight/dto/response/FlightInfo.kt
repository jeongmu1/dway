package com.dnlab.dway.flight.dto.response

import java.sql.Timestamp

data class FlightInfo(
    val flightCode: String,
    val aircraft: String,
    val deptAirport: String,
    val arriAirport: String,
    val deptTime: Timestamp,
    val arriTime: Timestamp,
    val flightSeatInfoList: List<FlightSeatInfo>
)
