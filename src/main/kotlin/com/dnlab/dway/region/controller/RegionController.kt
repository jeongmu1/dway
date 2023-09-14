package com.dnlab.dway.region.controller

import com.dnlab.dway.region.dto.response.CountriesResponseDto
import com.dnlab.dway.region.service.RegionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/region")
class RegionController(
    private val regionService: RegionService
) {
    @GetMapping("/country")
    fun getCountryInfos(): ResponseEntity<CountriesResponseDto> {
        return ResponseEntity.ok(regionService.findCountries())
    }
}