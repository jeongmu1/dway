package com.dnlab.dway.flight.repository

import com.dnlab.dway.flight.domain.Flight
import org.springframework.data.jpa.repository.JpaRepository

interface FlightRepository: JpaRepository<Flight, Long>