package com.dnlab.dway.flight.controller

import com.dnlab.dway.booking.dto.request.LowestFareRequestDto
import com.dnlab.dway.booking.dto.response.LowestFaresResponseDto
import com.dnlab.dway.flight.dto.request.FlightOfDayRequestDto
import com.dnlab.dway.flight.dto.request.NewFlightRequestDto
import com.dnlab.dway.flight.dto.response.FlightInfo
import com.dnlab.dway.flight.dto.response.NewFlightResponseDto
import com.dnlab.dway.flight.service.FlightService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/flight")
class FlightController(
    private val flightService: FlightService
) {

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    fun addFlight(@RequestBody requestDto: NewFlightRequestDto): ResponseEntity<NewFlightResponseDto> {
        return try {
            ResponseEntity.ok(flightService.addFlight(requestDto))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @GetMapping("/daily-flight")
    fun getFlightOfDay(@RequestBody requestDto: FlightOfDayRequestDto): ResponseEntity<List<FlightInfo>> {
        return ResponseEntity.ok(flightService.findFlightInfoOfDay(requestDto))
    }

    @GetMapping("/lowest-fares")
    fun getLowestFares(requestDto: LowestFareRequestDto): ResponseEntity<LowestFaresResponseDto> {
        return ResponseEntity.ok(flightService.getLowestFareInfoListOfWeek(requestDto))
    }
}