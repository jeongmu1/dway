package com.dnlab.dway.flight.repository

import com.dnlab.dway.booking.repository.TicketCustomRepository
import com.dnlab.dway.flight.domain.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository: JpaRepository<Ticket, Long>, TicketCustomRepository