package com.dnlab.dway.booking.repository.impl

import com.dnlab.dway.booking.domain.QTicket
import com.dnlab.dway.booking.repository.TicketCustomRepository
import com.dnlab.dway.flight.domain.FlightSeats
import com.querydsl.jpa.impl.JPAQueryFactory

class TicketCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): TicketCustomRepository {
    override fun countAvailableSeatsByFlightSeats(flightSeats: FlightSeats): Int {
        val qTicket = QTicket.ticket

        val maxPassengers = flightSeats.maxPassengers
        val reservedSeats = jpaQueryFactory.select(qTicket.passengerCount.sum())
            .from(qTicket)
            .where(qTicket.flightSeats.eq(flightSeats))
            .fetch().firstOrNull() ?: 0
        return maxPassengers - reservedSeats
    }
}