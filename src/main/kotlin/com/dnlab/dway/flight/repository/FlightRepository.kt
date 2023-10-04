package com.dnlab.dway.flight.repository

import com.dnlab.dway.booking.repository.FlightCustomRepository
import com.dnlab.dway.flight.domain.Flight
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface FlightRepository : JpaRepository<Flight, Long>,
    FlightCustomRepository,
    JpaSpecificationExecutor<Flight>