package com.dnlab.dway.booking.repository.impl

import com.dnlab.dway.booking.domain.QTicket
import com.dnlab.dway.booking.dto.repository.LowestFareInfoDto
import com.dnlab.dway.booking.repository.FlightSeatsCustomRepository
import com.dnlab.dway.flight.domain.*
import com.dnlab.dway.region.domain.QAirport
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZoneId

class FlightSeatsCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : FlightSeatsCustomRepository {
    override fun findLowestFareFlightSeatsBy(
        deptAirportCode: String, arriAirportCode: String, startDay: LocalDate, endDay: LocalDate
    ): Map<LocalDate, FlightSeats> {
        val qFlight = QFlight.flight
        val qFlightSeats = QFlightSeats.flightSeats
        val qDepartureAirport = QAirport("d")
        val qArrivalAirport = QAirport("a")

        val flightSeatsList =
            jpaQueryFactory.selectFrom(qFlightSeats).join(qFlightSeats.flight, qFlight).fetchJoin()
                .join(qFlight.departureAirport, qDepartureAirport).fetchJoin()
                .join(qFlight.arrivalAirport, qArrivalAirport).fetchJoin().where(
                    Expressions.list(qFlight.departureTime, qFlightSeats.fare).`in`(
                        JPAExpressions.select(qFlight.departureTime, qFlightSeats.fare.min())
                            .from(qFlightSeats).innerJoin(qFlightSeats.flight, qFlight).where(
                                qFlight.departureTime.between(
                                    Timestamp.valueOf(startDay.atStartOfDay()),
                                    Timestamp.valueOf(endDay.atTime(23, 59, 59))
                                )
                            ).groupBy(qFlight.departureTime.dayOfMonth())
                    )
                ).fetch()

        println("$flightSeatsList")

        return flightSeatsList.associateBy {
            it.flight.departureTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }

    override fun findLowestFareInfoBy(
        deptAirportCode: String, arriAirportCode: String, startDay: LocalDate, endDay: LocalDate
    ): Map<LocalDate, LowestFareInfoDto> {
        val qFlight = QFlight.flight
        val qFlightSeats = QFlightSeats.flightSeats
        val qTicket = QTicket.ticket

        val minFareSubQuery = JPAExpressions.select(qFlightSeats.fare.min())
            .from(qFlightSeats)
            .where(qFlightSeats.flight.eq(qFlight))

        val result = jpaQueryFactory
            .select(
                Projections.constructor(
                    LowestFareInfoDto::class.java,
                    qFlight.departureTime,
                    qFlight.departureAirport.id.stringValue().`as`("deptAirportCode"),
                    qFlight.arrivalAirport.id.stringValue().`as`("arriAirportCode"),
                    qFlight.enabled.`as`("flightOperate"),
                    Expressions.asBoolean(
                        qFlightSeats.maxPassengers.subtract(
                            JPAExpressions.select(qTicket.passengerCount.sum())
                                .from(qTicket)
                                .where(qTicket.flightSeats.eq(qFlightSeats))
                        ).goe(1)
                    ).`as`("soldOut"),
                    Expressions.asBoolean(
                        JPAExpressions.selectOne()
                            .from(qFlightSeats)
                            .where(
                                qFlightSeats.flight.eq(qFlight),
                                qFlightSeats.fareGrade.eq(FareGrade.BUSINESS)
                            )
                            .exists()
                    ).`as`("businessOperate"),
                    ExpressionUtils.`as`(minFareSubQuery, "fareAmt"),
                    ExpressionUtils.`as`(
                        JPAExpressions.select(qFlightSeats.fareGrade)
                            .from(qFlightSeats)
                            .where(
                                qFlightSeats.flight.eq(qFlight),
                                qFlightSeats.fare.eq(minFareSubQuery)
                            ),
                        "fareType"
                    )
                )
            ).from(qFlight)
            .innerJoin(qFlight.flightSeats, qFlightSeats)
            .leftJoin(qFlightSeats.tickets, qTicket)
            .fetch()

        return result.associateBy {
            it.departureTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }
}