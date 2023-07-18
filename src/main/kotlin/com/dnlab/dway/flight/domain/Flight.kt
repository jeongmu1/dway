package com.dnlab.dway.flight.domain

import com.dnlab.dway.region.domain.Airport
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.sql.Timestamp

@Entity
class Flight(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(length = 10)
        val code: String,

        @ManyToOne(fetch = FetchType.LAZY)
        val aircraft: Aircraft,

        @ManyToOne(fetch = FetchType.LAZY)
        val departureAirport: Airport,

        @ManyToOne(fetch = FetchType.LAZY)
        val arrivalAirport: Airport,

        val departureTime: Timestamp,

        val arrivalTime: Timestamp,

        @CreatedDate
        var createdDate: Timestamp? = null,
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Flight

                return id == other.id
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }
}