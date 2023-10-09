package com.dnlab.dway.booking.repository.impl

import com.dnlab.dway.booking.repository.FlightCustomRepository
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.QAircraft
import com.dnlab.dway.flight.domain.QFlight
import com.dnlab.dway.flight.domain.QFlightSeats
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDate

class FlightCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : FlightCustomRepository {
    override fun findFlightInfosBy(
        schedule: LocalDate,
        deptAirportCode: String,
        arriAirportCode: String,
    ): List<Flight> {
        val qFlight = QFlight.flight
        val qFlightSeats = QFlightSeats.flightSeats
        val qAircraft = QAircraft.aircraft

        return jpaQueryFactory.selectFrom(qFlight)
            .leftJoin(qFlight._flightSeats, qFlightSeats).fetchJoin()
            .leftJoin(qFlight.aircraft, qAircraft).fetchJoin()
            .where(
                qFlight.departureAirport.id.eq(deptAirportCode),
                qFlight.arrivalAirport.id.eq(arriAirportCode),
                qFlight.departureTime.between(schedule.atStartOfDay(), schedule.atTime(23, 59, 59))
            ).fetch()
    }
}