package com.dnlab.dway.flight.domain

import com.dnlab.dway.auth.domain.Member
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
) {
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