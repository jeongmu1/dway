package com.dnlab.dway.booking.repository

import com.dnlab.dway.annotaions.IntegrationTest
import com.dnlab.dway.flight.domain.*
import com.dnlab.dway.flight.repository.AircraftRepository
import com.dnlab.dway.flight.repository.AirportRepository
import com.dnlab.dway.flight.repository.FlightRepository
import com.dnlab.dway.flight.repository.FlightSeatsRepository
import com.dnlab.dway.region.domain.Airport
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Timestamp
import java.time.LocalDate

@IntegrationTest
@SpringBootTest
internal class FlightSeatsCustomRepositoryTest {

    @Autowired
    private lateinit var flightRepository: FlightRepository

    @Autowired
    private lateinit var flightSeatsRepository: FlightSeatsRepository

    @Autowired
    private lateinit var aircraftRepository: AircraftRepository

    @Autowired
    private lateinit var airportRepository: AirportRepository

    private lateinit var flight: Flight
    private lateinit var aircraft: Aircraft
    private lateinit var flightSeatsList: List<FlightSeats>
    private lateinit var deptAirport: Airport
    private lateinit var arriAirport: Airport
    private lateinit var targetFlightSeats: FlightSeats

    @BeforeEach
    internal fun setUp() {
        aircraft = aircraftRepository.save(createAircraft())
        deptAirport = airportRepository.findById("ICN").orElseThrow()
        arriAirport = airportRepository.findById("NRT").orElseThrow()
        flight = flightRepository.save(createFlight())
        targetFlightSeats = createTargetFlightSeats()
        flightSeatsList = createFlightSeatsList(targetFlightSeats).map { flightSeatsRepository.save(it) }
    }

    @Test
    internal fun findLowestFareFlightSeatsBy_test() {
        val lowestFareList = flightSeatsRepository.findLowestFareInfoBy(
            deptAirportCode = "ICN",
            arriAirportCode = "NRT",
            startDay = LocalDate.now().minusDays(1L),
            endDay = LocalDate.now().plusDays(1L)
        )

        println("lowestFareInfo : ${lowestFareList[LocalDate.now()]}")
        assertEquals(lowestFareList[LocalDate.now()]!!.fareAmt, targetFlightSeats.fare)
    }

    fun createAircraft(): Aircraft {
        return Aircraft(
            manufacture = Manufacture.BOEING,
            model = "B737-200"
        )
    }

    private fun createFlight(): Flight {
        return Flight(
            enabled = true,
            aircraft = aircraft,
            arrivalTime = Timestamp(System.currentTimeMillis() + 50000),
            createdDate = Timestamp(System.currentTimeMillis()),
            departureTime = Timestamp(System.currentTimeMillis()),
            code = "TW000",
            departureAirport = deptAirport,
            arrivalAirport = arriAirport
        )
    }

    private fun createFlightSeatsList(targetFlightSeats: FlightSeats): List<FlightSeats> {
        return listOf(
            targetFlightSeats,
            FlightSeats(
                carryOnBaggageCount = 0,
                carryOnBaggageWeight = 0,
                checkedBaggageWeight = 0,
                checkedBaggageCount = 0,
                fare = 15,
                inflightMeal = 0,
                maxPassengers = 100,
                flight = flight,
                fareGrade = FareGrade.SMART,
                seatGrade = SeatGrade.ECONOMY
            )
        )
    }

    private fun createTargetFlightSeats(): FlightSeats {
        return FlightSeats(
            carryOnBaggageCount = 0,
            carryOnBaggageWeight = 0,
            checkedBaggageWeight = 0,
            checkedBaggageCount = 0,
            fare = 10,
            inflightMeal = 0,
            maxPassengers = 100,
            flight = flight,
            fareGrade = FareGrade.EVENT,
            seatGrade = SeatGrade.ECONOMY
        )
    }

}