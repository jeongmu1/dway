package com.dnlab.dway.booking.repository

import com.dnlab.dway.flight.domain.FlightSeats

interface TicketCustomRepository {
    fun countAvailableSeatsByFlightSeats(flightSeats: FlightSeats): Int
}