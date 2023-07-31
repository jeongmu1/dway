package com.dnlab.dway.flight.service

import com.dnlab.dway.flight.dto.NewFlightRequestDto
import com.dnlab.dway.flight.dto.NewFlightResponseDto

interface FlightService {
    fun addFlight(requestDto: NewFlightRequestDto): NewFlightResponseDto
}