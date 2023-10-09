package com.dnlab.dway.flight.domain

import com.dnlab.dway.common.domain.BaseTimeEntity
import com.dnlab.dway.region.domain.Airport
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Flight(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 10, nullable = false)
    val code: String,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    val aircraft: Aircraft,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val departureAirport: Airport,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val arrivalAirport: Airport,

    @Column(nullable = false)
    val departureTime: LocalDateTime,

    @Column(nullable = false)
    val arrivalTime: LocalDateTime,

    @Column(nullable = false)
    var enabled: Boolean = true,
): BaseTimeEntity() {
    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    private val _flightSeats: MutableList<FlightSeats> = mutableListOf()
    val flightSeats: List<FlightSeats>
        get() = _flightSeats

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Flight

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Flight(id=$id, code='$code', departureTime=$departureTime, arrivalTime=$arrivalTime, enabled=$enabled, createdAt=$createdAt)"
    }
}