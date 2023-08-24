package com.dnlab.dway.flight.domain

import jakarta.persistence.*

@Entity
class FlightSeats(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    val flight: Flight,

    @Enumerated(EnumType.STRING)
    val grade: SeatGrade,

    @Enumerated(EnumType.STRING)
    val fareGrade: FareGrade,

    @OneToMany(mappedBy = "seats", fetch = FetchType.LAZY)
    val tickets: MutableList<Ticket> = ArrayList(),

    val inflightMeal: Int = 0,
    val checkedBaggageWeight: Int = 10,
    val carryOnBaggageWeight: Int = 0,
    val carryOnBaggageCount: Int = 0,
    val fare: Int,
    val maxPassengers: Int,
    val currentPassengers: Int = maxPassengers,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FlightSeats

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}