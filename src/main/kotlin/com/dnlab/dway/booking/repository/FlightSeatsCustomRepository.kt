package com.dnlab.dway.booking.repository

import com.dnlab.dway.booking.dto.repository.LowestFareInfoDto
import com.dnlab.dway.flight.domain.FlightSeats
import java.time.LocalDate

interface FlightSeatsCustomRepository {

    fun findLowestFareFlightSeatsBy(
        deptAirportCode: String,
        arriAirportCode: String,
        startDay: LocalDate,
        endDay: LocalDate
    ): Map<LocalDate, FlightSeats>

    fun findLowestFareInfoBy(
        deptAirportCode: String,
        arriAirportCode: String,
        startDay: LocalDate,
        endDay: LocalDate
    ): Map<LocalDate, LowestFareInfoDto>
}