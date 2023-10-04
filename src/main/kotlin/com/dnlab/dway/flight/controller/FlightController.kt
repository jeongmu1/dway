package com.dnlab.dway.flight.controller

import com.dnlab.dway.flight.dto.request.FlightOfDayRequestDto
import com.dnlab.dway.flight.dto.request.NewFlightRequestDto
import com.dnlab.dway.flight.dto.response.FlightInfo
import com.dnlab.dway.flight.dto.response.NewFlightResponseDto
import com.dnlab.dway.flight.service.FlightService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/flight")
class FlightController(
    private val flightService: FlightService
) {

    @PostMapping("/new")
    fun addFlight(requestDto: NewFlightRequestDto): ResponseEntity<NewFlightResponseDto> {
        return try {
            ResponseEntity.ok(flightService.addFlight(requestDto))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @GetMapping("/daily-flight")
    fun getFlightOfDay(requestDto: FlightOfDayRequestDto): ResponseEntity<List<FlightInfo>> {
        return ResponseEntity.ok(flightService.findFlightInfoOfDay(requestDto))
    }
}