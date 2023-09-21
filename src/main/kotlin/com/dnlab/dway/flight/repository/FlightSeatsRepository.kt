package com.dnlab.dway.flight.repository

import com.dnlab.dway.booking.repository.FlightSeatsCustomRepository
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.FlightSeats
import org.springframework.data.jpa.repository.JpaRepository

interface FlightSeatsRepository: JpaRepository<FlightSeats, Long>, FlightSeatsCustomRepository {
    fun findAllByFlight(flight: Flight)
}