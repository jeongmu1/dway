package com.dnlab.dway.flight.service

import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.FlightSeats
import com.dnlab.dway.flight.dto.request.NewFlightRequestDto
import com.dnlab.dway.flight.dto.request.NewFlightSeatsRequestDto
import com.dnlab.dway.flight.dto.response.NewFlightResponseDto
import com.dnlab.dway.flight.repository.AircraftRepository
import com.dnlab.dway.flight.repository.AirportRepository
import com.dnlab.dway.flight.repository.FlightRepository
import com.dnlab.dway.flight.repository.FlightSeatsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FlightServiceImpl(
    private val flightRepository: FlightRepository,
    private val flightSeatsRepository: FlightSeatsRepository,
    private val aircraftRepository: AircraftRepository,
    private val airportRepository: AirportRepository
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
                grade = it.grade,
                fare = it.fare,
                inflightMeal = it.inflightMeal,
                checkedBaggageWeight = it.checkedBaggageWeight,
                carryOnBaggageCount = it.carryOnBaggageCount,
                carryOnBaggageWeight = it.carryOnBaggageWeight
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

    private fun requireNoDuplicatedFareGrade(newFlightSeatsRequestDtoList: List<NewFlightSeatsRequestDto>) {
        require(newFlightSeatsRequestDtoList.groupBy { it.fareGrade }
            .none { it.value.size > 1 }) { "중복된 FareGrade 가 존재합니다." }
    }

    private fun requireNoDuplicatedFare(newFlightSeatsRequestDtoList: List<NewFlightSeatsRequestDto>) {
        require(newFlightSeatsRequestDtoList.groupBy { it.fare }
            .none { it.value.size > 1}) { "다른 FareGrade 의 Fare 중 중복된 Fare 값이 있습니다." }
    }

    private fun findAirportById(id: String) =
        airportRepository.findAirportById(id)
            ?: throw NoSuchElementException("해당 공항을 찾을 수 없습니다: $id")
}