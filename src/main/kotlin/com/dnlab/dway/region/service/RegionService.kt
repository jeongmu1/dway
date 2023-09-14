package com.dnlab.dway.region.service

import com.dnlab.dway.region.dto.response.CountriesResponseDto

interface RegionService {
    fun findCountries(): CountriesResponseDto
}