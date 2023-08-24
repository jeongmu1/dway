package com.dnlab.dway.booking.repository

import com.dnlab.dway.flight.domain.Flight
import java.util.Date

interface FlightCustomRepository {
    fun findFlightInfosBy(
        schedule: Date,
        deptAirportCode: String,
        arriAirportCode: String
    ): List<Flight>
}