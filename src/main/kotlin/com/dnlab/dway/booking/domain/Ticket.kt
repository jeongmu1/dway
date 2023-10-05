package com.dnlab.dway.booking.domain

import com.dnlab.dway.auth.domain.Member
import com.dnlab.dway.common.domain.BaseTimeEntity
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.FlightSeats
import jakarta.persistence.*

@Entity
class Ticket(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        val member: Member,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        val flightSeats: FlightSeats,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        val flight: Flight,

        @Column(nullable = false)
        val passengerCount: Int
): BaseTimeEntity() {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Ticket

                return id == other.id
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }
}