package com.dnlab.dway.region.repository

import com.dnlab.dway.region.domain.Country
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository: JpaRepository<Country, String>, CountryCustomRepository {
    fun findCountryById(id: String): Country?
}