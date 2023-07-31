package com.dnlab.dway.flight.repository

import com.dnlab.dway.flight.domain.Aircraft
import org.springframework.data.jpa.repository.JpaRepository

interface AircraftRepository: JpaRepository<Aircraft, Long> {
    fun findAircraftByModel(model: String): Aircraft?
}