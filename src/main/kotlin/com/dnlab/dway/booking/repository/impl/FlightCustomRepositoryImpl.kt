package com.dnlab.dway.booking.repository.impl

import com.dnlab.dway.booking.repository.FlightCustomRepository
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.QAircraft
import com.dnlab.dway.flight.domain.QFlight
import com.dnlab.dway.flight.domain.QFlightSeats
import com.querydsl.jpa.impl.JPAQueryFactory
import java.sql.Timestamp
import java.time.ZoneId
import java.util.*

class FlightCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : FlightCustomRepository {
    override fun findFlightInfosBy(
        schedule: Date,
        deptAirportCode: String,
        arriAirportCode: String,
    ): List<Flight> {
        val qFlight = QFlight.flight
        val qFlightSeats = QFlightSeats.flightSeats
        val qAircraft = QAircraft.aircraft

        val localDate = schedule.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val startOfDate = Timestamp.valueOf(localDate.atStartOfDay())
        val endOfDate = Timestamp.valueOf(localDate.atTime(23, 59, 59))

        return jpaQueryFactory.selectFrom(qFlight)
            .leftJoin(qFlight.flightSeats, qFlightSeats).fetchJoin()
            .leftJoin(qFlight.aircraft, qAircraft).fetchJoin()
            .where(
                qFlight.departureAirport.id.eq(deptAirportCode),
                qFlight.arrivalAirport.id.eq(arriAirportCode),
                qFlight.departureTime.between(startOfDate, endOfDate)
            ).fetch()
    }
}