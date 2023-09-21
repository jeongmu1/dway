package com.dnlab.dway.flight.repository

import com.dnlab.dway.region.domain.Airport
import org.springframework.data.jpa.repository.JpaRepository

interface AirportRepository: JpaRepository<Airport, String> {
    fun findAirportById(id: String): Airport?
}