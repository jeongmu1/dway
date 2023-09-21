package com.dnlab.dway.booking.service

import com.dnlab.dway.booking.dto.request.ItineraryInfoRequestDto
import com.dnlab.dway.booking.dto.request.LowestFareRequestDto
import com.dnlab.dway.booking.dto.response.ItineraryInfoResponseDto
import com.dnlab.dway.booking.dto.response.LowestFaresResponseDto

interface BookingService {
    fun findFlightSeatInfos(requestDto: ItineraryInfoRequestDto): ItineraryInfoResponseDto

    fun getLowestFareInfoList(requestDto: LowestFareRequestDto): LowestFaresResponseDto
}