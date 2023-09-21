package com.dnlab.dway.booking.service

import com.dnlab.dway.booking.dto.request.ItineraryInfoRequestDto
import com.dnlab.dway.booking.dto.request.LowestFareRequestDto
import com.dnlab.dway.booking.dto.request.Period
import com.dnlab.dway.booking.dto.response.*
import com.dnlab.dway.common.util.toKoreanChar
import com.dnlab.dway.flight.domain.FareGrade
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.FlightSeats
import com.dnlab.dway.flight.repository.FlightRepository
import com.dnlab.dway.flight.repository.FlightSeatsRepository
import com.dnlab.dway.flight.repository.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class BookingServiceImpl(
    private val flightRepository: FlightRepository,
    private val ticketRepository: TicketRepository,
    private val flightSeatsRepository: FlightSeatsRepository
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

    @Transactional(readOnly = true)
    override fun getLowestFareInfoList(requestDto: LowestFareRequestDto): LowestFaresResponseDto {
        val criteriaDate =
            requestDto.criteriaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val (startDay, endDay) = when (requestDto.period) {
            Period.N -> criteriaDate.plusDays(1) to criteriaDate.plusDays(7)
            else -> criteriaDate.minusDays(7) to criteriaDate.minusDays(1)
        }

        val flightSeatsMap = flightSeatsRepository.findLowestFareFlightSeatsBy(
            deptAirportCode = requestDto.deptAirportCode,
            arriAirportCode = requestDto.arriAirportCode,
            startDay = startDay,
            endDay = endDay
        )

        return LowestFaresResponseDto(
            startDay.datesUntil(endDay)
                .map { createLowestFareInfo(it, flightSeatsMap[it]) }.toList()
        )
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

    private fun createLowestFareInfo(date: LocalDate, flightSeats: FlightSeats?): LowestFareInfo {
        val flight = flightSeats?.flight
        return LowestFareInfo(
            flightDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
            deptAirportCode = flight?.departureAirport?.id,
            arriAirportCode = flight?.arrivalAirport?.id,
            flightOperate = flight?.enabled ?: false,
            soldOut = (flightSeats != null)
                    && (flightSeats.maxPassengers - ticketRepository.countAvailableSeatsByFlightSeats(
                flightSeats
            )) > 0,
            businessOperate = flight?.flightSeats?.any { it.fareGrade == FareGrade.BUSINESS }
                ?: false,
            fareAmt = flightSeats?.fare,
            fareType = flightSeats?.fareGrade,
            dayOfTheWeek = date.dayOfWeek.toKoreanChar(),
            date = date.format(DateTimeFormatter.ofPattern("MM-dd"))
        )
    }
}