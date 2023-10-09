package com.dnlab.dway.flight.domain

import com.dnlab.dway.booking.domain.Ticket
import jakarta.persistence.*

@Entity
class FlightSeats(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val flight: Flight,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val seatGrade: SeatGrade,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val fareGrade: FareGrade,

    @Column(nullable = false)
    val inflightMeal: Short, // 기내식

    // 수하물 관련
    @Column(nullable = false)
    val checkedBaggageWeight: Short,
    @Column(nullable = false)
    val checkedBaggageCount: Short,
    @Column(nullable = false)
    val carryOnBaggageWeight: Short,
    @Column(nullable = false)
    val carryOnBaggageCount: Short,

    @Column(nullable = false)
    val fare: Int,
    @Column(nullable = false)
    val maxPassengers: Int,
) {

    @OneToMany(mappedBy = "flightSeats", fetch = FetchType.LAZY)
    private val _tickets: MutableList<Ticket> = mutableListOf()
    val tickets: List<Ticket>
        get() = _tickets

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