package com.dnlab.dway.booking.repository

import com.dnlab.dway.flight.domain.Flight
import java.time.LocalDate

interface FlightCustomRepository {
    fun findFlightInfosBy(
        schedule: LocalDate,
        deptAirportCode: String,
        arriAirportCode: String
    ): List<Flight>
}