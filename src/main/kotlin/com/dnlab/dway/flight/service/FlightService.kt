package com.dnlab.dway.flight.service

import com.dnlab.dway.booking.dto.request.LowestFareRequestDto
import com.dnlab.dway.booking.dto.response.LowestFaresResponseDto
import com.dnlab.dway.flight.dto.request.FlightOfDayRequestDto
import com.dnlab.dway.flight.dto.response.FlightInfo
import com.dnlab.dway.flight.dto.request.NewFlightRequestDto
import com.dnlab.dway.flight.dto.response.NewFlightResponseDto

interface FlightService {
    fun addFlight(requestDto: NewFlightRequestDto): NewFlightResponseDto
    fun getLowestFareInfoListOfWeek(requestDto: LowestFareRequestDto): LowestFaresResponseDto
    fun findFlightInfoOfDay(requestDto: FlightOfDayRequestDto): List<FlightInfo>
}