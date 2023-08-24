package com.dnlab.dway.booking.controller

import com.dnlab.dway.booking.dto.request.ItineraryInfoRequestDto
import com.dnlab.dway.booking.dto.response.ItineraryInfoResponseDto
import com.dnlab.dway.booking.service.BookingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/booking")
class BookingController(
    private val bookingService: BookingService
) {

    @GetMapping("/flight-info")
    fun findFlightInfos(requestDto: ItineraryInfoRequestDto): ResponseEntity<ItineraryInfoResponseDto> {
        return try {
            ResponseEntity.ok(bookingService.findFlightSeatInfos(requestDto))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
}