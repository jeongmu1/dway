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

    val inflightMeal: Short? = null, // 기내식

    // 수하물 관련
    val checkedBaggageWeight: Short? = null,
    val checkedBaggageCount: Short? = null,
    val carryOnBaggageWeight: Short? = null,
    val carryOnBaggageCount: Short? = null,

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