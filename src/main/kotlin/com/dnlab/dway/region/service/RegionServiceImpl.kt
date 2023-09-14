package com.dnlab.dway.region.service

import com.dnlab.dway.region.dto.response.CountriesResponseDto
import com.dnlab.dway.region.repository.CountryRepository
import org.springframework.stereotype.Service

@Service
class RegionServiceImpl(
    private val countryRepository: CountryRepository
): RegionService {
    override fun findCountries(): CountriesResponseDto {
        return CountriesResponseDto(countryRepository.findAllAsDto())
    }
}