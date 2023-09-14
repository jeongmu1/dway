package com.dnlab.dway.region.repository

import com.dnlab.dway.region.dto.response.CountryDto

interface CountryCustomRepository {
    fun findAllAsDto(): List<CountryDto>
}