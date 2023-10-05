package com.dnlab.dway.booking.repository

import com.dnlab.dway.booking.domain.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository: JpaRepository<Ticket, Long>, TicketCustomRepository