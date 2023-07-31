package com.dnlab.dway.flight.repository

import com.dnlab.dway.flight.domain.FlightSeats
import org.springframework.data.jpa.repository.JpaRepository

interface FlightSeatsRepository: JpaRepository<FlightSeats, Long>