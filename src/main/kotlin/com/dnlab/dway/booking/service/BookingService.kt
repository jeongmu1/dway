package com.dnlab.dway.booking.service

import com.dnlab.dway.booking.dto.request.ItineraryInfoRequestDto
import com.dnlab.dway.booking.dto.response.ItineraryInfoResponseDto

interface BookingService {
    fun findFlightSeatInfos(requestDto: ItineraryInfoRequestDto): ItineraryInfoResponseDto
}