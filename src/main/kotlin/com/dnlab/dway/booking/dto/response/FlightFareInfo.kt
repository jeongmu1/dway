package com.dnlab.dway.booking.dto.response

data class FlightFareInfo(
    val flightCode: String,
    val aircraftModel: String,
    val deptTime: String,
    val arriTime: String,
    val flightSeatInfos: List<SimpleFlightSeatInfo>,
)
