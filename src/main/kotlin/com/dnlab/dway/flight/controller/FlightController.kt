package com.dnlab.dway.flight.controller

import com.dnlab.dway.flight.dto.NewFlightRequestDto
import com.dnlab.dway.flight.service.FlightService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/flight")
class FlightController(
    private val flightService: FlightService
) {

    @PostMapping("/new")
    fun addFlight(requestDto: NewFlightRequestDto): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(flightService.addFlight(requestDto))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}