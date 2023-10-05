package com.dnlab.dway.flight.service

import com.dnlab.dway.booking.dto.request.LowestFareRequestDto
import com.dnlab.dway.booking.dto.request.Period
import com.dnlab.dway.booking.dto.response.LowestFareInfo
import com.dnlab.dway.booking.dto.response.LowestFaresResponseDto
import com.dnlab.dway.booking.repository.TicketRepository
import com.dnlab.dway.flight.dto.request.FlightOfDayRequestDto
import com.dnlab.dway.flight.dto.response.FlightInfo
import com.dnlab.dway.flight.dto.response.FlightSeatInfo
import com.dnlab.dway.common.util.getEndTimestamp
import com.dnlab.dway.common.util.getStartTimestamp
import com.dnlab.dway.common.util.toKoreanChar
import com.dnlab.dway.flight.domain.FareGrade
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.FlightSeats
import com.dnlab.dway.flight.dto.request.NewFlightRequestDto
import com.dnlab.dway.flight.dto.request.NewFlightSeatsRequestDto
import com.dnlab.dway.flight.dto.response.NewFlightResponseDto
import com.dnlab.dway.flight.repository.*
import com.dnlab.dway.flight.util.FlightSpecification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class FlightServiceImpl(
    private val flightRepository: FlightRepository,
    private val flightSeatsRepository: FlightSeatsRepository,
    private val aircraftRepository: AircraftRepository,
    private val airportRepository: AirportRepository,
    private val ticketRepository: TicketRepository
) : FlightService {

    @Transactional
    override fun addFlight(requestDto: NewFlightRequestDto): NewFlightResponseDto {
        requireNoDuplicatedFareGrade(requestDto.flightSeatInfo)
        requireNoDuplicatedFare(requestDto.flightSeatInfo)
        val aircraft = aircraftRepository.findAircraftByModel(requestDto.aircraftModel)
            ?: throw NoSuchElementException(
                "해당 모델을 찾을 수 없습니다: ${requestDto.aircraftModel}"
            )
        val departureAirport = findAirportById(requestDto.departureAirport)
        val arrivalAirport = findAirportById(requestDto.arrivalAirport)

        val flight = flightRepository.save(
            Flight(
                aircraft = aircraft,
                arrivalTime = requestDto.arrivalTime,
                departureTime = requestDto.departureTime,
                code = requestDto.code,
                arrivalAirport = arrivalAirport,
                departureAirport = departureAirport
            )
        )
        flightSeatsRepository.saveAll(requestDto.flightSeatInfo.map {
            FlightSeats(
                fareGrade = it.fareGrade,
                maxPassengers = it.maxPassengers,
                flight = flight,
                seatGrade = it.grade,
                fare = it.fare,
                inflightMeal = it.inflightMeal,
                checkedBaggageWeight = it.checkedBaggageWeight,
                checkedBaggageCount = it.checkedBaggageCount,
                carryOnBaggageCount = it.carryOnBaggageCount,
                carryOnBaggageWeight = it.carryOnBaggageWeight,
            )
        })

        return NewFlightResponseDto(
            code = flight.code,
            arrivalAirport = arrivalAirport.id,
            departureAirport = departureAirport.id,
            departureTime = requestDto.departureTime,
            arrivalTime = requestDto.arrivalTime
        )
    }

    @Transactional(readOnly = true)
    override fun findFlightInfoOfDay(requestDto: FlightOfDayRequestDto): List<FlightInfo> {
        val flights = flightRepository.findAll(
            FlightSpecification.hasDepartureTimeBetween(
                start = requestDto.flightDate.getStartTimestamp(),
                end = requestDto.flightDate.getEndTimestamp()
            ).and(FlightSpecification.hasDepartureAirportId(requestDto.deptAirportId))
                .and(FlightSpecification.hasArrivalAirportId(requestDto.arriAirportId))
        )

        return flights.map { it.toFlightInfo() }
    }

    @Transactional(readOnly = true)
    override fun getLowestFareInfoListOfWeek(requestDto: LowestFareRequestDto): LowestFaresResponseDto {
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

    private fun requireNoDuplicatedFareGrade(newFlightSeatsRequestDtoList: List<NewFlightSeatsRequestDto>) {
        require(newFlightSeatsRequestDtoList.groupBy { it.fareGrade }
            .none { it.value.size > 1 }) { "중복된 FareGrade 가 존재합니다." }
    }

    private fun requireNoDuplicatedFare(newFlightSeatsRequestDtoList: List<NewFlightSeatsRequestDto>) {
        require(newFlightSeatsRequestDtoList.groupBy { it.fare }
            .none { it.value.size > 1 }) { "다른 FareGrade 의 Fare 중 중복된 Fare 값이 있습니다." }
    }

    private fun findAirportById(id: String) =
        airportRepository.findAirportById(id)
            ?: throw NoSuchElementException("해당 공항을 찾을 수 없습니다: $id")

    private fun Flight.toFlightInfo(): FlightInfo {
        val flightSeatInfoList = flightSeats.map { it.toFlightSeatInfo() }
        val deptAirportId = departureAirport.id
        val arriAirportId = arrivalAirport.id
        val aircraft = aircraft.model

        return FlightInfo(
            flightCode = code,
            aircraft = aircraft,
            deptAirport = deptAirportId,
            arriAirport = arriAirportId,
            deptTime = departureTime,
            arriTime = arrivalTime,
            flightSeatInfoList = flightSeatInfoList
        )
    }

    private fun FlightSeats.toFlightSeatInfo(): FlightSeatInfo {
        val remainingSeats = maxPassengers - tickets.sumOf { it.passengerCount }
        return FlightSeatInfo(
            fareGrade = fareGrade,
            seatGrade = seatGrade,
            checkedBaggageWeight = checkedBaggageWeight,
            checkedBaggageCount = checkedBaggageCount,
            carryOnBaggageWeight = carryOnBaggageWeight,
            carryOnBaggageCount = carryOnBaggageCount,
            inflightMeal = inflightMeal,
            remainingSeats = remainingSeats
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