package com.dnlab.dway.booking.service

import com.dnlab.dway.booking.dto.request.ItineraryInfoRequestDto
import com.dnlab.dway.booking.dto.response.FlightFareInfo
import com.dnlab.dway.booking.dto.response.ItineraryInfoResponseDto
import com.dnlab.dway.booking.dto.response.SimpleFlightSeatInfo
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.FlightSeats
import com.dnlab.dway.flight.repository.FlightRepository
import com.dnlab.dway.flight.repository.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class BookingServiceImpl(
    private val flightRepository: FlightRepository,
    private val ticketRepository: TicketRepository
) : BookingService {

    @Transactional(readOnly = true)
    override fun findFlightSeatInfos(requestDto: ItineraryInfoRequestDto): ItineraryInfoResponseDto {
        val airportInfo = requestDto.airportCodes.first()
        val deptAirport = airportInfo.deptAirportCodeFormat
        val arriAirport = airportInfo.arriAirportCodeFormat

        val flights = requestDto.schedule?.let {
            flightRepository.findFlightInfosBy(it, deptAirport, arriAirport)
        } ?: throw IllegalArgumentException("비행일은 반드시 포함되어야 합니다.")

        return flights.toItineraryInfoResponseDto()
    }

    private fun List<Flight>.toItineraryInfoResponseDto(): ItineraryInfoResponseDto {
        return ItineraryInfoResponseDto(map { it.toFlightFareInfo() })
    }

    private fun Flight.toFlightFareInfo(): FlightFareInfo {
        return FlightFareInfo(
            flightCode = code,
            aircraftModel = aircraft.model,
            deptTime = departureTime.toString(),
            arriTime = arrivalTime.toString(),
            flightSeatInfos = flightSeats.map { it.toSimpleFlightSeatInfo() }
        )
    }

    private fun FlightSeats.toSimpleFlightSeatInfo(): SimpleFlightSeatInfo {
        return SimpleFlightSeatInfo(
            fareGrade = fareGrade,
            seatGrade = grade,
            fare = fare,
            inflightMeal = inflightMeal,
            checkedBaggageWeight = checkedBaggageWeight,
            carryOnBaggageWeight = carryOnBaggageWeight,
            carryOnBaggageCount = carryOnBaggageCount,
            remainingSeats = ticketRepository.countAvailableSeatsByFlightSeats(this)
        )
    }
}